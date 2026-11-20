# Pocket Pilot — regenerated source starter

IMPORTANT: This is SOURCE CODE, NOT an APK. It has NOT been compiled, device-tested or security-audited. Build/API errors may require corrections. Do not trust it with important tasks.

## Phone-only build using GitHub
1. Extract this ZIP using your Android file manager.
2. Create an empty GitHub repository whose default branch is main.
3. Upload extracted CONTENTS at repository root. settings.gradle, build.gradle and app/ must be at the top level, not inside an extra folder. Desktop site mode may help.
4. Android pickers may omit hidden .github/. If necessary use GitHub Create new file, name it .github/workflows/build.yml and paste BUILD_WORKFLOW.txt into it. Commit it.
5. Open Actions > Build APK > Run workflow. Builds require internet and are subject to your GitHub account's quotas/policies.
6. When successful download artifact PocketPilot-debug-apk, extract it, and install app-debug.apk. No compiled APK is supplied here. Do not bypass malware/security warnings.
7. If it fails share the first compiler/CMake error, not just the final build-failed message.

## Model
Target candidate: Qwen3-1.7B Q4_K_M GGUF. Example model repository: https://huggingface.co/bartowski/Qwen_Qwen3-1.7B-GGUF . Obtain the .gguf file, not MLX/.task. Model is not included. You are responsible for license compliance and choosing a trusted download. Import copies it to private storage, requiring additional free space. Load speed, memory consumption, thermal behavior and inference performance on Galaxy A37 6GB are unverified.

App buttons: Import Qwen3 GGUF > Load imported model > enter one safe command > Propose action > Review and approve. Start with Open followed by an exact installed app name.

## Screen control
Optional AccessibilityService reads exposed screen text. Enable only after reviewing disclosure. Visit a harmless app, use Recents to return to Pocket Pilot, request a single safe action and inspect the proposed target. Tap/scroll/back leave the activity and act only if the captured package matches the foreground package after a delay. Taps require a unique exact label match. UI changes, stale snapshots and navigation may still cause failures or unexpected effects. No completion/success verification is implemented.

Supported: open app, tap exact label, scroll forward, Back, Home, refusal. Not implemented: voice, typing, screenshot vision, unattended task loops, model selector, robust lifecycle handling or cancellation of native inference. Every executed action requires approval. No action is executed merely by generating output. Force-stop the app / disable its accessibility service to fully stop it. Discard proposal does not interrupt an ongoing native generation.

## Security
No INTERNET permission, analytics or cloud fallback in the generated app. Build machines fetch dependencies remotely. Screen summaries are in process memory. Accessibility grants powerful access. Do NOT use with banking, payments, private messaging, deletion, credentials, lock screens or security/permission dialogs. Prompt refusals are not a reliable safety boundary. Human approval is the main boundary; model mistakes and screen prompt injection remain possible. No production-security claim is made. Do not publish as a finished assistant; review current store policies first.

## Implementation
Java Android UI and service, C++ JNI, llama.cpp tag b6000 fetched by CMake. CPU inference, four threads, context 2048, max 160 output tokens. Fresh context per command. Native crashes/OOM can terminate app; Java handling cannot prevent these. Activity recreation/state restoration minimal. Loading/import lifecycle can require restart. Artifact is debug-signed; keys may change across CI builds, potentially requiring uninstall/reinstall (which deletes imported model). Workflow uses third-party actions pinned by major tags, not immutable hashes. No tests or successful build are claimed.
