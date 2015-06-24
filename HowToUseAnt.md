This is how to use Ant on Android (needs JavaIDEdroid PRO):
  1. Get JavaIDEdroid v2.6.0 or later from Google Play
  1. Get ant\_1-9-1.jar.dex.zip from the Download area and copy it to the storage card of your Android device
  1. Get ecj\_ant-adapter\_3-8-3.jar.dex.zip from Download area and copy it to the storage card of your Android device
  1. Get HelloJava\_2-0-0.zip from the Download area and unzip it to a subdirectory of your development root on the storage card of your Android device

In the project root of HelloJava, you now have following files:
  * build.bsh: This BeanShell script is used to configure and start Ant on Android
  * ant\_build.bat: This batch file is used to configure and start Ant on the PC
  * build\_ant.xml: This is the Ant build script which works on Android and on the PC