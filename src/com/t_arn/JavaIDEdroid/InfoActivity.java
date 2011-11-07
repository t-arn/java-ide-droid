package com.t_arn.JavaIDEdroid;

import android.app.Activity;
import android.content.pm.*;
import android.os.Bundle;
import android.widget.TextView;

public class InfoActivity extends Activity
{
  private TextView tv;
  
//===================================================================
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState)
//===================================================================
  {
    setContentView(R.layout.info);
    tv = (TextView)findViewById(R.id.vwInfo);
    fnShowInfos();
    super.onCreate(savedInstanceState);
  }
//===================================================================
  @Override
  protected void onResume()
//===================================================================
  {
    super.onResume();
    G.fnSetCurrentActivity(this);
  } // onResume
//===================================================================
  private void fnShowInfos()
//===================================================================
  {
    String stInfo = "", pkgName;
    PackageManager pm;
    PackageInfo pkgInfo;
    ApplicationInfo appInfo;
   
    try
    {
      pm = getPackageManager();
      pkgName = getPackageName();
      pkgInfo = pm.getPackageInfo(pkgName,0);
      appInfo = getApplicationInfo();
      stInfo +=   "Package name:       "+pkgName;
      stInfo += "\nVersion name:       "+pkgInfo.versionName;
      stInfo += "\nVersion code:       "+pkgInfo.versionCode;
      stInfo += "\nTarget SDK version: "+appInfo.targetSdkVersion;
      stInfo += "\nDevice API level:   "+android.os.Build.VERSION.SDK_INT;
      stInfo += "\n\n"+G.Rstring(R.string.msg_foreignSW);
      stInfo += "\nEclipse Foundation:  Java Compiler (http://www.eclipse.org)";
      stInfo += "\nEllinwood, Ken:      Zipsigner-lib (http://code.google.com/p/zip-signer)";
      stInfo += "\nGoogle Inc.          aapt (http://developer.android.com)";
      stInfo += "\n                     dx (http://developer.android.com)";
      stInfo += "\n                     apkbuilder (http://developer.android.com)";
      stInfo += "\nNiemeyer, Patrick:   BeanShell Interpreter (http://www.beanshell.org)";
      stInfo += "\n\n";
      tv.setText (stInfo);
    }
    catch (Exception e)
    {
      G.fnLog("e", e.toString());
    }
  } // fnShowInfos
//===================================================================
} // InfoActivity
