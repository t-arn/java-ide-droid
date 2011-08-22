package com.t_arn.JavaIDEdroid;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

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
  
//===================================================================
// Global static methods
//===================================================================
  public static void fnInit (MainActivity mainActivity)
//===================================================================
  {
    G.main = mainActivity;
    G.oSet = new SettingActivity();
    G.oSet.fnApplySettings();
 } //fnInit
//===================================================================
  public static void fnError (String where, Throwable t)
//===================================================================
  {
    String errMsg="";
    if (t!=null) errMsg=t.toString();
    Log.e(G.stProgramName, errMsg);
    fnToast("Error in "+where+"!\n"+errMsg,10000);
    if (t!=null) t.printStackTrace();
  } //fnError
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
}
//##################################################################
