package com.t_arn.JavaIDEdroid;

import android.content.SharedPreferences;
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
  private static String stFontSize;
  private static String stFontType;

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
/** Load, parse and apply the settings
 *
 * @param main the main activity
 */
  public static void fnApplySettings(MainActivity main)
//===================================================================
{
   final SharedPreferences prefs;
   float fFontSize;

   // set default values
   fnSetDefaults();
   try { fFontSize = Float.parseFloat(stFontSize); } catch (NumberFormatException e1) { fFontSize=12; }

   // load the preferences
   prefs = PreferenceManager.getDefaultSharedPreferences(main);
   try { stFontSize = prefs.getString("font_size", stFontSize); }
   catch (ClassCastException e2) { Log.e(MainActivity.stProgramName, "ClassCastException on getting 'font_size'"); }
   try { stFontType = prefs.getString("font_type",stFontType); }
   catch (ClassCastException e3) { Log.e(MainActivity.stProgramName, "ClassCastException on getting 'font_type'"); }
   try { fFontSize = Float.parseFloat(stFontSize); }
   catch (NumberFormatException e4) { Log.e(MainActivity.stProgramName, "NumberFormatException on parsing 'font_size'"); }
   Log.i(MainActivity.stProgramName, "Setting font size to "+fFontSize);
   Log.i(MainActivity.stProgramName, "Setting font type to "+stFontType);
   
   // apply the settings
   main.tabBeanshell_tvOutput.setTextSize(TypedValue.COMPLEX_UNIT_SP, fFontSize);
   main.tabTools_tvOutput.setTextSize(TypedValue.COMPLEX_UNIT_SP, fFontSize);
   if (stFontType.equals("Monospace") )
   {
     main.tabBeanshell_tvOutput.setTypeface(Typeface.MONOSPACE);
     main.tabTools_tvOutput.setTypeface(Typeface.MONOSPACE);
   }
   else
   {
     main.tabBeanshell_tvOutput.setTypeface(Typeface.DEFAULT);
     main.tabTools_tvOutput.setTypeface(Typeface.DEFAULT);
   }
} // fnApplySettings
//===================================================================
/** Set the defaults for the options
 */
  private static void fnSetDefaults()
//===================================================================
{
  stFontSize="12";     // this must be a string representation of a float
  stFontType="Normal"; // set to "Normal" or "Monospace"
} // fnSetDefaults
//===================================================================
} // SettingActivity
//##################################################################
