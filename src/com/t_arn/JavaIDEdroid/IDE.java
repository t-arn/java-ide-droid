package com.t_arn.JavaIDEdroid;

import java.io.*;
import com.t_arn.lib.io.StringWriterOutputStream;

//##################################################################
/** This class provides access to the Java development tools.
 * 
 * @since 01.06.2011
 * @author Tom Arn
 * @author www.t-arn.com
 * @version 0.6
 */
public class IDE
//##################################################################
{
  private	ExitSecurityManager esm;

//===================================================================
public IDE () 
//===================================================================
{
  esm = new ExitSecurityManager();
}
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
  int i, rc = -99;
  try
  {
    // prevent compiler from exiting the VM
    System.setSecurityManager(esm);
    esm.setCanExit(false);
    esm.ExitValue = 255;
    // call ApkBuilder
    start = System.currentTimeMillis();
    System.out.println("ApkBuilder arguments:");
    for (i=0;i<args.length;i++) System.out.println(args[i]);
    System.out.println("");
    com.android.sdklib.build.ApkBuilderMain.main(args);
    rc = 0;
  }
  catch (ExitSecurityManager.Exception e)
  {
    rc = esm.ExitValue;
    System.out.println("Preventing VM exit...");
  }
  catch (Throwable t)
  {
    System.out.println("Error occurred!\n");
    t.printStackTrace();
  }
  esm.setCanExit(true);
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
    System.out.println("Error occurred!\n");
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
    // prevent app from exiting the VM
    System.setSecurityManager(esm);
    esm.setCanExit(false);
    esm.ExitValue = 255;
    // show arguments
    start = System.currentTimeMillis();
    System.out.println("Dx arguments:");
    for (i=0;i<args.length;i++) System.out.println(args[i]);
    System.out.println("");
    // start dx
    com.android.dx.command.Main.main(args);
    rc = 0;
  }
  catch (ExitSecurityManager.Exception e)
  {
    rc = esm.ExitValue;
    System.out.println("Preventing VM exit...");
  }
  catch (Throwable t)
  {
    System.out.println("Error occurred!\n");
    t.printStackTrace();
  }
  esm.setCanExit(true);
  System.out.println("\nDone in "+(System.currentTimeMillis()-start)/1000+" sec.\n");
  System.out.println("ExitValue: "+rc);
  return rc;
} //fnDx
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
    System.out.println("Error occurred!\n");
    t.printStackTrace();
  }
  finally
  {
    System.out.println("\nTotal script run time: "+(System.currentTimeMillis()-start)/1000+" sec.\n");
  }
}//fnRunBeanshellScript
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
  int i, rc;
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
    rc = 255;
    System.out.println("Error occurred!\n");
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
