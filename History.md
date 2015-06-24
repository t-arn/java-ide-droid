

&lt;H1&gt;

JavaIDEdroid history

&lt;/H1&gt;



<h2> version 2.7.0 (03.12.2014) </h2>
<ul>
<li>New: Updated dx to version 1.10 (Android 5.0.0 <a href='https://code.google.com/p/java-ide-droid/source/detail?r=7'>r7</a>)</li>
<li>New (PRO): JavaIDEdroid can now be used to compile and build CodenameOne apps.</li>
</ul>

<h2> version 2.6.0 (20.06.2014) </h2>
<ul>
<li>New: 'Module salt' option in Settings enables the user to create user-specific hash values when creating JavaRunner modules. This makes sure nobody else (and this includes other JavaIDEdroid users) can modify these modules undetected. <font color='orange'>INFO: Due to the new algorithm, module hashes that were created with older versions of JavaIDEdroid will not be accepted anymore and must be updated. See the help section 'Modules and JavaRunner' to learn how to do this.</font></li>
<li>New: 'Script salt' option in Settings activates the Protected Script Mode</li>
<li>New: Protected Script Mode: In this mode JavaIDEdroid executes only scripts which are approved by the user. Approval is done by saving the BeanShell script in the internal editor. This adds a hash value to the script which will then be checked every time the script is started.</li>
<li>New: Added option menu items for editing the default scripts with the internal editor</li>
<li>New: Added button in BeanShell tab to allow editing the chosen BeanShell script with the internal editor</li>
<li>New: Updated ECJ to latest version</li>
<li>New: ECJ can now be used in Ant javac tasks. See the wiki page on the project website for more information.</li>
<li>New: Added script commands <code>fnGetVersionCode</code> and <code>fnGetVersionName</code></li>
<li>New: The added script command <code>fnAddToLocallyLoadedClasses</code> allows JavaRunner to use custom versions of classes from the Java classpath. This allows the user to use newer versions of these classes than those installed on the device.</li>
<li>New: JavaRunner now supports optional dx parameters</li>
<li>New: In free version small JavaRunner modules are now supported. They don't need to be created on every run anymore.</li>
<li>New: Modules and jarFiles can now be run from the project filemanager</li>
<li>New (PRO): The free Java obfuscator yGuard can now be used as custom Ant task. See the wiki page on the project website for more information.</li>
<li>New (PRO): <code>JID.iMaxDexSize</code> option in Settings to define maximum dex size used when dexing incrementally.</li>
<li>New (PRO): Script command <code>JID.fnDexJarArchiveIncrementally</code> will dex jarFiles incrementally in chunks of <code>JID.iMaxDexSize</code> KB to prevent out-of-memory errors.</li>
<li>New (PRO): JavaRunner now uses incremental dexing.</li>
<li>New (PRO): JavaRunner task can be stopped during the incremental modul creation</li>
<li>Bug fixed: The process status TextView disappeared when device was rotated while task was running</li>
<li>Bug fixed: Some minor bug fixes</li>
</ul>

<h2> version 2.5.0 (01.03.2014) </h2>
<ul>
<li>Modified: aapt has been updated to the version included in Android 4.4.2</li>
<li>New: aapt versions for x86 and mips devices</li>
<li>New (PRO): JavaRunner now supports loading of several modules. This allows running Ant with custom Ant Tasks.</li>
<li>New: Now includes android-api8.jar and a (very) simple text editor, so you can use JavaIDEdroid right out of they box without installing further things.</li>
<li>New: New files can be created based on file templates</li>
<li>New: LogCat Activity</li>
<li>Modified: Now uses the Android 4.x look and feel</li>
<li>Some minor bugs have been fixed.</li>
</ul>

<h2> version 2.1.2 (05.08.2013) </h2>
<ul>
<li>Bugfix: Fixed a problem in JavaRunner where dynamically loaded classes were not found</li>
<li>Bugfix: Variables JID.stBshVar3, JID.stBshVar4 and JID.stBshVar5 were mapped incorrectly. Fixed now.</li>
<li>Bugfix: Corrected a fault in the help</li>
</ul>

<h2> version 2.1.1 (20.06.2013) </h2>
<ul>
<li>New: Added support for Samsung's Multi Window functionality</li>
<li>Bugfix: If the free APP was started by an intent then ScriptAutoRun did not work. Fixed now.</li>
<li>Bugfix: Occasionally the PRO license check was unsuccessful with NO_REASON_AVAILABLE. Hopefully fixed now.</li>
</ul>

<h2> version 2.1.0 (05.06.2013) </h2>
<p><font color='red'>CAUTION: The default scripts have been heavily modified. Please check your own scripts! Before using the new scripts you should clear the directory bin/test/ of your projects.</font></p>

<ul>
<li>New: JavaRunner allows to run any binary Java commandline application (.jar file) without recompilation!</li>
<li>Modified: All functionality which can be accessed from BeanShell scripts are now in the class JID. The default scripts have been adjusted accordingly.</li>
<li>Modified: The output in the BeanShell and Tools tab is now already visible during execution. The current state is shown at the top.</li>
<li>Modified: JID.fnPublishProgress() no longer updates the script state. It now writes output to the console. The state is now updated with JID.fnPublishStatus()</li>
<li>New: Data can now be read from stdin. To enter data there is an entry in the context menu of the console.</li>
<li>New: Permission INTERNET added, so that Java console applications can access the Internet. JavaIDEdroid itself does not need the Internet access.</li>
<li>Bugfix: Workaround for Android bug, which causes AsyncTask.onCancelled() to be called before doInBackground() has ended. The task status can now be checked with JID.fnIsCancelled()</li>
<li>Modified: When the user exits the APP the complete Android process is killed at the end, so that the APP can recover from out-of-memory conditions.</li>
<li>Modified: The directory exclude list of the project is now relative to the project root dir. So now you can move the project without the need to change the exclude list.</li>
<li>New: Shortcut Intent to place a shortcut on Android Home Screen.</li>
<li>New: The logcat information can now be stored to a file, because on Android 4.1 (Jelly Bean) the logcat is protected.</li>
<li>Bugfix: Fixed a bug in Recent Project List</li>
</ul>

<h2> version 2.0.2 (20.03.2013) </h2>
<ul>
<li>Bugfix: On some devices the title of the tabs was not or not completely shown. Now fixed.</li>
<li>Bugfix: In the help the description of the dex library directory was missing. Now fixed.</li>
</ul>

<h2> version 2.0.1 (14.03.2013) </h2>
<ul>
<li>New: Starting with this version the free and the PRO version have been merged. The PRO features are available if you have installed the old PRO version (1.x) or the new PRO key 2.x</li>
<li>New: Very small projects are now also supported in the free version. You can also use the project templates to create a new project.</li>
<li>New: Time log can be activated for a project, so you can see how much time you spent developing the project. The current elapsed time is shown in the project tab and the time log is written to the storage card when the project is closed.</li>
<li>New (PRO): APKs can now be signed with a certificate from your truststore (JKS or BKS)</li>
<li>New (PRO): dx now supports the 'incremental' option</li>
<li>New: Option for dexlibs directory</li>
<li>Bugfix: The field 'Project APK' was not saved persistently. Now fixed.</li>
</ul>

<h2> version 1.7.2 (14.03.2013) </h2>
<ul>
<li>New (PRO): Supports activation of the PRO features of the new JavaIDEdroid 2.x</li>
</ul>

<h2> version 1.7.1 (28.11.2012) </h2>
<ul>
<li>New (PRO): VIEW Intent filter for files with extension .jip</li>
<li>Modified: Replaced VIEW Intent filter text/x-beanshell with VIEW Intent filter for files with extension .bsh. The MIME type text/x-beanshell is no longer needed and no longer supported.</li>
<li>Bugfix: Fixed bug in VIEW Intent processing</li>
<li>Bugfix: SavedInstanceState is now handled properly. The SEND Intent is no longer executed on re-create.</li>
</ul>

<h2> version 1.7.0 (15.11.2012) </h2>
<ul>
<li>New (PRO): Option for automatically re-open the last used project on program start</li>
<li>Modified: Projects can now be opened without closing the current project first</li>
<li>Bugfix: In BeanShell scripts you can now also use accented characters (e.g. german umlauts). Scripts must be saved with UTF-8 encoding.</li>
<li>Bugfix: When user aborts a task the script output was not shown. Fixed now.</li>
</ul>



&lt;H2&gt;

 version 1.6.0 (11.10.2012) 

&lt;/H2&gt;




&lt;UL&gt;




&lt;LI&gt;

New (PRO): dx now supports merging of dex files. This will help with the out-of-memory exceptions when dexing large jar libraries because too large libraries can now be dexed on the PC and then merged on the Android device.

&lt;/LI&gt;




&lt;LI&gt;

Modified (PRO): The default scripts have been modified to support dex merging

&lt;/LI&gt;




&lt;LI&gt;

New (PRO): DexMerger tool and G.ide.fnDexMerger method

&lt;/LI&gt;




&lt;LI&gt;

New: History activity

&lt;/LI&gt;




&lt;LI&gt;

New: After the first installation of the app the help activity is shown, after updates, the history activity is shown

&lt;/LI&gt;




&lt;LI&gt;

New: App can now be installed on sdcard 

&lt;/LI&gt;




&lt;LI&gt;

New: Screen (device) stays on during execution of BeanShell and Tool tasks

&lt;/LI&gt;




&lt;LI&gt;

Bugfix: Default settings were not shown when Setting activity was displayed for the first time. Fixed now.

&lt;/LI&gt;




&lt;LI&gt;

Bugfix: Background tasks can now be properly cancelled by checking G.bshTask.isCancelled() in the BeanShell scripts

&lt;/LI&gt;




&lt;/UL&gt;





&lt;H2&gt;

 version 1.5.0 (12.01.2012) 

&lt;/H2&gt;




&lt;UL&gt;




&lt;LI&gt;

 New (PRO): Support for multiple project templates

&lt;/LI&gt;




&lt;LI&gt;

 New (PRO): Menuitem for installing APK 

&lt;/LI&gt;




&lt;LI&gt;

 New (PRO): Project variable for APK filepath 

&lt;/LI&gt;




&lt;LI&gt;

 New (PRO): Create dir in project file manager 

&lt;/LI&gt;




&lt;LI&gt;

 New (PRO): Delete dir in project file manager 

&lt;/LI&gt;




&lt;LI&gt;

 New (PRO): Rename file/dir in project file manager 

&lt;/LI&gt;




&lt;LI&gt;

 New: Context menu in BeanShell and Tools tab (Top, Bottom, Copy to clipboard) 

&lt;/LI&gt;




&lt;LI&gt;

 New: Option for auto-scroll-to-bottom after script execution 

&lt;/LI&gt;




&lt;LI&gt;

 New: Option for fullscreen mode 

&lt;/LI&gt;




&lt;LI&gt;

 Modified: Made several layout changes to gain space on screen (tab height, fullscreen, run script button) 

&lt;/LI&gt;




&lt;LI&gt;

 Modified: On errors, the complete stack trace is now logged to catlog. 

&lt;/LI&gt;




&lt;LI&gt;

 Modified: Refactoring of SettingActivity 

&lt;/LI&gt;




&lt;LI&gt;

 Modified (PRO): Default scripts are now in /sdcard/.JavaIDEdroid/xx/ where xx is the version code (see Infos activity)

&lt;/LI&gt;




&lt;LI&gt;

 Modified: FileBrowser now only accepts matching picks

&lt;/LI&gt;




&lt;LI&gt;

 Modified: Removed deprecated message from ApkBuilder

&lt;/LI&gt;




&lt;LI&gt;

 Bugfix: Fixed potential crash in background processes 

&lt;/LI&gt;




&lt;LI&gt;

 Bugfix: Added fillViewport in InfoActivity 

&lt;/LI&gt;




&lt;LI&gt;

 Bugfix: Added catch for ActivityNotFoundException 

&lt;/LI&gt;




&lt;LI&gt;

 Bugfix: Fixed a display bug in the recent file list

&lt;/LI&gt;




&lt;/UL&gt;





&lt;H2&gt;

 version 1.4.1 (23.12.2011) 

&lt;/H2&gt;




&lt;UL&gt;




&lt;LI&gt;

 Modified: Updated zipsigner-lib to 1.11 to make it work again on Android 2.3.6 

&lt;/LI&gt;




&lt;/UL&gt;





&lt;H2&gt;

 version 1.4.0 (04.11.2011) 

&lt;/H2&gt;




&lt;P&gt;

 First PRO release

&lt;/P&gt;




&lt;UL&gt;




&lt;LI&gt;

 New: added info activity that shows package information

&lt;/LI&gt;




&lt;LI&gt;

 New (PRO): added project definition files to store project specific information 

&lt;/LI&gt;




&lt;LI&gt;

 New (PRO): added template project zip that is used to create a new skeleton project.

&lt;/LI&gt;




&lt;LI&gt;

 New (PRO): added BeanShell access to project infos through G.oPrj class instance

&lt;/LI&gt;




&lt;LI&gt;

 New (PRO): added recent project file list to quickly re-open project definition files

&lt;/LI&gt;




&lt;LI&gt;

 New (PRO): added filemanager to browse, view/open, edit and delete project files and run BeanShell scripts.

&lt;/LI&gt;




&lt;LI&gt;

 New (PRO): added customizable, quick change directory to filemanager

&lt;/LI&gt;




&lt;LI&gt;

 New (PRO): added default compile and build scripts. They access the information in the project file and therefore work for almost every project.

&lt;/LI&gt;




&lt;LI&gt;

 New (PRO): added project menu items to start the default scripts. If the default scripts are copied to the project root dir (and then possibly modified), the menu items will start them from there.

&lt;/LI&gt;




&lt;LI&gt;

 Modified: Replaced OI Filemanager with internal file browser 

&lt;/LI&gt;




&lt;LI&gt;

 Modified: Replaced G.oSet.stDefaultStartDir with G.oSet.stDevRootDir

&lt;/LI&gt;




&lt;LI&gt;

 Modified: Common help file for free and PRO version

&lt;/LI&gt;




&lt;LI&gt;

 Modified: Replaced some Toast messages with AlertDialog dialogs

&lt;/LI&gt;




&lt;LI&gt;

 Modified: Changed output directory in scripts from out/ to bin/

&lt;/LI&gt;




&lt;LI&gt;

 Modified (STD): Replaced Donate Activity with Upgrade Activity

&lt;/LI&gt;




&lt;LI&gt;

 Bugfix: Added check for missing working directory (missing sdcard)

&lt;/LI&gt;




&lt;LI&gt;

 Bugfix: Some Error / warning messages referenced already closed or hidden Activities. Fixed now.

&lt;/LI&gt;




&lt;LI&gt;

 Bugfix: Pressing the back button does not close the app anymore

&lt;/LI&gt;




&lt;/UL&gt;





&lt;H2&gt;

 version 1.2.0 (21.09.2011) 

&lt;/H2&gt;




&lt;UL&gt;




&lt;LI&gt;

 New: Option for log level, with which the amount of logcat messages can be controlled 

&lt;/LI&gt;




&lt;LI&gt;

 Modified: logcat messages now have a time stamp

&lt;/LI&gt;




&lt;LI&gt;

 New: more logcat messages

&lt;/LI&gt;




&lt;LI&gt;

 New: intent filter for android.intent.action.VIEW added. Use e.g. ASTRO filemanager to define MIME type text/x-beanshell and associate it with the extension .bsh. Now, clicking on a .bsh file starts JavaIDEdroid and passes the script path.

&lt;/LI&gt;




&lt;LI&gt;

 New: G.iScriptResultCode variable with which the script can report back its result code.

&lt;/LI&gt;




&lt;LI&gt;

 New: Better integration with other Android apps: ScriptAutoRun, ScriptAutoExit, WantResultText extra values for intent filter android.intent.action.SEND; ScriptResultCode, ScriptResultText extra values in result intent

&lt;/LI&gt;




&lt;LI&gt;

 New: added DonateActivity with link to [!JavaIDEdroidDonate](https://market.android.com/details?id=com.t_arn.JavaIDEdroidDonate) for those who want to make a donation

&lt;/LI&gt;




&lt;/UL&gt;





&lt;H2&gt;

 version 1.1.1 (31.08.2011) 

&lt;/H2&gt;




&lt;UL&gt;




&lt;LI&gt;

 New: Password Activity allows to set temporary passwords which can be accessed in BeanShell scripts

&lt;/LI&gt;




&lt;LI&gt;

 New: Option to log output to a file

&lt;/LI&gt;




&lt;LI&gt;

 New: Option for default directory when browsing for scripts

&lt;/LI&gt;




&lt;LI&gt;

 New: Options for variables that can be used in scripts: stAndroidJarPath, stBshVar1, stBshVar2, stBshVar3, stBshVar4, stBshVar5

&lt;/LI&gt;




&lt;LI&gt;

 New: Added german language files

&lt;/LI&gt;




&lt;LI&gt;

 Bugfix: Intent example in help file corrected. 

&lt;/LI&gt;




&lt;/UL&gt;





&lt;H2&gt;

 version 1.0.1 (25.08.2011) 

&lt;/H2&gt;




&lt;UL&gt;




&lt;LI&gt;

 Bugfix: Corrected help file. 

&lt;/LI&gt;




&lt;/UL&gt;





&lt;H2&gt;

 version 1.0.0 (22.08.2011) 

&lt;/H2&gt;




&lt;UL&gt;




&lt;LI&gt;

 New: Aapt native lib integrated. 

&lt;/LI&gt;




&lt;LI&gt;

 New: Added intent action 'android.intent.action.SEND' so that JavaIDEdroid's BeanShell scripts can be started from other apps.

&lt;/LI&gt;




&lt;/UL&gt;





&lt;H2&gt;

 version 0.7.3 (12.08.2011) 

&lt;/H2&gt;




&lt;UL&gt;




&lt;LI&gt;

 New: Modified dx.jar and sdklib.jar to not call System.exit() anymore 

&lt;/LI&gt;




&lt;LI&gt;

 Modified: Removed ExitSecurityManager which threw an Exception on Android 2.3 and higher (ExitSecurityManager is no longer supported on Android 2.3 and higher) 

&lt;/LI&gt;




&lt;/UL&gt;





&lt;H2&gt;

 version 0.7.2 (08.08.2011) 

&lt;/H2&gt;




&lt;UL&gt;




&lt;LI&gt;

 New: Added G.class which provides access to global variables and methods. NOTE: Due to this change you will nee to adjust your BeanShell Scripts! See the built-in help to learn more 

&lt;/LI&gt;




&lt;LI&gt;

 New: Made new build batch files for production and test 

&lt;/LI&gt;




&lt;LI&gt;

 Modified: Removed IntelliJ project files 

&lt;/LI&gt;




&lt;LI&gt;

 New: APK is now signed with proper certificate 

&lt;/LI&gt;




&lt;LI&gt;

 New: Added disclaimer to help 

&lt;/LI&gt;




&lt;LI&gt;

 Bugfix: Output was deleted on screen orientation change. Fixed now.

&lt;/LI&gt;




&lt;/UL&gt;





&lt;H2&gt;

 version 0.7.1 (30.06.2011) 

&lt;/H2&gt;




&lt;UL&gt;




&lt;LI&gt;

 New: Options screen now allows to set font size and font type 

&lt;/LI&gt;




&lt;LI&gt;

 Modified: better scrolling 

&lt;/LI&gt;




&lt;LI&gt;

 Modified: Reduced the application permissions to WRITE\_EXTERNAL\_STORAGE 

&lt;/LI&gt;




&lt;/UL&gt;





&lt;H2&gt;

 version 0.7.0 (02.06.2011) 

&lt;/H2&gt;




&lt;UL&gt;




&lt;LI&gt;

 Modified: Tasks now run in a separate thread 

&lt;/LI&gt;




&lt;LI&gt;

 New: While the tasks run, a ProgressDialog is showed

&lt;/LI&gt;




&lt;LI&gt;

 New: fnPublishProgress and fnToast methods in BeanShell scripts

&lt;/LI&gt;




&lt;LI&gt;

 Modified: Code refactoring (eliminated GenericAndroidTextArea and IGenericTextArea, replaced GenericTextAreaOutputStream with StringWriterOutputStream

&lt;/LI&gt;




&lt;/UL&gt;





&lt;H2&gt;

 version 0.6.2 (21.05.2011) 

&lt;/H2&gt;




&lt;UL&gt;




&lt;LI&gt;

 Modified: fnCompile() does not use the ExitSecurityManager any more. 

&lt;/LI&gt;




&lt;LI&gt;

 Bugfix: fnCompile() always returned -1. Fixed now. It now returns 0 on OK, 1 on compilation warnings, 2 on compilation errors or 3 on other errors.

&lt;/LI&gt;




&lt;LI&gt;

 Bugfix: Fixed the help message of ZipSigner

&lt;/LI&gt;




&lt;/UL&gt;





&lt;H2&gt;

 version 0.6.1 (20.05.2011) 

&lt;/H2&gt;




&lt;UL&gt;




&lt;LI&gt;

 New: Ken Ellinwoods Zipsigner-lib integrated. 

&lt;/LI&gt;




&lt;LI&gt;

 Modified: The public methods of IDE now return exit codes. 

&lt;/LI&gt;




&lt;/UL&gt;





&lt;H2&gt;

 version 0.5.1 (17.05.2011) 

&lt;/H2&gt;




&lt;UL&gt;




&lt;LI&gt;

 New: ApkBuilder integrated. Only unsigned APKs can be created. 

&lt;/LI&gt;




&lt;LI&gt;

 Modified: Only 2 tabs anymore: The BeanShell tab and a Tools tab where you can call all available tools directly 

&lt;/LI&gt;




&lt;/UL&gt;





&lt;H2&gt;

 version 0.4.1 (11.05.2011) 

&lt;/H2&gt;




&lt;UL&gt;




&lt;LI&gt;

 New: Some logs to CatLog 

&lt;/LI&gt;




&lt;LI&gt;

 Modified: BeanShell recompiled for target 1.5, so we get rid of the compilation warnings.

&lt;/LI&gt;




&lt;LI&gt;

 Modified: The last picked script file is remembered 

&lt;/LI&gt;




&lt;/UL&gt;





&lt;H2&gt;

 version 0.4.0 (04.05.2011) 

&lt;/H2&gt;




&lt;P&gt;

 First public release 

&lt;/P&gt;

