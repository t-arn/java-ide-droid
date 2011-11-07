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
    intent.putExtra("android.intent.extra.ScriptPath", "/sdcard/AndroidDev/mydomain/HelloWorld/build.bsh");
    intent.putExtra("android.intent.extra.ProjectFilePath", "/sdcard/AndroidDev/mydomain/HelloWorld/HelloWorld.jip"); // PRO version only
    intent.putExtra("android.intent.extra.ScriptAutoRun", true);  // default = false
    intent.putExtra("android.intent.extra.ScriptAutoExit", true); // default = false
    intent.putExtra("android.intent.extra.WantResultText", true); // default = false
    startActivityForResult(intent,123);
*/

package com.t_arn.JavaIDEdroid;

import android.app.TabActivity;
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

import java.io.File;

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
  public TextView tabBeanshell_tvOutput, tabTools_tvOutput;
  public EditText tabBeanshell_etScript, tabTools_etArgs;
  private boolean bCreated;
  private TabHost tabHost;
  private static final int REQUEST_CODE_PICK_BSH_FILE = 1;
  private static final int REQUEST_CODE_SETTINGS = 2;

//===================================================================
  /** Called when the activity is first created. */
  @Override
  protected void onCreate(Bundle savedInstanceState)
//===================================================================
  { 
    try
    {
      bCreated = false;
      G.fnInit(this);
      setContentView(R.layout.main);
      tabHost=getTabHost();
      TabSpec tabBeanshell=tabHost.newTabSpec(G.Rstring(R.string.tab_beanshell));
      tabBeanshell.setContent(R.id.tabBeanshell);
      tabBeanshell.setIndicator(G.Rstring(R.string.tab_beanshell));
      TabSpec tabTools=tabHost.newTabSpec(G.Rstring(R.string.tab_tools));
      tabTools.setContent(R.id.tabTools);
      tabTools.setIndicator(G.Rstring(R.string.tab_tools));

      tabHost.addTab(tabBeanshell);
      tabHost.addTab(tabTools);

      tabBeanshell_etScript=(EditText)findViewById(R.id.tabBeanshell_etScript);
      tabBeanshell_tvOutput=(TextView)findViewById(R.id.tabBeanshell_tvOutput);
            
      tabTools_etArgs=(EditText)findViewById(R.id.tabTools_etArgs);
      tabTools_tvOutput=(TextView)findViewById(R.id.tabTools_tvOutput);
      
      // load and set global application variables and settings
      G.oSet.fnApplySettings();
      fnGetIntentData();
      
      super.onCreate(savedInstanceState);
      bCreated = true;
    }
    catch (Throwable t) { G.fnToast("Exception in onCreate!\n"+t.toString(),10000); }
  }//onCreate
//===================================================================
  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data)
//===================================================================
  {
    String filename = "";
    Bundle extras;

    try
    {
      if (this.bCreated) G.fnSetCurrentActivity(this);
      switch (requestCode) 
      {
        case REQUEST_CODE_PICK_BSH_FILE:
          if ( (resultCode != RESULT_OK) || (data==null) ) break;
          extras = data.getExtras();
          filename = extras.getString("android.intent.extra.chosenFile");
          tabBeanshell_etScript.setText(filename);
          break;
        case REQUEST_CODE_SETTINGS:
          G.oSet.fnApplySettings();
          if (!(new File(G.oSet.stDevRootDir).isDirectory()))
          {
            G.oSet.stDevRootDir="/sdcard/";
            G.fnAlertDialog(null, G.DIALOG_INVALID_DEVROOT, R.string.tit_invalid_data, R.string.msg_invalid_devroot, R.string.btnOK, 0);
          }
          break;
        default:
          G.fnLog("e", "ActivityResult not handled. RequestCode: "+requestCode+", resultCode: "+resultCode);
          break;
      } // switch
      super.onActivityResult(requestCode, resultCode, data);
    } // try
    catch (Throwable t) { G.fnError("onActivityResult", t); }
  } // onActivityResult
//===================================================================
  @Override 
  public void onBackPressed()
//===================================================================
  {
    G.fnLog("d","onBackPressed()");
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
  public boolean onOptionsItemSelected (MenuItem item)
//===================================================================
  {
    try
    {
      switch (item.getItemId())
      {
        case R.id.opt_upgrade:
          fnUpgrade ();
          return true;
        case R.id.opt_exit:
          finish();
          return true;
        case R.id.opt_help:
          fnHelp ();
          return true;
        case R.id.opt_infos:
          startActivity(new Intent (this, InfoActivity.class));
          return true;
        case R.id.opt_passwords:
          startActivity(new Intent (this, PasswordActivity.class));
          return true;
        case R.id.opt_settings:
          fnSettings(); 
          return true;
        default:
          return super.onOptionsItemSelected(item);
      }//switch
    }//try
    catch (Throwable t) { G.fnError("onOptionsItemSelected", t); }
    return true;
  } //onOptionsItemSelected
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
  protected void onResume()
//===================================================================
  {
    try
    {
      G.fnLog("d", "onResume() start");
      super.onResume();
      G.fnSetCurrentActivity(this);
      if (G.fnIsFirstOpen())
      {
        fnUpgrade();
      }
      else
      {
        // run script
        if (G.bScriptAutoRun)
        {
          G.bScriptAutoRun = false;
          fnSelectTab(R.id.tabBeanshell);
          tabBeanshell_btnRun (null);
        }
      }
      G.fnLog("d", "onResume() end");
    }//try
    catch (Throwable t) { G.fnError("onResume", t); }
  } // onResume
//===================================================================
  public void tabBeanShell_btnBrowse (final View view)
//===================================================================
  { 
    String startDir;
    int idx;
    
    try
    {
      startDir = tabBeanshell_etScript.getText().toString();
      idx = startDir.lastIndexOf('/');
      if (idx==-1) startDir=G.oSet.stDevRootDir;
      else startDir = startDir.substring(0,idx+1);
      Intent intent = new Intent (this, FileBrowserActivity.class);
      intent.putExtra("android.intent.extra.Title", G.Rstring(R.string.msg_choose_script));
      intent.putExtra("android.intent.extra.DirPath", startDir);
      intent.putExtra("android.intent.extra.Pattern", ".*\\.bsh");
      startActivityForResult(intent, REQUEST_CODE_PICK_BSH_FILE);

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
      fnClear(tabBeanshell_tvOutput);
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
      fnClear(tabTools_tvOutput);
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
      tabBeanshell_tvOutput.setText("");
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
      if (stScript!=null) 
      {
        tabBeanshell_etScript.setText(stScript);
        fnSelectTab(R.id.tabBeanshell);
      }
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
    public void fnSelectTab (int id)
//===================================================================
  {
    switch (id)
    {
      case R.id.tabBeanshell:
        tabHost.setCurrentTab (0);
        break;
      case R.id.tabTools:
        tabHost.setCurrentTab (1);
        break;
    }
  } // fnSelectTab
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
      intent.putExtra("android.intent.extra.ResultText", tabBeanshell_tvOutput.getText().toString());
    }
    setResult(RESULT_OK, intent);
  } // fnSetResult
//===================================================================
  public void fnSettings ()
//===================================================================
  {
    Intent intent = new Intent(this, SettingActivity.class);
    startActivityForResult(intent, REQUEST_CODE_SETTINGS);
  } // fnSettings
//===================================================================
  private void fnUpgrade ()
//===================================================================
  {
    final Intent i = new Intent (this, UpgradeActivity.class);
    G.fnLog("d", "Starting upgrade");
    startActivity(i);
  } //fnUpgrade
//===================================================================
}//MainActivity
//##################################################################
