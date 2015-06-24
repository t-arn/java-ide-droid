This is how to run Java console applications on Android. To fully use JavaRunner, you need the PRO version of JavaIDEdroid. But the demo 'HelloJava' also works with the free version.

  1. Download HelloJava\_2-0-0.zip from the Download area and unzip it to a subdirectory of your development root on your Android device
  1. Go to the Tools tab of JavaIDEdroid, choose 'JavaRunner', enter the path to HelloJava.jar and press OK
  1. JavaIDEdroid now creates HelloJava.jar.dex.zip and runs it. To enter data, long press on the prompt message and choose 'Input to stdin'

If you have the PRO version of JavaIDEdroid, you can use the build.bsh to create a new build of the project after you have changed the source code.

If you have the free version only, you can use the compile.bsh in the project root to compile the source. You will then need to create the HelloWorld.jar yourself, for example with GhostCommander.