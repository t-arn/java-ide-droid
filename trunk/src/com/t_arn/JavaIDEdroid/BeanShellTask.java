package com.t_arn.JavaIDEdroid;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import com.t_arn.lib.io.StringWriterOutputStream;

//##################################################################
public class BeanShellTask 
  extends AsyncTask<String, String, Integer> // android.os.AsyncTask<Params, Progress, Result>
//##################################################################
{ 
  private ProgressDialog progressDialog;
  private StringWriterOutputStream swos;
  
//===================================================================
public BeanShellTask ()
//===================================================================
{
}
//===================================================================
@Override
protected void onPreExecute() 
//===================================================================
{
  G.fnLog("d", "onPreExecute()");
  // show progress dialog
  progressDialog = new ProgressDialog(G.fnGetCurrentActivity());
  progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
  progressDialog.setTitle(G.Rstring(R.string.tit_bshtask));
  progressDialog.setMessage(G.Rstring(R.string.msg_taskrunning));
  progressDialog.setIndeterminate(true);
  progressDialog.show();
} // onPreExecute
//===================================================================
@Override
protected Integer doInBackground(String... args) 
//===================================================================
{
  int iResult=99;
  try
  {
    final bsh.Interpreter i;
    G.fnLog("d", "doInBackground()");
    i = new bsh.Interpreter();
    i.set("G", new G());
    swos = new StringWriterOutputStream();
    G.ide.fnRedirectOutput(swos);
    G.fnLog("d", "running script "+args[0]);
    G.ide.fnRunBeanshellScript(i, args[0]);
    G.fnLog("i", "script result="+G.iScriptResultCode);
    iResult=G.iScriptResultCode;
  }
  catch (Throwable t)
  {
    G.fnError("doInBackground", t);
    iResult = 99;
  }
  return new Integer(iResult);
} // doInBackground
//===================================================================
@Override
protected void onProgressUpdate(String... progress)
//===================================================================
{
  String[] uitask;
  try
  {
    G.fnLog("d", "onProgressUpdate()");
    if (!progress[0].startsWith("~UITASK~")) progressDialog.setMessage(progress[0]);
    else
    {
      uitask = progress[0].split("\t");
      if (uitask[1].equals("CLEAR")) 
      {
        swos.toStringBuffer().setLength(0);
        G.main.fnClear();
      }
      if (uitask[1].equals("TOAST"))
      { 
        G.fnToast(uitask[2], Integer.parseInt(uitask[3]));
      }
    }//else
  } catch (Exception e) { G.fnLog("e", "onProgressUpdate(): "+e.getMessage()); }
} // onProgressUpdate
//===================================================================
@Override
protected void onPostExecute(Integer IntResult) 
//===================================================================
{ 
  boolean bWarn = false;
  publishProgress(G.Rstring(R.string.msg_taskfinished));
  if (G.oSet.bLogOutput) 
  {
    if (G.stPw1.length()+G.stPw2.length()>0)
    {
      System.err.println("\n"+G.Rstring(R.string.msg_logpw)+"\n");
      bWarn = true;
    }
    G.ide.fnLogOutput(swos);
  }
  G.main.tabBeanshell_tvOutput.append(swos.toString());
  swos=null;
  if (progressDialog.isShowing()) progressDialog.dismiss();
  if (bWarn) G.fnToast(R.string.msg_logpwgui,10000);
  G.main.fnSetResult();
  if (G.bScriptAutoExit) G.main.finish();
} // onPostExecute
//===================================================================
public void fnClear()
//===================================================================
{
  publishProgress("~UITASK~\tCLEAR");
}
//===================================================================
public void fnPublishProgress(String progressMessage)
//===================================================================
{
  publishProgress(progressMessage);
}
//===================================================================
public void fnToast(String msg, int ms)
//===================================================================
{
  publishProgress("~UITASK~\tTOAST\t"+msg+"\t"+ms);
}
//===================================================================
}
//##################################################################
