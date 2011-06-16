@echo off
rem
rem Batch to build JavaIDEdroid
rem
cls
echo Building JavaIDEdroid
echo.=====================
echo.
rem setting java home
set JSDK=u:\programs\jdk6

rem set project name and directory and output directory
set PNAME=JavaIDEdroid
set PDIR=%~dp0
set OUTDIR=%PDIR%out\production\%PNAME%

rem change to project directory
cd /d %PDIR%

rem set android sdk tool dir
set ASDK=u:\Programs\android-sdk

echo 1: generate resources
echo 2: compile
echo 3: dexify
echo 4: create APK package
echo 5: sign APK package
echo a: do it all
echo q: quit
echo.
set /P ACTION=Action: 

if "%ACTION%"=="1" goto aapt
if "%ACTION%"=="2" goto compile
if "%ACTION%"=="3" goto dx
if "%ACTION%"=="4" goto apk
if "%ACTION%"=="5" goto sign
if "%ACTION%"=="a" goto aapt
goto end

:aapt
rem generate resource java code and packaged resources
echo ***** Generating R.java and packaged resources *****
%ASDK%\platform-tools\aapt p -m -J gen -M AndroidManifest.xml -S res -I %ASDK%\platforms\android-8\android.jar -f -F %OUTDIR%\%PNAME%.apk.res
if not "%ACTION%"=="a" goto end
pause

:compile
rem compile
echo ***** Compiling Java sources *****
%JSDK%\bin\javac -verbose -deprecation -extdirs "" -encoding ascii -target 1.5 -d %OUTDIR% -bootclasspath "%ASDK%\platforms\android-8\android.jar;%PDIR%libs\ecj.jar;%PDIR%libs\dx.jar;%PDIR%libs\bsh.jar;%PDIR%libs\androidprefs.jar;%PDIR%libs\sdklib.jar;%PDIR%libs\zipsigner-lib_all.jar" -classpath "%PDIR%gen;%PDIR%src" "%PDIR%src\com\t_arn\%PNAME%\MainActivity.java"
if not "%ACTION%"=="a" goto end
pause

:dx
rem dexify
echo ***** Creating classes.dex *****
CALL %ASDK%\platform-tools\dx.bat --dex --output=%OUTDIR%\classes.dex %OUTDIR% %PDIR%libs\*.jar
if not "%ACTION%"=="a" goto end
pause

:apk
rem package resources and dex files together
rem Use -u for unsigned builds.
echo ***** Creating APK file *****
CALL %ASDK%\tools\apkbuilder.bat %OUTDIR%\%PNAME%.apk.unsigned -z %OUTDIR%\%PNAME%.apk.res -f %OUTDIR%\classes.dex -rf %PDIR%src -rj %PDIR%libs 
goto end
if not "%ACTION%"=="a" goto end
pause

:sign
rem zipsigner
echo ***** Signing the APK file *****
%JSDK%\bin\java -cp %OUTDIR% com.t_arn.%PNAME%.SignApk -M testkey -I "%OUTDIR%\%PNAME%.apk.unsigned" -O "%OUTDIR%\%PNAME%.apk"
pause

:end
echo.
pause
