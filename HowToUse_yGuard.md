This wiki explains how to use the free Java obfuscator yGuard on Android (needs JavaIDEdroid PRO).
First, you need to create the yGuard module which includes a bugfix for Android's JarOutputStream class:
  1. Get JavaIDEdroid v2.6.0 or later from Google Play
  1. Get HelloJava\_2-0-0.zip from the Download area and unzip it to a subdirectory of your development root on the storage card of your Android device
  1. Get JarOutputStreamFix\_1-0-0.zip from the Download area and save it as /sdcard/.JavaIDEdroid/JarOutputStreamFix\_1-0-0.zip
  1. Download yguard-2.5.1.zip from http://www.yworks.com/
  1. Extract yguard.jar from yguard-2.5.1.zip/yguard-2.5.1/lib/ and save it as /sdcard/.JavaIDEdroid/yguard\_2-5-1.jar
  1. Open the HelloJava project and run the script 'create\_yguard\_module.bsh' in the project root
  1. Move the created module yguard\_2-5-1\_androidFix.jar.dex.zip to where you want it, e.g. the JavaIDEdroid\_modules/ subdirectory of your development root (which is where I store my modules)

To use yGuard, you need to get Ant running on your Android device, since yGuard is implemented as Ant Custom Task:
  1. Get the Ant module: HowToUseAnt
  1. Open the HelloJava project and edit the build.bsh: Set `bUseYguard=true` at the beginning of the script. Adjust the paths in the script to fit your environment.
  1. Run the build.bsh script. This will create the HelloJava.jar and its obfuscated version HelloJava\_obf.jar in the project root directory