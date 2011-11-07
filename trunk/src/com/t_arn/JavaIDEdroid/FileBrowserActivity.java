package com.t_arn.JavaIDEdroid;

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.t_arn.lib.io.FileBrowser;
import java.util.ArrayList;

//##################################################################
public class FileBrowserActivity
  extends ListActivity
//##################################################################
{
  // class variables
  private ArrayAdapter<String> aa;
  private FileBrowser fb;
  private String stTitle;
  private String stCurDir;
  private String stPattern;
  private ArrayList<String> alExcludeDirs;
  private String[] aList;
  private TextView tvTitle, tvCurDir;
  private EditText etFilename;
  
//===================================================================
  @Override
  protected void onCreate (Bundle savedInstanceState)
//===================================================================
  {
    try
    {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.filebrowserlist);
      tvTitle = (TextView) findViewById(R.id.fileview_tvTitle);
      tvCurDir = (TextView) findViewById(R.id.fileview_tvPath);
      etFilename = (EditText) findViewById(R.id.filelist_etFilename);
      aa = new ArrayAdapter<String> (this, R.layout.filelist_item);
      aa.setNotifyOnChange(false);
      setListAdapter(aa);
      fb = new FileBrowser();
      aList=null;
      fnGetIntentData();
      tvTitle.setText(stTitle);
      fnShowDirList();
    }
    catch (Throwable t) { G.fnError("onCreate", t); }
  } // onCreate
//===================================================================
  @Override 
  public void onBackPressed()
//===================================================================
  {
    G.fnLog("d","onBackPressed()");
    // super.onBackPressed();
  }
//===================================================================
  @Override 
  public void onConfigurationChanged(Configuration config)
//===================================================================
  {
    G.fnLog("d","onConfigurationChanged()");
    super.onConfigurationChanged(config);
  } // onConfigurationChanged
//===================================================================
  @Override
  protected void onListItemClick (ListView lv, View v, int pos, long id)
//===================================================================
  {
    String file;
    super.onListItemClick(lv, v, pos, id);
    etFilename.setText("");
    file = aList[pos];
    if (file.endsWith("/"))
    {
      stCurDir+=file;
      fnShowDirList();
      return;
    }
    if (file.equals(".."))
    {
      stCurDir=stCurDir.substring(0,stCurDir.length()-1); 
      stCurDir=stCurDir.substring(0,stCurDir.lastIndexOf('/')+1); 
      fnShowDirList();
      return;
    }
    etFilename.setText(file);
  } // onListItemClick
//===================================================================
  @Override
  protected void onResume()
//===================================================================
  {
    super.onResume();
    G.fnSetCurrentActivity(this);
  } // onResume
//===================================================================
  public void fnOnClickBtnOK (final View view)
//===================================================================
  {
    G.fnLog("d", "fnOnClickBtnOK() start");
    Intent intent = new Intent();
    intent.putExtra("android.intent.extra.chosenFile", stCurDir+etFilename.getText().toString());
    setResult(RESULT_OK, intent);
    finish();
    G.fnLog("d", "fnOnClickBtnOK() end");
  } // fnOnClickBtnOK
//===================================================================
  public void fnOnClickBtnCancel (final View view)
//===================================================================
  {
    G.fnLog("d", "fnOnClickBtnCancel()");
    setResult(RESULT_CANCELED, null);
    finish();
  } // fnOnClickBtnCancel
//===================================================================
  private void fnGetIntentData()
//===================================================================
  {
    try
    {
    // get passed data
      Intent intent = getIntent();
      Bundle extras = intent.getExtras();
      if (extras != null)
      {
        this.stTitle = extras.getString("android.intent.extra.Title");
        this.stCurDir = extras.getString("android.intent.extra.DirPath");
        this.stPattern = extras.getString("android.intent.extra.Pattern");
        this.alExcludeDirs = extras.getStringArrayList("android.intent.extra.ExcludeDirs");
       }
    } catch (Throwable t) { G.fnError("fnGetIntentData", t); }
  } // fnGetIntentData
//===================================================================
  private void fnShowDirList ()
//===================================================================
  {
    tvCurDir.setText(this.stCurDir+"  ("+stPattern+")");
    etFilename.setText("");
    fb.setDirectory(this.stCurDir);
    fb.setPattern(this.stPattern);
    fb.setExcludeDirs(this.alExcludeDirs);
    aList=fb.getDirsAndFiles();
    aa.clear();
    for (int i=0; i<aList.length; i++) 
      aa.add(aList[i]);
    aa.notifyDataSetChanged();
  } // fnShowDirList
//===================================================================
}
//##################################################################
