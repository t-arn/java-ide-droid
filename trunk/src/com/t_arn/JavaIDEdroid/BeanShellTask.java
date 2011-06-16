package com.t_arn.JavaIDEdroid;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import com.t_arn.lib.io.StringWriterOutputStream;

//##################################################################
public class BeanShellTask 
  extends AsyncTask<String, String, Void> // android.os.AsyncTask<Params, Progress, Result>
//##################################################################
{ 
  private ProgressDialog progressDialog;
  private MainActivity mainAct;
  private StringWriterOutputStream swos;
  
public BeanShellTask (MainActivity main)
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
  progressDialog.setTitle("BeanShell Task");
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
    final bsh.Interpreter i;
    Log.i(MainActivity.stProgramName, "doInBackground");
    i = new bsh.Interpreter();
    i.set("mainActivity", mainAct);
    swos = new StringWriterOutputStream();
    mainAct.ide.fnRedirectOutput(swos);
    mainAct.ide.fnRunBeanshellScript(i, args[0]);
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
  String[] uitask;
  try
  {
    Log.i(MainActivity.stProgramName, "onProgressUpdate");
    if (!progress[0].startsWith("~UITASK~")) progressDialog.setMessage(progress[0]);
    else
    {
      uitask = progress[0].split("\t");
      if (uitask[1].equals("CLEAR")) 
      {
        swos.toStringBuffer().setLength(0);
        mainAct.fnClear();
      }
      if (uitask[1].equals("TOAST"))
      { 
        mainAct.fnToast(uitask[2], Integer.parseInt(uitask[3]));
      }
    }//else
  } catch (Exception e) { Log.e(MainActivity.stProgramName, "onProgressUpdate: "+e.getMessage()); }
} // onProgressUpdate
//===================================================================
protected void onPostExecute(Void unused) 
//===================================================================
{ 
  publishProgress("Task finished");
  mainAct.tabBeanshell_tvOutput.append(swos.toString());
  if (progressDialog.isShowing()) progressDialog.dismiss();
} 
//===================================================================
public void fnClear()
{
  publishProgress("~UITASK~\tCLEAR");
}
//===================================================================
public void fnPublishProgress(String progressMessage)
{
  publishProgress(progressMessage);
}
//===================================================================
public void fnToast(String msg, int ms)
{
  publishProgress("~UITASK~\tTOAST\t"+msg+"\t"+ms);
}
//===================================================================
}
//##################################################################
