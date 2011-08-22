/*
Copyright (C) 2011 t-arn.com

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
See the GNU General Public License for more details. 
*/

/* Use following code to call this app from another app:
   ComponentName cn = new ComponentName("com.t_arn.JavaIDEdroid", "com.t_arn.JavaIDEdroid.MainActivity");
   Intent intent = new Intent();
   intent.setComponent(cn);
   intent.putExtra("android.intent.extra.ScriptPath", "/sdcard/build.bsh");
   intent.putExtra("android.intent.extra.minExitCode", "0");
   startActivity(intent);
*/

package com.t_arn.JavaIDEdroid;

import android.app.TabActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

//##################################################################
/**
 * This is the main Activity of JavaIDEdroid
 *
 * @since 01.06.2011
 * @author Tom Arn
 * @author www.t-arn.com
 */
public class MainActivity extends TabActivity
//##################################################################
{
  private EditText tabBeanshell_etScript, tabTools_etArgs;
  private static final int REQUEST_CODE_PICK_FILE_OR_DIRECTORY = 1;
  private static final int REQUEST_CODE_SETTINGS = 2;

//===================================================================
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
//===================================================================
  {
    try
    {
      setContentView(R.layout.main);
      TabHost tabHost=getTabHost();
      TabSpec tabBeanshell=tabHost.newTabSpec("BeanShell");
      tabBeanshell.setContent(R.id.tabBeanshell);
      tabBeanshell.setIndicator("BeanShell");
      TabSpec tabTools=tabHost.newTabSpec("Tools");
      tabTools.setContent(R.id.tabTools);
      tabTools.setIndicator("Tools");

      tabHost.addTab(tabBeanshell);
      tabHost.addTab(tabTools);

      tabBeanshell_etScript=(EditText)findViewById(R.id.tabBeanshell_etScript);
      G.tabBeanshell_tvOutput=(TextView)findViewById(R.id.tabBeanshell_tvOutput);
      
      G.ide = new IDE();
      
      tabTools_etArgs=(EditText)findViewById(R.id.tabTools_etArgs);
      G.tabTools_tvOutput=(TextView)findViewById(R.id.tabTools_tvOutput);
      
      // load and set global application variables and settings
      G.fnInit(this);
      
      // get passed data
      Bundle extras = getIntent().getExtras();
      Log.i(G.stProgramName, "getting extras");
      if (extras != null)
      {
        Log.i(G.stProgramName, "getting extra values");
        String stScript = extras.getString("android.intent.extra.ScriptPath");
        if (stScript!=null)
        {
          tabBeanshell_etScript.setText(stScript);
          tabBeanshell_btnRun (null);
        }
      }
      super.onCreate(savedInstanceState);
    }
    catch (Throwable t) { G.fnToast("Exception in onCreate!\n"+t.toString(),10000); }
  }//onCreate
//===================================================================
  @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
//===================================================================
  {
    String filename = "";
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) 
    {
      case REQUEST_CODE_PICK_FILE_OR_DIRECTORY:
        if (resultCode == RESULT_OK && data != null) filename = fnPickFile(data);
        break;
      case REQUEST_CODE_SETTINGS:
        G.oSet.fnApplySettings();
        break;
      default:
        Log.e(G.stProgramName, "ActivityResult not handled: "+requestCode);
        break;
    }
    tabBeanshell_etScript.setText(filename);
    super.onActivityResult(requestCode, resultCode, data);
  }
//===================================================================
  @Override 
  public void onConfigurationChanged(Configuration config)
//===================================================================
  {
    Log.i(G.stProgramName, "onConfigurationChanged()");
    super.onConfigurationChanged(config);
  }
//===================================================================
   @Override 
   public boolean onCreateOptionsMenu (Menu menu)
//===================================================================
  {
    getMenuInflater().inflate(R.menu.mainmenu, menu);
    return super.onCreateOptionsMenu(menu);
  }
//===================================================================
  @Override 
  public boolean onOptionsItemSelected (MenuItem item)
//===================================================================
  {
    switch (item.getItemId())
    {
      case R.id.opt_options:
        startActivityForResult(new Intent(this, SettingActivity.class), REQUEST_CODE_SETTINGS);
        return true;
      case R.id.opt_help:
        fnHelp ();
        return true;
      case R.id.opt_exit:
        finish();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
    // return false;
  } //onOptionsItemSelected
//===================================================================
  public void tabBeanShell_btnBrowse (final View view)
//===================================================================
  { 
    try
    {
      Intent intent = new Intent ("org.openintents.action.PICK_FILE");
      intent.setData(Uri.parse("file://"+tabBeanshell_etScript.getText().toString()));
      // Set fancy title and button (optional)
      intent.putExtra("org.openintents.extra.TITLE", "Choose Beanshell script");
      intent.putExtra("org.openintents.extra.BUTTON_TEXT", "OK");
      startActivityForResult(intent, REQUEST_CODE_PICK_FILE_OR_DIRECTORY);
    } 
    catch (ActivityNotFoundException e) 
    {
      // No compatible file manager was found.
      G.fnToast("OI Filemanager needs to be installed", 10000);
    }
    catch (Throwable t) { G.fnError("tabBeanshell_btnBrowse", t); }
  }//tabBeanshell_btnBrowse
//===================================================================
  public void tabBeanshell_btnRun (final View view)
//===================================================================
  {
    try
    {
      String script;
      Log.i(G.stProgramName, "Starting BeanShell script");
      fnClear(G.tabBeanshell_tvOutput);
      script=tabBeanshell_etScript.getText().toString();
      G.bshTask = new BeanShellTask();
      G.bshTask.execute(script);
    }
    catch (Throwable t) { G.fnError("tabBeanshell_btnRun", t); }
  }
//===================================================================
  public void tabTools_btnRun (final View view)
//===================================================================
  {
    try
    {
      Spinner spinner = (Spinner) findViewById(R.id.sp_tools);
      int pos = spinner.getSelectedItemPosition();
      String[] ar_tools_values = getResources().getStringArray(R.array.ar_tools);
      String stChosen = ar_tools_values[pos];
      String params = tabTools_etArgs.getText().toString();
      fnClear(G.tabTools_tvOutput);
      Log.i(G.stProgramName, "Starting "+stChosen);
      new ToolTask().execute(stChosen, params);
    }
    catch (Throwable t) { G.fnError("tabTools_btnRun", t); }
  } //tabTools_btnRun
//===================================================================
public void fnClear ()
//===================================================================
{
  try
  {
    G.tabBeanshell_tvOutput.setText("");
  }
  catch (Throwable t) { G.fnError("fnClear", t); }
}//fnClear
//===================================================================
  protected void fnClear (TextView tv)
//===================================================================
  {
    try
    {
      tv.setText("");
    }
    catch (Throwable t) { G.fnError("fnClear", t); }
  }//fnClear
//===================================================================
  private void fnHelp ()
//===================================================================
  {
    final Intent i = new Intent (this, HelpActivity.class);
    Log.i(G.stProgramName, "Starting help");
    startActivity(i);
  } //fnHelp
//===================================================================
  private String fnPickFile (Intent data)
//===================================================================
  {
    String filename;
    if (data==null) return "";
    // obtain the filename
    filename = data.getDataString();
    if (filename != null) 
    {
      // Get rid of URI prefix:
      if (filename.startsWith("file://")) filename = filename.substring(7);
    }
    else filename = "";
    return filename;
  } //fnPickFile
//===================================================================
}//MainActivity
//##################################################################
