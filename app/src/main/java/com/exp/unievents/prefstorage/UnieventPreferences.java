package com.exp.unievents.prefstorage;

import android.content.Context;
import android.content.SharedPreferences;

public final class UnieventPreferences {

  private static final String PREFERENCE_FILE = "unievent";
  private static UnieventPreferences appPreferences;
  private final SharedPreferences mSharedPreferences;
  private final SharedPreferences.Editor mSharedPreferencesEditor;

  private UnieventPreferences(Context context, String permissionFile) {
    mSharedPreferences = context.getSharedPreferences(permissionFile, Context.MODE_PRIVATE);
    mSharedPreferencesEditor = mSharedPreferences.edit();
    mSharedPreferencesEditor.apply();
  }

  public static UnieventPreferences getInstance(Context context) {
    if (appPreferences == null) {
      synchronized (UnieventPreferences.class) {
        appPreferences = new UnieventPreferences(context, PREFERENCE_FILE);
      }
    }
    return appPreferences;
  }
  public String getString(String key){
    return  mSharedPreferences.getString(key,null);
  }

  public void storeOrUpdateSting(String key, String value){
    synchronized (this) {
      mSharedPreferencesEditor.putString(key, value);
      mSharedPreferencesEditor.apply();
    }
  }

  public boolean getBoolean(String key){
    return  mSharedPreferences.getBoolean(key,false);
  }

  public void storeOrUpdateBoolean(String key, boolean value){
    synchronized (this) {
      mSharedPreferencesEditor.putBoolean(key, value);
      mSharedPreferencesEditor.apply();
    }
  }

  public void storeOrUpdateInt(String key, int value) {
    synchronized (this) {
      mSharedPreferencesEditor.putInt(key, value);
      mSharedPreferencesEditor.apply();
    }
  }
}
