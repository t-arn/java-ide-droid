package com.t_arn.JavaIDEdroid;

import android.util.Log;
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
    boolean ok = G.fnMakeLogDir();
    if (!ok)
    {
      System.err.println(G.Rstring(R.string.err_mkdir)+" "+G.stLogDir);
      return false;
    }
    try
    {
      Log.i(G.stProgramName, "Loading native library aaptcomplete...");
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

    Log.i(G.stProgramName, "Calling JNImain...");
    rc = JNImain (args);
    Log.i(G.stProgramName, "Result from native lib="+rc);
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
      lnr = new LineNumberReader(new FileReader(G.stLogDir+"/native_stdout.txt"));
      st="";
      while(st!=null)
      {
        st = lnr.readLine();
        if (st!=null) System.out.println(st);
      }// while
      lnr.close();
      
      lnr = new LineNumberReader(new FileReader(G.stLogDir+"/native_stderr.txt"));
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
