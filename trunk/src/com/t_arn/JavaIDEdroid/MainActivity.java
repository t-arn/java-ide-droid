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

/*  Use following code to call this app from another app:

    ComponentName cn = new ComponentName("com.t_arn.JavaIDEdroid", "com.t_arn.JavaIDEdroid.MainActivity");
    Intent intent = new Intent("android.intent.action.SEND");
    intent.setComponent(cn);
    intent.putExtra("android.intent.extra.ScriptPath", "/mnt/sdcard/!Daten/Android/com/t_arn/HelloAndroid/0_build-debug.bsh");
    intent.putExtra("android.intent.extra.ScriptAutoRun", true);  // default = false
    intent.putExtra("android.intent.extra.ScriptAutoExit", true); // default = false
    intent.putExtra("android.intent.extra.WantResultText", true); // default = false
    startActivityForResult(intent,123);
*/

package com.t_arn.JavaIDEdroid;

import android.app.TabActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
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
  protected void onCreate(Bundle savedInstanceState)
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
      G.tabBeanshell_tvOutput=(TextView)findViewById(R.id.tabBeanshell_tvOutput); // must be set before G.fnInit !!
            
      tabTools_etArgs=(EditText)findViewById(R.id.tabTools_etArgs);
      G.tabTools_tvOutput=(TextView)findViewById(R.id.tabTools_tvOutput); // must be set before G.fnInit !!
      
      // load and set global application variables and settings
      G.fnInit(this);
      fnGetIntentData();
      
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

    switch (requestCode) 
    {
      case REQUEST_CODE_PICK_FILE_OR_DIRECTORY:
        if (resultCode == RESULT_OK && data != null) filename = fnPickFile(data);
        break;
      case REQUEST_CODE_SETTINGS:
        G.oSet.fnApplySettings();
        break;
      default:
        G.fnLog("e", "ActivityResult not handled: "+requestCode);
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
    G.fnLog("d","onConfigurationChanged()");
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
  protected void onPause() 
//===================================================================
  {
    G.fnLog("d","onPause()");
    if (isFinishing()) 
    {
      G.stPw1 = G.stPw2 = "";
      G.fnLog("i", "finishing...");
    }
    super.onPause();
  } // onPause
//===================================================================
  @Override 
  public boolean onOptionsItemSelected (MenuItem item)
//===================================================================
  {
    switch (item.getItemId())
    {
      case R.id.opt_donate:
        fnDonate ();
        return true;
      case R.id.opt_exit:
        finish();
        return true;
      case R.id.opt_help:
        fnHelp ();
        return true;
      case R.id.opt_options:
        startActivityForResult(new Intent(this, SettingActivity.class), REQUEST_CODE_SETTINGS);
        return true;
      case R.id.opt_passwords:
        startActivity(new Intent (this, PasswordActivity.class));
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
    // return false;
  } //onOptionsItemSelected
//===================================================================
  @Override 
  protected void onResume()
//===================================================================
  {
    try
    {
      super.onResume();
      G.fnLog("d", "onResume() start");
      // run script
      if (G.bScriptAutoRun)
      {
        G.bScriptAutoRun = false;
        tabBeanshell_btnRun (null);
      }
      G.fnLog("d", "onResume() end");
    }//try
    catch (Throwable t) { G.fnToast("Exception in onResume!\n"+t.toString(),10000); }
  } // onResume
//===================================================================
  public void tabBeanShell_btnBrowse (final View view)
//===================================================================
  { 
    String startDir;
    
    try
    {
      startDir = tabBeanshell_etScript.getText().toString();
      if (startDir.equals("")) startDir=G.oSet.stDefaultStartDir;
      Intent intent = new Intent ("org.openintents.action.PICK_FILE");
      intent.setData(Uri.parse("file://"+startDir));
      // Set fancy title and button (optional)
      intent.putExtra("org.openintents.extra.TITLE", G.Rstring(R.string.msg_choose_script));
      intent.putExtra("org.openintents.extra.BUTTON_TEXT", "OK");
      startActivityForResult(intent, REQUEST_CODE_PICK_FILE_OR_DIRECTORY);
    } 
    catch (ActivityNotFoundException e) 
    {
      // No compatible file manager was found.
      G.fnToast(G.Rstring(R.string.err_no_oifm), 10000);
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
      G.fnLog("d", "Starting BeanShell script");
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
      String[] ar_tools_values = G.res.getStringArray(R.array.ar_tools);
      String stChosen = ar_tools_values[pos];
      String params = tabTools_etArgs.getText().toString();
      fnClear(G.tabTools_tvOutput);
      G.fnLog("d", "Starting "+stChosen);
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
  private void fnDonate ()
//===================================================================
  {
    final Intent i = new Intent (this, DonateActivity.class);
    G.fnLog("d", "Starting donate");
    startActivity(i);
  } //fnDonate
//===================================================================
  private void fnGetIntentData()
//===================================================================
  {
    String stScript=null;
    Uri uri;
    
    try
    {
    // get passed data
      Intent intent = getIntent();
      Bundle extras = intent.getExtras();
      String action = intent.getAction();
      if (action==null) action="";
      if (action.equals(Intent.ACTION_VIEW)||action.equals(Intent.ACTION_EDIT))
      {
        uri=intent.getData();
        if (uri!=null) stScript=uri.getPath();
      }
      G.fnLog("d", "getting extras");
      if (extras != null)
      {
        G.fnLog("d", "getting extra values");
        if (stScript==null) stScript = extras.getString("android.intent.extra.ScriptPath");
        G.bScriptAutoRun = extras.getBoolean("android.intent.extra.ScriptAutoRun", false);
        G.bScriptAutoExit = extras.getBoolean("android.intent.extra.ScriptAutoExit", false);
        G.bWantResultText = extras.getBoolean("android.intent.extra.WantResultText", false);
      }
      if (stScript!=null) tabBeanshell_etScript.setText(stScript);
      G.fnLog("d", "fnGetIntentData done.");
    } catch (Throwable t) { G.fnError("fnGetIntentData", t); }
  } // fnGetIntentData
//===================================================================
  private void fnHelp ()
//===================================================================
  {
    final Intent i = new Intent (this, HelpActivity.class);
    G.fnLog("d", "Starting help");
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
  protected void fnSetResult() 
//===================================================================
  {
    Intent intent = new Intent();
    G.fnLog("d","setting result extra:android.intent.extra.ScriptResultCode");
    intent.putExtra("android.intent.extra.ScriptResultCode", G.iScriptResultCode);
    if (G.bWantResultText) 
    {
      G.fnLog("d","setting extra:android.intent.extra.ResultText");
      intent.putExtra("android.intent.extra.ResultText", G.tabBeanshell_tvOutput.getText().toString());
    }
    setResult(RESULT_OK, intent);
  } // fnSetResult
//===================================================================
}//MainActivity
//##################################################################
