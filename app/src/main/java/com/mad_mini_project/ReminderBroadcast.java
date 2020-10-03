package com.mad_mini_project;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class ReminderBroadcast extends BroadcastReceiver {
    private Object NotificationManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        String date = bundle.getString("date");
        String time = bundle.getString("time");

        Intent intent1 = new Intent(context, NotifyMessage.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("message",date);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,1,intent1,PendingIntent.FLAG_ONE_SHOT);

        //get notification service to create reminder
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder nbuilder = new NotificationCompat.Builder(context,"notify");

        //add notification details
        RemoteViews contentViews = new RemoteViews(context.getPackageName(),R.layout.notification_layout);
        contentViews.setImageViewResource(R.id.image, R.mipmap.ic_launcher);
        PendingIntent pendingswitch = PendingIntent.getBroadcast(context,0,intent,0);
        contentViews.setOnClickPendingIntent(R.id.flashButton, pendingswitch);
        contentViews.setTextViewText(R.id.message, date);
        contentViews.setTextViewText(R.id.message1, time);
        nbuilder.setSmallIcon(R.drawable.ic_alarm);
        nbuilder.setAutoCancel(false);
        nbuilder.setOngoing(true);
        nbuilder.setPriority(Notification.PRIORITY_HIGH);
        nbuilder.setOnlyAlertOnce(true);
        nbuilder.build().flags = Notification.FLAG_NO_CLEAR |Notification.PRIORITY_HIGH;
        nbuilder.setContent(contentViews);
        nbuilder.setContentIntent(pendingIntent);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            String chanelID = "channel";
            NotificationChannel notificationChannel = new NotificationChannel(chanelID,"chanelName", (Integer) NotificationManager);
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
            nbuilder.setChannelId(chanelID);
        }

        Notification notification = nbuilder.build();
        notificationManager.notify(1,notification);
    }
}
