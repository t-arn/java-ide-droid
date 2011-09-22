package com.t_arn.JavaIDEdroid;

import android.content.res.Resources;
import android.text.format.Time;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import java.io.*;

//##################################################################
/** 
 * Class for global variables and methods
 */
 public class G
//##################################################################
{
  // Global variables
  public static String stProgramName="JavaIDEdroid";
  public static MainActivity main = null;
  public static IDE ide;
  public static BeanShellTask bshTask;
  public static SettingActivity oSet;
  public static String stPw1, stPw2;
  public static int iScriptResultCode;
  protected static TextView tabBeanshell_tvOutput, tabTools_tvOutput;
  protected static String stLogDir = "/sdcard/."+stProgramName;
  protected static boolean bScriptAutoRun, bScriptAutoExit, bWantResultText;
  protected static Resources res;
  
//===================================================================
// Global static methods
//===================================================================
  public static void fnInit (MainActivity mainActivity)
//===================================================================
  {
    G.main = mainActivity;
    G.stPw1 = G.stPw2 = "";
    bScriptAutoRun = bScriptAutoExit = bWantResultText = false;
    G.res = G.main.getResources();
    G.ide = new IDE();
    G.iScriptResultCode = 99;
    G.oSet = new SettingActivity();
    // CAUTION: G.tabBeanshell_tvOutput and G.tabTools_tvOutput must already be set now!
    G.oSet.fnApplySettings();
 } //fnInit
//===================================================================
  public static void fnError (String where, Throwable t)
//===================================================================
  {
    String errMsg="";
    if (t!=null) errMsg=t.toString();
    G.fnLog("e",errMsg);
    fnToast(G.Rstring(R.string.err_errorIn)+" "+where+"!\n"+errMsg,10000);
    if (t!=null) t.printStackTrace();
  } //fnError
//===================================================================
  public static int fnArrayIndexOf(String[] array, String key)
//===================================================================
  {
    int i, pos=-1;
    if ((array==null)||(key==null)) return -1;
    for (i=0;i<array.length;i++) 
    {
      if (array[i].equals(key)) 
      {
        pos=i;
        break;
      }//if
    }//for
    return pos;
  } //fnArrayIndexOf
//===================================================================
  public static void fnLog (String level, String msg)
//===================================================================
  {
    if (G.oSet.iLogLevel==0) return; // user wants no logging
    Time t = new Time();
    t.setToNow();
    msg = t.format("%Y-%m-%d %H:%M:%S") + " "+msg;
    if ((level=="v")&&(G.oSet.iLogLevel>=5)) Log.v(G.stProgramName, msg);
    if ((level=="d")&&(G.oSet.iLogLevel>=4)) Log.d(G.stProgramName, msg);
    if ((level=="i")&&(G.oSet.iLogLevel>=3)) Log.i(G.stProgramName, msg);
    if ((level=="w")&&(G.oSet.iLogLevel>=2)) Log.w(G.stProgramName, msg);
    if ((level=="e")&&(G.oSet.iLogLevel>=1)) Log.e(G.stProgramName, msg);
  } //fnLog
//===================================================================
  public static boolean fnMakeLogDir ()
//===================================================================
  {
    File dir;
    boolean ok = false;
    try
    {
      dir = new File(stLogDir);
      if (!dir.exists()) dir.mkdir();
      if (dir.isDirectory()) ok = true;
    }
    catch (Exception e)
    {
      ok = false;
    }
    return ok;
  } // fnMakeLogDir
//===================================================================
  public static int fnParseInt (String s, int defaultValue)
//===================================================================
  {
    int i;
    try
    {
      if ( s == null )
      {
        i = defaultValue;
      }  // if
      else i=Integer.parseInt(s);
    }  // try
    catch (NumberFormatException e)
    {
      i = defaultValue;
    }  // catch
    return i;
  }  // fnParseInt 
//===================================================================
  public static void fnToast (int msg_id, int msec)
//===================================================================
  {
    Toast.makeText(G.main,msg_id,msec).show();
  } 
//===================================================================
  public static void fnToast (String msg, int msec)
//===================================================================
  {
    Toast.makeText(G.main,msg,msec).show();
  } 
//===================================================================
  public static String Rstring (int id)
//===================================================================
  {
    return res.getString(id);
  } // 
//===================================================================
}
//##################################################################
