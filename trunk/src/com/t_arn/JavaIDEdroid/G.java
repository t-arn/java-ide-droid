package com.t_arn.JavaIDEdroid;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.*;
import android.content.res.Resources;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;
import java.io.*;

//##################################################################
/** 
 * Class for global variables and methods
 */
 public class G
//##################################################################
{
  // Global variables
  public static String stProgramName="JavaIDEdroid";
  public static MainActivity main = null;
  public static IDE ide;
  public static BeanShellTask bshTask;
  public static SettingActivity oSet;
  public static String stPw1, stPw2;
  public static int iScriptResultCode;
  public static String stWorkDir = "/sdcard/.JavaIDEdroid/";
  public static boolean bScriptAutoRun, bScriptAutoExit, bWantResultText;
  private static Activity currentActivity;
  protected static Resources res=null;
  public static final int DIALOG_INFO_ONLY = 1;
  public static final int DIALOG_INVALID_DEVROOT = 2; 
  
//===================================================================
// Global static methods
//===================================================================
  public static void fnInit (MainActivity mainActivity)
//===================================================================
  {
    G.main = mainActivity;
    currentActivity = null;
    G.stPw1 = G.stPw2 = "";
    bScriptAutoRun = bScriptAutoExit = bWantResultText = false;
    G.res = G.main.getResources();
    G.oSet = new SettingActivity();
    G.oSet.iLogLevel=5;
    fnMakeLogDir(true);
    G.ide = new IDE();
    G.iScriptResultCode = 99;
 } //fnInit
//===================================================================
/** Shows an uncancelable alert dialog. When the user clicks a 
 * a button, fnAlertDialogResult is called with the id of the
 * dialog and the clicked button.
 *
 * @param ctx The context. If null is passed, G.currentActivity will
 *            be used (if it is not null itself)
 * @param id The unique id of the dialog
 * @param title The title of the dialog or null if no title desired
 * @param message The message to be shown
 * @param posButtonText The text for the positive button
 *                      or null if no button desired
 * @param negButtonText The text for the negative button
 *                      or null if no button desired
 */
   public static void fnAlertDialog (Context ctx,
    final int id, String title, String message, 
    String posButtonText, String negButtonText
  )
//===================================================================
  { 
    AlertDialog.Builder adb;
    Context mCtx = ctx;
    if (mCtx==null) mCtx=currentActivity;
    if (mCtx==null)
    {
      fnLog("e", "G.fnAlertDialog(): No valid context found to display dialog");
      fnLog("e", "G.fnAlertDialog() message was: "+message);
      fnToast(message, 10000);
      return;
    }
    else fnLog("d", "AlertDialog Context="+currentActivity.getLocalClassName());
    adb = new AlertDialog.Builder(mCtx);
    if (title!=null) adb.setTitle(title);
    if (posButtonText!=null) 
    {
      adb.setPositiveButton(posButtonText,
        new DialogInterface.OnClickListener() 
        {
          public void onClick(DialogInterface dialog, int which) 
          {
            G.fnAlertDialogResult(id, which);
          }
        }
      );
    }
    if (negButtonText!=null) 
    {
      adb.setNegativeButton(negButtonText,
        new DialogInterface.OnClickListener() 
        {
          public void onClick(DialogInterface dialog, int which) 
          {
            G.fnAlertDialogResult(id, which);
          }
        }
      );
    }
    adb.setCancelable(false);
    adb.setMessage(message);
    adb.show();
  } // fnAlertDialog
//===================================================================
 /** Shows an uncancelable alert dialog. When the user clicks a 
 * a button, fnAlertDialogResult is called with the id of the
 * dialog and the clicked button.
 *
 * @param ctx The context
 * @param id The unique id of the dialog
 * @param title The title of the dialog or 0 if no title desired
 * @param message The message to be shown
 * @param posButtonText The text for the positive button
 *                      or 0 if no button desired
 * @param negButtonText The text for the negative button
 *                      or 0 if no button desired
 */ 
  public static void fnAlertDialog (Context ctx,
    final int id, int title, int message, 
    int posButtonText, int negButtonText
  )
//===================================================================
  {
    String stTit, stMsg, stPosBtn, stNegBtn;
    stTit=stPosBtn=stNegBtn=null;
    if (title!=0) stTit=G.Rstring(title);
    stMsg=G.Rstring(message);
    if (posButtonText!=0) stPosBtn=G.Rstring(posButtonText);
    if (negButtonText!=0) stNegBtn=G.Rstring(negButtonText);
    G.fnAlertDialog(ctx, id, stTit, stMsg, stPosBtn, stNegBtn);
  } // 
//===================================================================
  public static void fnAlertDialogResult (int id, int clickedButton)
//===================================================================
  {
    switch (id)
    {
      case DIALOG_INFO_ONLY:
        break;
      case DIALOG_INVALID_DEVROOT:
        G.main.fnSettings();
        break;
      default:
        break;
    } // switch
  } // fnAlertDialogResult
//===================================================================
  public static int fnArrayIndexOf(String[] array, String key)
//===================================================================
  {
    int i, pos=-1;
    if ((array==null)||(key==null)) return -1;
    for (i=0;i<array.length;i++) 
    {
      if (array[i].equals(key)) 
      {
        pos=i;
        break;
      }//if
    }//for
    return pos;
  } //fnArrayIndexOf
//===================================================================
  public static boolean fnCheckWorkDir (boolean showToast)
//===================================================================
  {
    boolean ok = true;
    if (! new File(stWorkDir).exists()) 
    {
      ok = false;
      G.fnLog("w", Rstring(R.string.msg_workdir_missing));
      if (showToast) G.fnToast(R.string.msg_workdir_missing,15000);
    }
    return ok;
  }//fnCheckWorkDir
//===================================================================
  public static void fnError (String where, Throwable t)
//===================================================================
  {
    String errMsg="";
    if (t!=null) errMsg=t.toString();
    G.fnLog("e",errMsg);
    fnToast(G.Rstring(R.string.err_errorIn)+" "+where+"!\n"+errMsg,10000);
    if (t!=null) t.printStackTrace();
  } //fnError
//===================================================================
  public static boolean fnIsFirstOpen ()
//===================================================================
  {
    File f;
    String filename, pkgName;
    PackageManager pm;
    PackageInfo pkgInfo;
    boolean first=false;
    
    try
    {
      if (G.fnCheckWorkDir(false)==false) return false;
      pm = main.getPackageManager();
      pkgName = main.getPackageName();
      pkgInfo = pm.getPackageInfo(pkgName,0);
      filename=stWorkDir+pkgInfo.versionCode+".flg";
      f = new File(filename);
      if (f.exists()) first=false;
      else
      {
        first = true;
        f.createNewFile();
      }
    }//try
    catch (Exception e)
    {
      first = false;
    }//catch
    return first;
  } // fnIsFirstOpen
//===================================================================
  public static Activity fnGetCurrentActivity ()
//===================================================================
  {
    return G.currentActivity;
  } // 
//===================================================================
  public static void fnLog (String level, String msg)
//===================================================================
  {
    if (G.oSet.iLogLevel==0) return; // user wants no logging
    Time t = new Time();
    t.setToNow();
    msg = t.format("%Y-%m-%d %H:%M:%S") + " "+msg;
    if ((level=="v")&&(G.oSet.iLogLevel>=5)) Log.v(G.stProgramName, msg);
    if ((level=="d")&&(G.oSet.iLogLevel>=4)) Log.d(G.stProgramName, msg);
    if ((level=="i")&&(G.oSet.iLogLevel>=3)) Log.i(G.stProgramName, msg);
    if ((level=="w")&&(G.oSet.iLogLevel>=2)) Log.w(G.stProgramName, msg);
    if ((level=="e")&&(G.oSet.iLogLevel>=1)) Log.e(G.stProgramName, msg);
  } //fnLog
//===================================================================
  public static boolean fnMakeLogDir (boolean showToast)
//===================================================================
  {
    File dir;
    boolean ok = false;
    try
    {
      dir = new File(stWorkDir);
      if (!dir.exists()) dir.mkdir();
      if (dir.isDirectory()) ok = true;
    }
    catch (Exception e)
    {
      ok = false;
    }
    fnCheckWorkDir(showToast);
    return ok;
  } // fnMakeLogDir
//===================================================================
  public static int fnParseInt (String s, int defaultValue)
//===================================================================
  {
    int i;
    try
    {
      if ( s == null )
      {
        i = defaultValue;
      }  // if
      else i=Integer.parseInt(s);
    }  // try
    catch (NumberFormatException e)
    {
      i = defaultValue;
    }  // catch
    return i;
  }  // fnParseInt 
//===================================================================
  public static void fnSetCurrentActivity (Activity ca)
//===================================================================
  {
    G.currentActivity = ca;
    if (ca==null) G.fnLog("d", "Current activity = null");
    else G.fnLog("d", "Current activity = "+ca.getLocalClassName());
  } // 
//===================================================================
  public static void fnToast (int msg_id, int msec)
//===================================================================
  {
    Toast.makeText(G.main,msg_id,msec).show();
  } 
//===================================================================
  public static void fnToast (String msg, int msec)
//===================================================================
  {
    Toast.makeText(G.main,msg,msec).show();
  } 
//===================================================================
  public static String Rstring (int id)
//===================================================================
  {
    String msg;
    try
    {
      msg=res.getString(id);
    }
    catch (Exception e)
    {
      msg="textNA:"+id;
    }
    return msg;
  } // 
//===================================================================
}
//##################################################################
