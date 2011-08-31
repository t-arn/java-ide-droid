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
  public String stDefaultStartDir;
  public boolean bLogOutput;
  public String stAndroidJarPath;
  public String stBshVar1;
  public String stBshVar2;
  public String stBshVar3;
  public String stBshVar4;
  public String stBshVar5;

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
/** Load, parse and apply the settings
 *
 * @param main the main activity
 */
  public void fnApplySettings()
//===================================================================
{
   final SharedPreferences prefs;
   float fFontSize;

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
     catch (NumberFormatException e1) { Log.e(G.stProgramName, "NumberFormatException on parsing 'font_size'"); }
     Log.i(G.stProgramName, "Setting font size to "+fFontSize);
     Log.i(G.stProgramName, "Setting font type to "+stFontType);
     stDefaultStartDir=prefs.getString("start_dir",stDefaultStartDir);
     bLogOutput=prefs.getBoolean("log_output",false);
     stAndroidJarPath=prefs.getString("android_jar_path",stAndroidJarPath);
     stBshVar1=prefs.getString("bsh_var1",stBshVar1);
     stBshVar2=prefs.getString("bsh_var2",stBshVar2);
     stBshVar3=prefs.getString("bsh_var3",stBshVar3);
     stBshVar4=prefs.getString("bsh_var4",stBshVar4);
     stBshVar5=prefs.getString("bsh_var5",stBshVar5);
   }
   catch (ClassCastException e) 
   { 
     G.fnError("SettingActivity", e); 
   }
   
   // apply the settings
   G.tabBeanshell_tvOutput.setTextSize(TypedValue.COMPLEX_UNIT_SP, fFontSize);
   G.tabTools_tvOutput.setTextSize(TypedValue.COMPLEX_UNIT_SP, fFontSize);
   if (stFontType.equals("Monospace") )
   {
     G.tabBeanshell_tvOutput.setTypeface(Typeface.MONOSPACE);
     G.tabTools_tvOutput.setTypeface(Typeface.MONOSPACE);
   }
   else
   {
     G.tabBeanshell_tvOutput.setTypeface(Typeface.DEFAULT);
     G.tabTools_tvOutput.setTypeface(Typeface.DEFAULT);
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
  stDefaultStartDir="/sdcard/";
  bLogOutput=false;
  stAndroidJarPath="";
  stBshVar1="";
  stBshVar2="";
  stBshVar3="";
  stBshVar4="";
  stBshVar5="";
} // fnSetDefaults
//===================================================================
} // SettingActivity
//##################################################################
