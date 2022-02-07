package ir.sep.android.merchantapp.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import ir.sep.android.merchantapp.Const;
import ir.sep.android.merchantapp.R;
import ir.sep.android.merchantapp.data.entities.Inbox;
import ir.sep.android.merchantapp.data.repository.InboxRepository;

@AndroidEntryPoint
public class MessagingService extends FirebaseMessagingService {

    @Inject
    InboxRepository inboxRepository;


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(Const.TAG, "onMessageReceived: " + remoteMessage.getData().get("message"));
        Log.d(Const.TAG, "From " + remoteMessage.getFrom());
        //   Log.d(Const.TAG, "Body " + remoteMessage.getNotification().getBody());

        //  String title = remoteMessage.getNotification().getTitle();
        //  String body = remoteMessage.getNotification().getBody();


        Map<String, String> data = remoteMessage.getData();
        String title = data.get("title");
        String description = data.get("description");
        ;
        String time = data.get("time");
        String date = data.get("date");


        Inbox inbox = new Inbox(title, description, time, date,Inbox.MessageState.UnRead.getId());
        inboxRepository.insert(inbox);

        pushNotification(title, description);

    }


    private void pushNotification(String title, String body) {

        NotificationManager notificationManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplication(), "channelId");
        builder.setContentTitle(title);
        builder.setContentText(body);
        builder.setSmallIcon(R.mipmap.ic_launcher);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            builder.setChannelId("channelId");
            NotificationChannel notificationChannel = new NotificationChannel("channelId", "channelName", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }


        notificationManager.notify(1, builder.build());


    }
}
