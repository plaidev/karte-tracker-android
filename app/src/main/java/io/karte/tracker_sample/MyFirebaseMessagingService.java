package io.karte.tracker_sample;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import io.karte.android.tracker.Tracker;
import io.karte.android.tracker.firebase.KarteMessageHandler;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

  private static final String EXTRA_PUSH_FLAG = "krt_push_notification";
  private static final String EXTRA_CAMPAIGN_ID = "krt_campaign_id";
  private static final String EXTRA_SHORTEN_ID = "krt_shorten_id";

  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {

    if (remoteMessage.getNotification() != null) {
      RemoteMessage.Notification notification = remoteMessage.getNotification();
      sendNotification(notification.getTitle(), notification.getBody(), remoteMessage.getData());
      return;
    }

    if (remoteMessage.getData() != null) {
      Map<String, String> data = remoteMessage.getData();
      String title = data.get("subject");
      String body = data.get("text");
      sendNotification(title, body, data);
    }
  }

  protected void sendNotification(String title, String body, Map<String, String> data) {

    Intent intent = new Intent(this, MainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

    KarteMessageHandler.copyInfoToIntent(data, intent);

    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT);

    Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

    NotificationCompat.Builder notificationBuilder =
            new NotificationCompat.Builder(this)
                    .setSmallIcon(android.R.drawable.sym_def_app_icon)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

    NotificationManager notificationManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    notificationManager.notify(0, notificationBuilder.build());
  }
}

