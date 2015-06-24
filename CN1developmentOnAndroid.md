# Introduction #

[CodenameOne](https://code.google.com/p/codenameone/) (CN1) development normally is done on the deskop computer using Eclipse, NetBeans or IntelliJ with the CodenameOne plugin.

But now, you can also develop CN1 apps on an Android device!


## Motivation ##
Why would you want to develop on Android devices?<br>
For me, Android tablets are better portable mobile development devices than Windows based Notebooks because:<br>
<ul><li>I especially like the instant ON / OFF feature on Android. Since apps are designed for this, you'll more likely not loose data due to instant OFF (for example, when I have to run off a train or bus at a stop)<br>
</li><li>The battery runtime is longer on Android<br>
</li><li>The performance of light Windows tablets is normally not so great, especially when you run applications that are meant to run on high-performance desktops<br>
</li><li>Android tablets are cheaper<br>
</li><li>With an Android tablet you can test your builds right on the real device</li></ul>

And, of course, I like to drive JavaIDEdroid to its limits :-)<br>
<br>
<br>
<h2>So, what is working on Android ?</h2>
<ul><li>Editing CN1 source code: Use any IDE (e.g. AIDE or BrightMIDE) or text editor (e.g. Quoda or Jota+)<br>
</li><li>Building CN1 projects: JavaRunner feature of JavaIDEdroid<br>
</li><li>Sending CN1 builds to the CN1 build server: JavaRunner feature of JavaIDEdroid<br>
</li><li>Downloading the builds: Web Browser on your Android device<br>
</li><li>Creating CN1 builds offline on your Android device: JavaRunner feature of JavaIDEdroid<br>
</li><li>Installing the builds: built-in Android package Manager<br>
</li><li>Testing the builds: use your real Android device</li></ul>

<h2>What is not (yet) working on Android ?</h2>
<ul><li>CN1 Simulator<br>
</li><li>CN1 Designer</li></ul>

<h2>How to work around the limitations:</h2>
For the <b>Simulator</b>, unfortunately, there is no fully equivalent alternative. Of course, you can use your Android development device to create a build (even offline!) and test the build. You have the real device to test, but you can only test the Android platform. There's no way to test the other platforms like iPhone or Windows Phone.<br>
<br>
With <b>Designer</b>, you create binary resource files which handle following things:<br>
<ol><li>Themes<br>
</li><li>Images<br>
</li><li>Fonts<br>
</li><li>Data files<br>
</li><li>GUIs<br>
</li><li>Localization</li></ol>

The current solution to Point 1 to 3 is to still use the binary src/theme.res file which you will still need to create on the desktop computer. But since you don't need to change these things very often, you can work most of the time without your desktop computer.<br>
<br>
For <b>creating GUIs</b> there is Steve Hannah's <a href='http://sjhannah.com/blog/?p=345'>CN1ML Parser</a> which will use cn1ml files to create GUIs in Java source code. cn1ml files are HTML files with additional CN1ML elements.<br>
<br>
For <b>Localization</b> and <b>data files</b>, you can use the standard res/theme.xml along with the utility class com.t_arn.lib.ui.util.taResources which offers the same interface as com.codename1.ui.util.Resources<br>
<br>
<br>
<h1>How to set up the CN1 tool chain on Android</h1>

You'll need following tools:<br>
<br>
1. JavaIDEdroidPRO (JID)<br>
<br>
If you are new to JavaIDEdroid, follow the HowToGetStarted wiki to set it up correctly before you go on!<br>
<br>
<br>
2. The JID module ant_1-9-1.jar.dex.zip<br>
3. The JID module openjdk6-javac.jar.dex.zip or ecj_ant-adapter_3-8-3.jar.dex.zip<br>
<br>
With the above, you'll have Ant running on Android.<br>
You need the PRO version of JavaIDEdroid because the needed JavaRunner support is very limited in the free version.<br>
<br>
4. The JID module BuildClientSupport_1-0-0.jar.dex.zip<br>
<br>
The BuildClientSupport module implements Java classes used by CodeNameOneBuildClient which are missing on Android, like the JOptionPane. On Android, there is no AWT and no Swing, so GUI classes are replaced with a console-based equivalents. To input data during JID script execution, you can long-press the output area and<br>
then choose 'Input to stdin'<br>
<br>
5. The JID module CN1MLParser_0-1-7.jar.dex.zip<br>
6. The JID module jsoup-1.8.2-SNAPSHOT.jar.dex.zip<br>
7. The JID module CN1MLcli_1-0-0(9).jar.dex.zip (commandline interface for CN1MLParser)<br>
<br>
With this, you can create GUIs using Steve Hannah's CN1ML parser.<br>
<br>
8. The BeanShell script build.bsh<br>
<br>
The script controls the JID build process and passes data to the Ant build process. You will need to adjust the variables at the beginning of the script to fit your environment. Use the JID <b>internal</b> editor for this because all BeanShell scripts are protected with a hash which will be missing/wrong when you use an external editor.<br>
<br>
9. The Android offline build template AndroidTemplate_SB20141206.zip<br>
<br>
This archive contains the CN1 Android port, the AndroidStub, an AndroidManifest.xml template and other files needed to create an APK offline on your device.<br>
<br>
You will need to copy the build.bsh script to the root directory of every CN1 project. The other tools you only need once - I put them in the 'JavaIDEdroid_modules' subdirectory of my development root directory.<br>
<br>
You can get the modules from the JavaIDEdroid download area at <a href='https://code.google.com/p/java-ide-droid/'>https://code.google.com/p/java-ide-droid/</a> --> Downloads<br>
JID modules normally are protected with a hash for security reasons.<br>
Since the hash contains the developer's personal salt, I cannot distribute modules with hashes - you wouldn't be able to use them.<br>
So, the modules in the download area do not contain a hash and you will<br>
need to approve the modules once at first run time.<br>
<br>
Of course, you can also build all the modules by yourself.<br>
Follow the instructions in the JID help about "Modules and JavaRunner".<br>
<br>
To get a working copy of the build.bsh script, download the source code<br>
of the sample project <b>AndroidDevDemo.zip</b> from the JavaIDEdroid download area. In this project, you'll also find the Ant build file build-jid.xml which is a customized version of the standard build file build.xml. The file build-jid.xml needs to be present in the root directory of every CN1 project.<br>
<br>
<br>
<h1>How to develop CN1 applications on Android</h1>
Download the source code of the sample app AndroidDevDemo from the JavaIDEdroid download area and you'll find the instructions in the help file in /res/theme/help-en.html or just click <a href='http://www.t-arn.com/software/CN1/AndroidDevDemo-en.html'>here</a>.<br>
<br>
<br>
<h1>Support</h1>
If you have questions, please post them in the JID support forum at <a href='https://groups.google.com/forum/#!forum/java-ide-droid'>https://groups.google.com/forum/#!forum/java-ide-droid</a> <br>
Do <b>not</b> post them in the Codename One forum because I do <b>dot</b> monitor that forum.