package com.t_arn.JavaIDEdroid;

import android.app.TabActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends TabActivity
{
  public static final String stProgramName = "JavaIDEdroid";
  public IDE ide;
  public BeanShellTask bshTask;
  public TextView tabBeanshell_tvOutput, tabTools_tvOutput;
  private EditText tabBeanshell_etScript, tabTools_etArgs;
  private static final int REQUEST_CODE_PICK_FILE_OR_DIRECTORY = 1;

//===================================================================
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
//===================================================================
  {
    try
    {
      super.onCreate(savedInstanceState);
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
      tabBeanshell_tvOutput=(TextView)findViewById(R.id.tabBeanshell_tvOutput);
      tabBeanshell_tvOutput.setMovementMethod(ScrollingMovementMethod.getInstance());
      
      ide = new IDE();
      
      tabTools_etArgs=(EditText)findViewById(R.id.tabTools_etArgs);
      tabTools_tvOutput=(TextView)findViewById(R.id.tabTools_tvOutput);
      tabTools_tvOutput.setMovementMethod(ScrollingMovementMethod.getInstance());
    }
    catch (Throwable t) { fnToast("Exception in onCreate!\n"+t.toString(),10000); }
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
    }
    tabBeanshell_etScript.setText(filename);
  }
//===================================================================
   public boolean onCreateOptionsMenu (Menu menu)
//===================================================================
  {
    getMenuInflater().inflate(R.menu.mainmenu, menu);
    return super.onCreateOptionsMenu(menu);
  }
//===================================================================
  public boolean onOptionsItemSelected (MenuItem item)
//===================================================================
  {
    switch (item.getItemId())
    {
      case R.id.opt_options:
        fnOptions ();
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
      fnToast("OI Filemanager needs to be installed", 10000);
    }
    catch (Throwable t) { fnError("tabBeanshell_btnBrowse", t); }
  }//tabBeanshell_btnBrowse
//===================================================================
  public void tabBeanshell_btnRun (final View view)
//===================================================================
  {
    try
    {
      String script;
      Log.i(stProgramName, "Starting BeanShell script");
      fnClear(tabBeanshell_tvOutput);
      script=tabBeanshell_etScript.getText().toString();
      bshTask = new BeanShellTask(this);
      bshTask.execute(script);
    }
    catch (Throwable t) { fnError("tabBeanshell_btnRun", t); }
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
      fnClear(tabTools_tvOutput);
      Log.i(stProgramName, "Starting "+stChosen);
      new ToolTask(this).execute(stChosen, params);
    }
    catch (Throwable t) { fnError("tabTools_btnRun", t); }
  } //tabTools_btnRun
//===================================================================
public void fnClear ()
//===================================================================
{
  try
  {
    tabBeanshell_tvOutput.setText("");
  }
  catch (Throwable t) { fnError("fnClear", t); }
}//fnClear
//===================================================================
  protected void fnClear (TextView tv)
//===================================================================
  {
    try
    {
      tv.setText("");
    }
    catch (Throwable t) { fnError("fnClear", t); }
  }//fnClear
//===================================================================
  public void fnError (String where, Throwable t)
//===================================================================
  {
    fnToast("Error in "+where+"!\n"+t.toString(),10000);
    if (t!=null) t.printStackTrace();
  }//fnError
//===================================================================
  private void fnHelp ()
//===================================================================
  {
    final Intent i = new Intent (this, HelpActivity.class);
    Log.i(stProgramName, "Starting help");
    startActivity(i);
  } //fnHelp
//===================================================================
  private void fnOptions ()
//===================================================================
  {
    fnToast("not yet implemented.", 5000);
  } //fnOptions
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
  public void fnToast (String msg, int msec)
//===================================================================
  {
    Toast.makeText(MainActivity.this,msg,msec).show();
  } //
//===================================================================
}//MainActivity
