package com.t_arn.JavaIDEdroid;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import com.t_arn.lib.io.StringWriterOutputStream;

//##################################################################
public class ToolTask 
  extends AsyncTask<String, String, Void> // android.os.AsyncTask<Params, Progress, Result>
//##################################################################
{ 
  private ProgressDialog progressDialog;
  private MainActivity mainAct;
  private StringWriterOutputStream swos;
  
public ToolTask (MainActivity main)
//===================================================================
{
  this.mainAct = main;
} //
//===================================================================
protected void onPreExecute() 
//===================================================================
{
  Log.i(MainActivity.stProgramName, "onPreExecute");
  // show progress dialog
  progressDialog = new ProgressDialog(mainAct);
  progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
  progressDialog.setTitle("Tool Task");
  progressDialog.setMessage("Task running...");
  progressDialog.setIndeterminate(true);
  progressDialog.show();
}
//===================================================================
protected Void doInBackground(String... args) 
//===================================================================
{
  try
  {
    String params = "";
    if (args.length>=2) params = args[1];
    Log.i(MainActivity.stProgramName, "doInBackground");
    swos = new StringWriterOutputStream();
    mainAct.ide.fnRedirectOutput(swos);
    if (args[0].equals("ECJ")) mainAct.ide.fnCompile(params);
    else if (args[0].equals("DX")) mainAct.ide.fnDx(params);
    else if (args[0].equals("ApkBuilder")) mainAct.ide.fnApkBuilder(params);
    else if (args[0].equals("ZipSigner")) mainAct.ide.fnSignApk(params);
  }
  catch (Throwable t)
  {
    mainAct.fnError("doInBackground", t);
  }
  return null;
}
//===================================================================
protected void onProgressUpdate(String... progress)
{
  try
  {
    Log.i(MainActivity.stProgramName, "onProgressUpdate");
    progressDialog.setMessage(progress[0]);
  } catch (Exception e) { Log.e(MainActivity.stProgramName, "onProgressUpdate: "+e.getMessage()); }
} // onProgressUpdate
//===================================================================
protected void onPostExecute(Void unused) 
//===================================================================
{ 
  publishProgress("Task finished");
  mainAct.tabTools_tvOutput.append(swos.toString());
  if (progressDialog.isShowing()) progressDialog.dismiss();
} 
//===================================================================
public void fnPublishProgress(String progressMessage)
{
  publishProgress(progressMessage);
}
//===================================================================
}
//##################################################################
