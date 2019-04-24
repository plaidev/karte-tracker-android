package io.karte.tracker_sample;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import io.karte.android.tracker.Tracker;

public class MyKarteFirebaseInstanceIdService extends FirebaseInstanceIdService {

  @Override
  public void onTokenRefresh() {
    super.onTokenRefresh();
    final String token = FirebaseInstanceId.getInstance().getToken();
    Tracker.getInstance(this, SampleApp.APP_KEY).trackFcmToken(token);
  }

}
