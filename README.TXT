How to build JavaIDEdroid
=========================

1. Adjust ndk-build-aaptcomplete.sh to call the ndk-build script of the Android NDK

2. Execute ndk-build-aaptcomplete.sh
   This step creates the shared library libaaptcomplete.so in libs/armeabi

3. Adjust 0_build-debug.bat and 0_build-prod.bat to match your environment. 
   Adjust following variables: JSDK, ASDK, KS, KEY

4. Execute 0_build-debug.bat or 0_build-prod.bat and choose "do it all"
   This creates JavaIDEdroid.apk in out/test/JavaIDEdroid/ or out/production/JavaIDEdroid/
   
Tom Arn, 2011-08-22
