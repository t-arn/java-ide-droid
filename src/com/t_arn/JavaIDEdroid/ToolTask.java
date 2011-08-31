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
  private StringWriterOutputStream swos;
  
public ToolTask ()
//===================================================================
{
} //
//===================================================================
protected void onPreExecute() 
//===================================================================
{
  Log.i(G.stProgramName, "onPreExecute");
  // show progress dialog
  progressDialog = new ProgressDialog(G.main);
  progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
  progressDialog.setTitle(G.Rstring(R.string.tit_tooltask));
  progressDialog.setMessage(G.Rstring(R.string.msg_taskrunning));
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
    Log.i(G.stProgramName, "doInBackground");
    swos = new StringWriterOutputStream();
    G.ide.fnRedirectOutput(swos);
    if (args[0].equals("AAPT")) G.ide.fnAapt(params);
    else if (args[0].equals("ECJ")) G.ide.fnCompile(params);
    else if (args[0].equals("DX")) G.ide.fnDx(params);
    else if (args[0].equals("ApkBuilder")) G.ide.fnApkBuilder(params);
    else if (args[0].equals("ZipSigner")) G.ide.fnSignApk(params);
    if (G.oSet.bLogOutput) G.ide.fnLogOutput();
  }
  catch (Throwable t)
  {
    G.fnError("doInBackground", t);
  }
  return null;
}
//===================================================================
protected void onProgressUpdate(String... progress)
{
  try
  {
    Log.i(G.stProgramName, "onProgressUpdate");
    progressDialog.setMessage(progress[0]);
  } catch (Exception e) { Log.e(G.stProgramName, "onProgressUpdate: "+e.getMessage()); }
} // onProgressUpdate
//===================================================================
protected void onPostExecute(Void unused) 
//===================================================================
{ 
  publishProgress(G.Rstring(R.string.msg_taskfinished));
  G.tabTools_tvOutput.append(swos.toString());
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
