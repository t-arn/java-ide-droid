package com.t_arn.JavaIDEdroid;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import com.t_arn.lib.io.StringWriterOutputStream;

//##################################################################
public class ToolTask 
  extends AsyncTask<String, String, Void> // android.os.AsyncTask<Params, Progress, Result>
//##################################################################
{ 
  private ProgressDialog progressDialog;
  private StringWriterOutputStream swos;
  
//===================================================================
public ToolTask ()
//===================================================================
{
} //
//===================================================================
@Override
protected void onPreExecute() 
//===================================================================
{
  G.fnLog("d", "onPreExecute()");
  // show progress dialog
  progressDialog = new ProgressDialog(G.fnGetCurrentActivity());
  progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
  progressDialog.setTitle(G.Rstring(R.string.tit_tooltask));
  progressDialog.setMessage(G.Rstring(R.string.msg_taskrunning));
  progressDialog.setIndeterminate(true);
  progressDialog.show();
}
//===================================================================
@Override
protected Void doInBackground(String... args) 
//===================================================================
{
  try
  {
    String params = "";
    if (args.length>=2) params = args[1];
    G.fnLog("d", "doInBackground()");
    swos = new StringWriterOutputStream();
    G.ide.fnRedirectOutput(swos);
    if (args[0].equals("AAPT")) G.ide.fnAapt(params);
    else if (args[0].equals("ECJ")) G.ide.fnCompile(params);
    else if (args[0].equals("DX")) G.ide.fnDx(params);
    else if (args[0].equals("ApkBuilder")) G.ide.fnApkBuilder(params);
    else if (args[0].equals("ZipSigner")) G.ide.fnSignApk(params);
    if (G.oSet.bLogOutput) G.ide.fnLogOutput(swos);
  }
  catch (Throwable t)
  {
    G.fnError("doInBackground", t);
  }
  return null;
}
//===================================================================
@Override
protected void onProgressUpdate(String... progress)
//===================================================================
{
  try
  {
    G.fnLog("d", "onProgressUpdate()");
    progressDialog.setMessage(progress[0]);
  } catch (Exception e) { G.fnLog("e", "onProgressUpdate(): "+e.getMessage()); }
} // onProgressUpdate
//===================================================================
@Override
protected void onPostExecute(Void unused) 
//===================================================================
{ 
  publishProgress(G.Rstring(R.string.msg_taskfinished));
  G.main.tabTools_tvOutput.append(swos.toString());
  swos = null;
  if (progressDialog.isShowing()) progressDialog.dismiss();
} 
//===================================================================
public void fnPublishProgress(String progressMessage)
//===================================================================
{
  publishProgress(progressMessage);
}
//===================================================================
}
//##################################################################
