package io.karte.tracker_sample;

import android.app.Application;

import io.karte.android.tracker.Tracker;

public class SampleApp extends Application {

  public final static String APP_KEY = "SET_YOUR_APP_KEY";

  @Override
  public void onCreate() {
    super.onCreate();
    Tracker.init(this, APP_KEY);
  }
}
