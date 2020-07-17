package com.exp.unievents.ui.activities;

import android.app.Dialog;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.exp.unievents.dialog.ProgressDialogUtil;
import com.google.gson.Gson;

import java.io.StringReader;
import java.lang.reflect.Type;

import static com.exp.unievents.ApplicationController.preferences;

/**
 * @BaseActivity use to access common method/variable for all Activity
 */
public class BaseActivity extends AppCompatActivity {
  private Dialog pDialog = null;

  public BaseActivity() {
  }

  protected void showLoadingProgress(String message) {
    if (pDialog == null) {
      pDialog = new ProgressDialogUtil().createDialog(this, message);
    }
    pDialog.show();
  }

  protected void hideProgressDialog() {
    if (pDialog != null && pDialog.isShowing()) pDialog.dismiss();
  }

  protected String getPrefString(String key) {
    return preferences.getString(key);
  }

  protected boolean getPrefBoolean(String key) {
    return preferences.getBoolean(key);
  }

  protected <T> T getPrefObject(String key, @NonNull Class<T> valueType) {//t eans a type class which is hold any class dianically. which is resturn a class.. it is a array of class
    String s = preferences.getString(key);//preferenc is a object of unievent ..by this we called get string method
    return new Gson().fromJson(s, valueType);
  }

  protected Object getPrefObject(String key, @NonNull Type typeToken) {
    String s = preferences.getString(key);
    StringReader reader = new StringReader(s);
    return new Gson().fromJson(reader, typeToken);//from json is a method ... type token is a value type...
  }

  protected void storeOrUpdateObject(String key, Object object) {
    String s = new Gson().toJson(object);
    preferences.storeOrUpdateSting(key, s);
  }

  protected void storeOrUpdateBoolean(String key, boolean object) {
    preferences.storeOrUpdateBoolean(key, object);
  }

  protected void storeOrUpdateInt(String key, int object) {
    preferences.storeOrUpdateInt(key, object);
  }

}
