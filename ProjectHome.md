# JavaIDEdroid #

JavaIDEdroid is an integrated development environment which runs on Android and allows to create native Android applications without the need to use the Android SDK on Windows or Linux.

You need Android 2.2.3 or higher and a storage card (/sdcard/) to use this app!

Following development tools are integrated in JavaIDEdroid:
  * aapt tool
  * Eclipse compiler for Java
  * dx tool
  * DexMerger tool
  * ApkBuilder
  * zipsigner-lib (this library also does the zipalign)
  * SpongyCastle Library
  * BeanShell Interpreter
  * JavaRunner: allows to run any binary Java commandline application (.jar file)

The APP can be extended with modules. The modules are loaded dynamically and the integrity of the module is checked before every start of the module. In the download area of the project's website you'll find some pre-built modules, for example for Ant or the jar tool.

The APP can be controlled and customized with BeanShell scripts. The APP supports the 'protected script mode' which verifies the integrity of the scripts before executing them.

The software supports project features:
  * Project definition files: Allows to define project specific information for every project.
  * Default scripts: Due to the project definition files, it is possible to use default BeanShell scripts (for compiling and building) which fit almost all projects. The default scripts can be started from the project menu and can be customized if needed.
  * Project template: When creating a new project definition files, you can tell the app to create a skeleton project for you.
  * Recent project list: Allows to quickly re-open recent projects
  * Project filemanager: Allows you to browse all your project files and create, open, edit, delete and run (only .bsh files) them. There is a 'Directory list' button that lets you quickly change to another subdirectory of the project.
  * Project time log: Allows you to see the time spent for development

By installing the JavaIDEdroidPRO key (or the old JavaIDEdroidPRO 1.x app) additional features become available:
  * Unlimited project support (the free version only supports very small projects)
  * DexMerger Tool: Allows to merge 2 .dex files. So, .jar libraries do not need to be re-dexed everytime.
  * dx: Merge functionality
  * dx: Incremental option
  * APK signing with user certificate
  * Unlimited JavaRunner (the free version only supports very small .jar files)

JavaIDEdroid supports the VIEW intent for associating BeanShell scripts (.bsh) and the SEND intent to integrate it into other Android apps.

You can get the latest APK from the <a href='https://play.google.com/store/apps/details?id=ch.tanapro.JavaIDEdroid'>Google Play</a>
<br>The source is available from SVN (<a href='http://code.google.com/p/java-ide-droid/source/checkout'>http://code.google.com/p/java-ide-droid/source/checkout</a>)<br>
<br>
You can get JavaIDEdroidPRO from <a href='https://play.google.com/store/apps/details?id=ch.tanapro.JavaIDEdroid.PRO'>Google Play</a>.<br>
<hr />
Screenshots of JavaIDEdroid:<br>
<br>
<img src='http://www.tanapro.ch/products/JavaIDEdroid/10_projectTab_270x480.png' />
<img src='http://www.tanapro.ch/products/JavaIDEdroid/50_beanshellTab_270x480.png' />