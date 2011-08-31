package com.t_arn.JavaIDEdroid;

import android.content.res.Resources;
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
  public static TextView tabBeanshell_tvOutput, tabTools_tvOutput;
  public static SettingActivity oSet;
  public static String stLogDir = "/sdcard/."+stProgramName;
  public static String stPw1, stPw2;
  private static Resources res;
  
//===================================================================
// Global static methods
//===================================================================
  public static void fnInit (MainActivity mainActivity)
//===================================================================
  {
    G.main = mainActivity;
    G.oSet = new SettingActivity();
    G.oSet.fnApplySettings();
    G.stPw1 = G.stPw2 = "";
    G.res = G.main.getResources();
 } //fnInit
//===================================================================
  public static void fnError (String where, Throwable t)
//===================================================================
  {
    String errMsg="";
    if (t!=null) errMsg=t.toString();
    Log.e(G.stProgramName, errMsg);
    fnToast(G.Rstring(R.string.err_errorIn)+" "+where+"!\n"+errMsg,10000);
    if (t!=null) t.printStackTrace();
  } //fnError
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
