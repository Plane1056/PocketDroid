#include <jni.h>
#include <string>
#include <vector>
#include "llama.h"
static llama_model *model=nullptr;
extern "C" JNIEXPORT jboolean JNICALL Java_dev_pocketpilot_MainActivity_load(JNIEnv*e,jobject,jstring path){
 const char*p=e->GetStringUTFChars(path,0);if(model)llama_model_free(model);
 llama_backend_init();auto mp=llama_model_default_params();mp.n_gpu_layers=0;
 model=llama_model_load_from_file(p,mp);e->ReleaseStringUTFChars(path,p);return model!=nullptr;
}
extern "C" JNIEXPORT jstring JNICALL Java_dev_pocketpilot_MainActivity_generate(JNIEnv*e,jobject,jstring input){
 if(!model)return e->NewStringUTF("ERROR: No model loaded");
 const char*p=e->GetStringUTFChars(input,0);std::string s(p);e->ReleaseStringUTFChars(input,p);
 auto v=llama_model_get_vocab(model);int n=-llama_tokenize(v,s.data(),s.size(),nullptr,0,true,true);
 if(n<=0||n>1400)return e->NewStringUTF("ERROR: Prompt too long");
 std::vector<llama_token> t(n);llama_tokenize(v,s.data(),s.size(),t.data(),n,true,true);
 auto cp=llama_context_default_params();cp.n_ctx=2048;cp.n_batch=2048;cp.n_ubatch=256;cp.n_threads=4;cp.n_threads_batch=4;
 auto ctx=llama_init_from_model(model,cp);if(!ctx)return e->NewStringUTF("ERROR: Context allocation failed");
 auto sampler=llama_sampler_init_greedy();std::string out;
 if(llama_decode(ctx,llama_batch_get_one(t.data(),n))==0){for(int i=0;i<160;i++){
 auto token=llama_sampler_sample(sampler,ctx,-1);if(llama_vocab_is_eog(v,token))break;
 std::vector<char>b(256);int len=llama_token_to_piece(v,token,b.data(),b.size(),0,true);
 if(len<0){b.resize(-len);len=llama_token_to_piece(v,token,b.data(),b.size(),0,true);}if(len>0)out.append(b.data(),len);
 if(llama_decode(ctx,llama_batch_get_one(&token,1))!=0)break;
 }}else out="ERROR: Decode failed";
 llama_sampler_free(sampler);llama_free(ctx);return e->NewStringUTF(out.c_str());
}
