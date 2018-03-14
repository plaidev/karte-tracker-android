package io.karte.tracker_sample;

import io.karte.android.tracker.Tracker;
import io.karte.android.tracker.firebase.KarteFirebaseInstanceIdService;

public class MyKarteFirebaseInstanceIdService extends KarteFirebaseInstanceIdService {

  protected Tracker getTracker() {
    return Tracker.getInstance(this, SampleApp.APP_KEY);
  }
}
