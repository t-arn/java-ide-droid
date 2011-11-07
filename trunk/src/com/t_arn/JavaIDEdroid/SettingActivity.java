package com.t_arn.JavaIDEdroid;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;

//##################################################################
/**
 * Shows the program settings and allows to change and save them.
 */
public class SettingActivity extends PreferenceActivity 
//##################################################################
{
  public String stFontSize;
  public String stFontType;
  public String stDevRootDir;
  public boolean bLogOutput;
  public String stAndroidJarPath;
  public String stBshVar1;
  public String stBshVar2;
  public String stBshVar3;
  public String stBshVar4;
  public String stBshVar5;
  public int iLogLevel;

//===================================================================
  /** Called when the activity is first created. */
  @Override
  protected void onCreate(Bundle icicle) 
//===================================================================
  {
    super.onCreate(icicle);
    this.addPreferencesFromResource(R.xml.settings);
  }
//===================================================================
  @Override 
  public void onConfigurationChanged(Configuration config)
//===================================================================
  {
    super.onConfigurationChanged(config);
  }
//===================================================================
  @Override
  protected void onResume()
//===================================================================
  {
    super.onResume();
    G.fnSetCurrentActivity(this);
  } // onResume
//===================================================================
/** Load, parse and apply the settings
 *
 * @param main the main activity
 */
  public void fnApplySettings()
//===================================================================
  {
    final SharedPreferences prefs;
    float fFontSize;
    String stLogLevel="VERBOSE";
    String [] ar_log_level;

    // set default values
    fnSetDefaults();
    try { fFontSize = Float.parseFloat(stFontSize); } catch (NumberFormatException e1) { fFontSize=12; }

    // load the preferences
    try
    {
      prefs = PreferenceManager.getDefaultSharedPreferences(G.main);
      stFontSize = prefs.getString("font_size", stFontSize);
      stFontType = prefs.getString("font_type",stFontType);
      try { fFontSize = Float.parseFloat(stFontSize); }
      catch (NumberFormatException e1) { G.fnLog("e", "NumberFormatException on parsing 'font_size'"); }
      G.fnLog("d","Setting font size to "+fFontSize);
      G.fnLog("d","Setting font type to "+stFontType);
      stDevRootDir=prefs.getString("devroot_dir",stDevRootDir);
      if (!stDevRootDir.endsWith("/")) stDevRootDir+="/";
      bLogOutput=prefs.getBoolean("log_output",false);
      stAndroidJarPath=prefs.getString("android_jar_path",stAndroidJarPath);
      stBshVar1=prefs.getString("bsh_var1",stBshVar1);
      stBshVar2=prefs.getString("bsh_var2",stBshVar2);
      stBshVar3=prefs.getString("bsh_var3",stBshVar3);
      stBshVar4=prefs.getString("bsh_var4",stBshVar4);
      stBshVar5=prefs.getString("bsh_var5",stBshVar5);
      stLogLevel = prefs.getString("log_level",stLogLevel);
      ar_log_level = G.res.getStringArray(R.array.ar_log_level);
      iLogLevel = G.fnArrayIndexOf(ar_log_level, stLogLevel);
      Log.i(G.stProgramName, "setting log level to "+iLogLevel+" ("+ar_log_level[iLogLevel]+")");
    }
    catch (ClassCastException e) 
    { 
      String msg=G.Rstring(R.string.err_errorIn)+" SettingActivity!\n"+e.toString();
      Log.e(G.stProgramName, msg);
      G.fnToast(msg,10000);
    }

    // apply the settings
    G.main.tabBeanshell_tvOutput.setTextSize(TypedValue.COMPLEX_UNIT_SP, fFontSize);
    G.main.tabTools_tvOutput.setTextSize(TypedValue.COMPLEX_UNIT_SP, fFontSize);
    if (stFontType.equals("Monospace") )
    {
      G.main.tabBeanshell_tvOutput.setTypeface(Typeface.MONOSPACE);
      G.main.tabTools_tvOutput.setTypeface(Typeface.MONOSPACE);
    }
    else
    {
      G.main.tabBeanshell_tvOutput.setTypeface(Typeface.DEFAULT);
      G.main.tabTools_tvOutput.setTypeface(Typeface.DEFAULT);
    }
  } // fnApplySettings
//===================================================================
/** Set the defaults for the options
 */
  private void fnSetDefaults()
//===================================================================
{
  stFontSize="12";     // this must be a string representation of a float
  stFontType="Normal"; // set to "Normal" or "Monospace"
  stDevRootDir="/sdcard/";
  bLogOutput=false;
  stAndroidJarPath="";
  stBshVar1="";
  stBshVar2="";
  stBshVar3="";
  stBshVar4="";
  stBshVar5="";
  iLogLevel = 5;
} // fnSetDefaults
//===================================================================
} // SettingActivity
//##################################################################
