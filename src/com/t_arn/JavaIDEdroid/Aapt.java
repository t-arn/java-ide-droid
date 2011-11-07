package com.t_arn.JavaIDEdroid;

import java.io.*;

//##################################################################
public class Aapt
//##################################################################
{
  private static boolean bInitialized = false;
  private native int JNImain(String args);

//===================================================================
  public Aapt ()
//===================================================================
  {
    if (!bInitialized) fnInit();
  } // constructor
//===================================================================
  private static boolean fnInit ()
//===================================================================
  {
    boolean ok = G.fnMakeLogDir(false);
    if (!ok)
    {
      System.err.println(G.Rstring(R.string.err_mkdir)+" "+G.stWorkDir);
      return false;
    }
    try
    {
      G.fnLog("i", "Loading native library aaptcomplete...");
      System.loadLibrary("aaptcomplete");
      bInitialized = true;
      ok = true;
    }
    catch (Exception e)
    {
      System.err.println(e.getMessage());
      ok = false;
    }
    return ok;
  } // fnInit
//===================================================================
  public synchronized int fnExecute (String args)
//===================================================================
  {
    int rc=99;

    G.fnLog("d", "Calling JNImain...");
    rc = JNImain (args);
    G.fnLog("d", "Result from native lib="+rc);
    fnGetNativeOutput();
    return rc;
  } // fnExecute
//===================================================================
  private void fnGetNativeOutput ()
//===================================================================
  {
    LineNumberReader lnr;
    String st="";
    
    try
    {
      lnr = new LineNumberReader(new FileReader(G.stWorkDir+"native_stdout.txt"));
      st="";
      while(st!=null)
      {
        st = lnr.readLine();
        if (st!=null) System.out.println(st);
      }// while
      lnr.close();
      
      lnr = new LineNumberReader(new FileReader(G.stWorkDir+"native_stderr.txt"));
      st="";
      while(st!=null)
      {
        st = lnr.readLine();
        if (st!=null) System.err.println(st);
      }// while
      lnr.close();
    }
    catch (Exception e)
    {
      System.err.println(e.getMessage());
    }
  } // 
//===================================================================
  public boolean isInitialized ()
//===================================================================
  {
    return bInitialized;
  } // 
//===================================================================
}
//##################################################################
