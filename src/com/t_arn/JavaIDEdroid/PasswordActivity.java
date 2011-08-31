package com.t_arn.JavaIDEdroid;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

//##################################################################
public class PasswordActivity extends Activity 
//##################################################################
{
  EditText et_pw1, et_pw2;
  
//===================================================================
  @Override
  protected void onCreate(Bundle savedInstanceState) 
//===================================================================
  {
    setContentView(R.layout.password);
    et_pw1 = (EditText)findViewById(R.id.et_pw1);
    et_pw2 = (EditText)findViewById(R.id.et_pw2);
    super.onCreate(savedInstanceState);
  } // onCreate
//===================================================================
  @Override
  protected void onPause()
//===================================================================
  {
    G.stPw1 = et_pw1.getText().toString();
    et_pw1.setText("");
    G.stPw2 = et_pw2.getText().toString();
    et_pw2.setText("");
    super.onPause();
  }
//===================================================================
}
//##################################################################
