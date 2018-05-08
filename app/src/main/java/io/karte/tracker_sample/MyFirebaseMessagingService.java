package io.karte.tracker_sample;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import io.karte.android.tracker.Tracker;
import io.karte.android.tracker.firebase.MessageHandler;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {

    boolean handled = MessageHandler.handleMessage(this, remoteMessage);
    if (!handled) {
      Map<String, String> data = remoteMessage.getData();
      String title = data.get("subject");
      String body = data.get("text");
      sendNotification(title, body, data.get("android_channel_id"), data);
    }
  }

  protected void sendNotification(String title, String body,String channel, Map<String, String> data) {

    Intent intent = new Intent(this, MainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    MessageHandler.copyInfoToIntent(data, intent);

    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT);

    Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

    NotificationCompat.Builder notificationBuilder =
            new NotificationCompat.Builder(this, "my_channel")
                    .setSmallIcon(getApplicationInfo().icon)
                    .setContentTitle(title)
                    .setContentText(body)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setChannelId(channel)
                    .setContentIntent(pendingIntent);

    NotificationManager notificationManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    if (notificationManager == null) {
      return;
    }
    notificationManager.notify(0, notificationBuilder.build());
  }
}

