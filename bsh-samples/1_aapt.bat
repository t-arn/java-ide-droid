@echo off
rem
rem aapt atch for HelloAndroid
rem
:start
cls
echo aapt for HelloAndroid
echo.======================
echo.

rem set project name and directory and output directory
set PNAME=HelloAndroid
set PDIR=%~dp0
set OUTDIR=%PDIR%out\test\%PNAME%

rem change to project directory
cd /d %PDIR%

rem set android sdk tool dir
set ANDROID_SDK_HOME=u:\Programs\android-sdk

:aapt
rem generate resource java code and packaged resources
echo ***** Generating R.java and packaged resources *****
%ANDROID_SDK_HOME%\platform-tools\aapt p -m -J gen -M AndroidManifest.xml -S res -I %ANDROID_SDK_HOME%\platforms\android-8\android.jar -f -F %OUTDIR%\%PNAME%.apk.res
pause

