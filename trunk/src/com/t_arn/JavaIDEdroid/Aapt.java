package com.t_arn.JavaIDEdroid;

import android.util.Log;
import java.io.*;

//##################################################################
public class Aapt
//##################################################################
{
  private static boolean bInitialized = false;
  private static String stOutputDir = "/sdcard/.JavaIDEdroid";
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
    boolean ok = false;
    File dir = new File(stOutputDir);
    if (!dir.exists()) dir.mkdir();
    if (!dir.isDirectory())
    {
      System.err.println("Could not create directory /sdcard/.JavaIDEdroid");
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
  public int fnExecute (String args)
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
      lnr = new LineNumberReader(new FileReader(stOutputDir+"/native_stdout.txt"));
      st="";
      while(st!=null)
      {
        st = lnr.readLine();
        if (st!=null) System.out.println(st);
      }// while
      lnr.close();
      
      lnr = new LineNumberReader(new FileReader(stOutputDir+"/native_stderr.txt"));
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
