package com.t_arn.JavaIDEdroid;

import java.io.*;
import com.t_arn.lib.io.StringWriterOutputStream;

//##################################################################
/** This class provides access to the Java development tools.
 * 
 * @since 01.06.2011
 * @author Tom Arn
 * @author www.t-arn.com
 */
public class IDE
//##################################################################
{
  
//===================================================================
public IDE () 
//===================================================================
{
  
}
//===================================================================
public int fnAapt (String commandLine)
//===================================================================
{
  return fnAapt(fnTokenize(commandLine));
} //
//===================================================================
public int fnAapt (String[] args)
//===================================================================
{
  long start=0;
  int i, rc = 99, apiLevel;
  String stCommandLine;
  Aapt oAapt;
  try
  {
    // check API level
    apiLevel = android.os.Build.VERSION.SDK_INT;
    if (apiLevel < 9)
    {
      System.out.println(G.Rstring(R.string.msg_aapt_build));
      System.out.println(G.Rstring(R.string.msg_aapt_your_api)+" "+apiLevel);
      System.out.println(G.Rstring(R.string.msg_aapt_warning));
    }
    
    if (G.fnCheckWorkDir(false)==false)
    {
      System.out.println(G.Rstring(R.string.msg_workdir_missing));
      return 99;
    }
    
    // show arguments
    start = System.currentTimeMillis();
    stCommandLine="aapt";
    for (i=0;i<args.length;i++) stCommandLine += "\t"+args[i];
    System.out.println("");
    // start aapt
    oAapt = new Aapt ();
    if (!oAapt.isInitialized()) return 2;
    rc=oAapt.fnExecute(stCommandLine);
  }
  catch (Throwable t)
  {
    rc = 99;
    System.err.println("Error occurred!\n"+t.getMessage());
    t.printStackTrace();
  }
  System.out.println("\nDone in "+(System.currentTimeMillis()-start)/1000+" sec.\n");
  System.out.println("ExitValue: "+rc);
  return rc;
} //fnAapt
//===================================================================
public int fnApkBuilder (String commandLine) 
//===================================================================
{
  return fnApkBuilder (fnTokenize(commandLine));
}
//===================================================================
public int fnApkBuilder (String[] args) 
//===================================================================
{
  long start=0;
  int i, rc = 99;
  try
  {
    // show arguments
    start = System.currentTimeMillis();
    System.out.println("ApkBuilder arguments:");
    for (i=0;i<args.length;i++) System.out.println(args[i]);
    System.out.println("");
    // call ApkBuilder
    rc = com.android.sdklib.build.ApkBuilderMain.main(args);
  }
  catch (Throwable t)
  {
    rc = 99;
    System.out.println("Error occurred!\n"+t.getMessage());
    t.printStackTrace();
  }
  System.out.println("\nDone in "+(System.currentTimeMillis()-start)/1000+" sec.\n");
  System.out.println("ExitValue: "+rc);
  return rc;
}//fnApkBuilder
//===================================================================
public int fnCompile (String commandLine) 
//===================================================================
{
  return fnCompile (fnTokenize(commandLine));
}
//===================================================================
public int fnCompile (String[] args) 
//===================================================================
{
  long start=0;
  int i, rc=99;
  boolean ok;
  org.eclipse.jdt.internal.compiler.batch.Main ecjMain;
  
  try
  {
    start = System.currentTimeMillis();
    System.out.println("Compilation arguments:");
    for (i=0;i<args.length;i++) System.out.println(args[i]);
    System.out.println("");
    // call compiler
    // new Main(new PrintWriter(System.out), new PrintWriter(System.err), true/*systemExit*/, null/*options*/, null/*progress*/).compile(argv);
    ecjMain = new org.eclipse.jdt.internal.compiler.batch.Main(new PrintWriter(System.out), new PrintWriter(System.err), false/*noSystemExit*/, null/*options*/, null/*progress*/);
    ok = ecjMain.compile(args);
    if (ok) rc = 0;
    else rc = 3;
    if (ecjMain.globalWarningsCount>0) rc = 1;
    if (ecjMain.globalErrorsCount>0) rc = 2;
  }
  catch (Throwable t)
  {
    rc = 99;
    System.out.println("Error occurred!\n"+t.getMessage());
    t.printStackTrace();
  }
  System.out.println("\nDone in "+(System.currentTimeMillis()-start)/1000+" sec.\n");
  System.out.println("ExitValue: "+rc);
  return rc;
}//fnCompile
//===================================================================
public int fnDx (String commandLine) 
//===================================================================
{
  return fnDx (fnTokenize(commandLine));
}
//===================================================================
public int fnDx (String[] args)
//===================================================================
{
  long start=0;
  int i, rc=99;
  try
  {
    // show arguments
    start = System.currentTimeMillis();
    System.out.println("Dx arguments:");
    for (i=0;i<args.length;i++) System.out.println(args[i]);
    System.out.println("");
    // start dx
    rc = com.android.dx.command.Main.main(args);
  }
  catch (Throwable t)
  {
    rc = 99;
    System.out.println("Error occurred!\n"+t.getMessage());
    t.printStackTrace();
  }
  System.out.println("\nDone in "+(System.currentTimeMillis()-start)/1000+" sec.\n");
  System.out.println("ExitValue: "+rc);
  return rc;
} //fnDx
//===================================================================
public synchronized void fnLogOutput (StringWriterOutputStream swos)
//===================================================================
{
  boolean ok;
  FileWriter fw;
  String stOut;
  
  try
  {
    if (swos==null) return;
    ok = G.fnMakeLogDir(false);
    if (!ok)
    {
      System.err.println(G.Rstring(R.string.err_mkdir)+" "+G.stWorkDir);
      return;
    }
    fw = new FileWriter (G.stWorkDir+"LogOutput.txt",false);
    stOut = swos.toString().replace("\n", "\r\n");
    fw.write(stOut);
    fw.close();
  }//try
  catch (Throwable t)
  {
    System.out.println("Error while saving output:\n"+t.getMessage());
  }//catch
} // fnLogOutput
//===================================================================
public void fnRedirectOutput (StringWriterOutputStream swos) 
//===================================================================
{
  // redirect stdout and stderr
  System.setOut(new PrintStream(swos));
  System.setErr(new PrintStream(swos));
}
//===================================================================
public void fnRunBeanshellScript (bsh.Interpreter i, String script) 
//===================================================================
{
  long start=0;
  try
  {
    start = System.currentTimeMillis();
    System.out.println("Running script "+script+"\n");
    if (new File(script).exists()) i.source(script);
    else System.out.println("Script does not exist.");
  }
  catch (Throwable t)
  {
    System.out.println("Error occurred!\n"+t.getMessage());
    t.printStackTrace();
  }
  finally
  {
    System.out.println("\nTotal script run time: "+(System.currentTimeMillis()-start)/1000+" sec.\n");
  }
}// fnRunBeanshellScript
//===================================================================
public int fnSignApk (String commandLine)
//===================================================================
{
  return fnSignApk(fnTokenize(commandLine));
} //
//===================================================================
public int fnSignApk (String[] args)
//===================================================================
{
  long start=0;
  int i, rc = 99;
  try
  {
    // show arguments
    start = System.currentTimeMillis();
    System.out.println("SignApk arguments:");
    for (i=0;i<args.length;i++) System.out.println(args[i]);
    System.out.println("");
    // start SignApk
    rc = SignApk.main(args);
  }
  catch (Throwable t)
  {
    rc = 99;
    System.out.println("Error occurred!\n"+t.getMessage());
    t.printStackTrace();
  }
  System.out.println("\nDone in "+(System.currentTimeMillis()-start)/1000+" sec.\n");
  return rc;
} //fnSignApk
//===================================================================
public static String[] fnTokenize (String commandLine) 
//===================================================================
{
  return org.eclipse.jdt.internal.compiler.batch.Main.tokenize(commandLine);
}
//===================================================================

} // IDE
//##################################################################
