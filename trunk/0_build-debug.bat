@echo off
rem
rem Batch to build JavaIDEdroid
rem
:start
cls
echo Building JavaIDEdroid (DEBUG)
echo.=====================
echo.
rem setting java home
set JSDK=u:\programs\jdk6

rem set android sdk tool dir
set ASDK=u:\Programs\android-sdk

rem setting keystore and key
set KS=v:\!Daten\Privat\EN\keystore.ks
set KEY=sw@t-arn.com

rem set project name and directory and output directory
set PNAME=JavaIDEdroid
set PDIR=%~dp0
set OUTDIR=%PDIR%bin\test\%PNAME%

rem change to project directory
cd /d %PDIR%

echo 0: clear old output files
echo 1: generate resources
echo 2: compile
echo 3: dexify
echo 4: create and test-sign APK package
echo a: do it all
echo q: quit
echo.
set /P ACTION=Action: 

if "%ACTION%"=="0" goto clear
if "%ACTION%"=="1" goto aapt
if "%ACTION%"=="2" goto compile
if "%ACTION%"=="3" goto dx
if "%ACTION%"=="4" goto apk
if "%ACTION%"=="a" goto clear
goto end

:clear
rd %OUTDIR% /S
md %OUTDIR%
echo Errorlevel: %ERRORLEVEL%
if not "%ACTION%"=="a" goto end
if not %ERRORLEVEL%==0 pause


:aapt
rem generate resource java code and packaged resources
echo ***** Generating R.java and packaged resources *****
%ASDK%\platform-tools\aapt p -m -J gen -M AndroidManifest.xml -S res -I %ASDK%\platforms\android-8\android.jar -f -F %OUTDIR%\%PNAME%.apk.res
echo Errorlevel: %ERRORLEVEL%
if not "%ACTION%"=="a" goto end
if not %ERRORLEVEL%==0 pause


:compile
rem compile
echo ***** Compiling Java sources *****
%JSDK%\bin\javac -verbose -deprecation -extdirs "" -encoding ascii -target 1.5 -d %OUTDIR% -bootclasspath "%ASDK%\platforms\android-8\android.jar;%PDIR%libs\ecj.jar;%PDIR%libs\dx_ta.jar;%PDIR%libs\bsh.jar;%PDIR%libs\androidprefs.jar;%PDIR%libs\sdklib_ta.jar;%PDIR%libs\zipsigner-lib_all.jar" -classpath "%PDIR%gen;%PDIR%src" "%PDIR%src\com\t_arn\%PNAME%\MainActivity.java"
echo Errorlevel: %ERRORLEVEL%
if not "%ACTION%"=="a" goto end
if not %ERRORLEVEL%==0 pause


:dx
rem dexify
echo ***** Creating classes.dex *****
CALL %ASDK%\platform-tools\dx.bat --dex --output=%OUTDIR%\classes.dex %OUTDIR% %PDIR%libs\*.jar
echo Errorlevel: %ERRORLEVEL%
if not "%ACTION%"=="a" goto end
if not %ERRORLEVEL%==0 pause


:apk
rem package resources and dex files together
rem Use -u for unsigned builds.
echo ***** Creating APK file *****
rem CALL %ASDK%\tools\apkbuilder.bat %OUTDIR%\%PNAME%.apk.unsigned -u -z %OUTDIR%\%PNAME%.apk.res -f %OUTDIR%\classes.dex -rf %PDIR%src -rj %PDIR%libs -nf %PDIR%libs
CALL %ASDK%\tools\apkbuilder.bat %OUTDIR%\%PNAME%.apk -z %OUTDIR%\%PNAME%.apk.res -f %OUTDIR%\classes.dex -rf %PDIR%src -rj %PDIR%libs -nf %PDIR%libs
echo Errorlevel: %ERRORLEVEL%
goto end
if not "%ACTION%"=="a" goto end
if not %ERRORLEVEL%==0 pause


:end
if "%ACTION%"=="q" exit
if "%ACTION%"=="Q" exit
echo.
pause
goto start
