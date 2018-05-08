package io.karte.tracker_sample;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import io.karte.android.tracker.Tracker;

public class SampleApp extends Application {

  public final static String APP_KEY = "SET_YOUR_APP_KEY";

  @Override
  public void onCreate() {
    super.onCreate();
    Tracker.init(this, APP_KEY);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      createChannel(this);
    }
  }

  @RequiresApi(api = 26)
  public static void createChannel(final Context context) {
    NotificationChannel channel = new NotificationChannel("my_channel", "通知テストチャンネル", NotificationManager.IMPORTANCE_DEFAULT);
    channel.setDescription("テストの説明です");
    channel.setShowBadge(true);

    // create or update the Notification channel
    final NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
    notificationManager.createNotificationChannel(channel);
  }
}
