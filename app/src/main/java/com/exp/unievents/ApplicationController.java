package com.exp.unievents;

import android.app.Application;
import android.content.Context;

import com.exp.unievents.prefstorage.UnieventPreferences;

public class ApplicationController extends Application {
  public static UnieventPreferences preferences;

  @Override
  public void onCreate() {
    super.onCreate();
    preferences = UnieventPreferences.getInstance(this);
  }

  public UnieventPreferences getPreferences() {
    return preferences;
  }
}
