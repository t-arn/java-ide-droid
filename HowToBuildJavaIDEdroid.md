  1. Adjust ndk-build-aaptcomplete.sh to call the ndk-build script of the Android NDK
  1. Execute ndk-build-aaptcomplete.sh<br>This step creates the shared library libaaptcomplete.so in libs/armeabi<br>
<ol><li>Adjust 0_build-debug.bat and 0_build-prod.bat to match your environment.<br>Adjust following variables: JSDK, ASDK, KS, KEY<br>
</li><li>Execute 0_build-debug.bat or 0_build-prod.bat and choose "do it all"<br>This creates JavaIDEdroid.apk in out/test/JavaIDEdroid/ or out/production/JavaIDEdroid/