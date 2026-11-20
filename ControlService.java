package dev.pocketpilot;
import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.*;
import android.os.Handler;
public class ControlService extends AccessibilityService {
 static ControlService instance;static volatile String screen="No external screen captured",screenPackage="";
 protected void onServiceConnected(){instance=this;}
 public void onDestroy(){instance=null;super.onDestroy();}
 public void onInterrupt(){}
 public void onAccessibilityEvent(AccessibilityEvent event){AccessibilityNodeInfo r=getRootInActiveWindow();if(r==null)return;String pkg=String.valueOf(r.getPackageName());if(pkg.equals(getPackageName()))return;StringBuilder b=new StringBuilder();walk(r,b,0);screen=b.toString();screenPackage=pkg;}
 void walk(AccessibilityNodeInfo n,StringBuilder b,int depth){if(n==null||depth>16||b.length()>2200)return;CharSequence t=n.getText(),d=n.getContentDescription();if(t!=null||d!=null)b.append(n.isClickable()?"button: ":"text: ").append(t!=null?t:d).append('\n');for(int i=0;i<n.getChildCount();i++)walk(n.getChild(i),b,depth+1);}
 void execute(String action,String label,String expected){
 if(action.equals("home")){performGlobalAction(GLOBAL_ACTION_HOME);return;}
 performGlobalAction(GLOBAL_ACTION_BACK);
 new Handler(getMainLooper()).postDelayed(()->{AccessibilityNodeInfo r=getRootInActiveWindow();if(r==null||!expected.equals(String.valueOf(r.getPackageName())))return;if(action.equals("back")){performGlobalAction(GLOBAL_ACTION_BACK);return;}if(action.equals("scroll")){scroll(r);return;}if(action.equals("tap")){java.util.ArrayList<AccessibilityNodeInfo> matches=new java.util.ArrayList<>();find(r,label,matches);if(matches.size()==1){AccessibilityNodeInfo n=matches.get(0);for(int i=0;i<4&&n!=null;i++,n=n.getParent())if(n.isClickable()){n.performAction(AccessibilityNodeInfo.ACTION_CLICK);break;}}}},1000);
 }
 void find(AccessibilityNodeInfo n,String text,java.util.ArrayList<AccessibilityNodeInfo> m){if(!text.isEmpty()&&(text.equalsIgnoreCase(String.valueOf(n.getText()))||text.equalsIgnoreCase(String.valueOf(n.getContentDescription()))))m.add(n);for(int i=0;i<n.getChildCount();i++){AccessibilityNodeInfo c=n.getChild(i);if(c!=null)find(c,text,m);}}
 boolean scroll(AccessibilityNodeInfo n){if(n.isScrollable()&&n.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD))return true;for(int i=0;i<n.getChildCount();i++){AccessibilityNodeInfo c=n.getChild(i);if(c!=null&&scroll(c))return true;}return false;}
}
