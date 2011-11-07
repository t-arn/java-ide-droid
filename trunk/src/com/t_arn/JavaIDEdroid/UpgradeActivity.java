package com.t_arn.JavaIDEdroid;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.webkit.WebView;

//##################################################################
public class UpgradeActivity extends Activity 
//##################################################################
{

//===================================================================
  @Override
  protected void onCreate(Bundle savedInstanceState) 
//===================================================================
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.upgrade);

    final WebView view = (WebView) findViewById(R.id.vwUpgrade);
    view.getSettings().setJavaScriptEnabled(true);
    initWebKit(view, this);
    view.bringToFront();
  } // onCreate
//===================================================================
  @Override 
  public void onConfigurationChanged(Configuration config)
//===================================================================
  {
    super.onConfigurationChanged(config);
  }
//===================================================================
  private void initWebKit(final WebView view, final Context context) 
//===================================================================
  {
    final String mimetype = "text/html";
    final String encoding = "UTF-8";
    String htmldata;
    
    final int pageId= R.raw.upgrade;    
    final InputStream is = context.getResources().openRawResource(pageId);

    try 
    {
      if (is != null && is.available() > 0) 
      {
        final byte[] bytes = new byte[is.available()];
        is.read(bytes);
        htmldata = new String(bytes);
        view.loadDataWithBaseURL(null, htmldata, mimetype, encoding, null);
      }
    } catch (IOException e) { }
  } // initWebKit
//===================================================================
}
//##################################################################
