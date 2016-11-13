package com.alexanderwolf.feedtheworld;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

public class Notification extends Service {
    public Notification() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences isFullPref = getSharedPreferences("Storage", Context.MODE_PRIVATE);

        boolean IsFriendFull = isFullPref.getBoolean("FriendIsFull", false);
        boolean IsFactFull = isFullPref.getBoolean("FactIsFull", false);
        boolean IsRestFull = isFullPref.getBoolean("RestIsFull", false);
        boolean IsMineFull = isFullPref.getBoolean("MineIsFull", false);
        boolean IsEnrichFull = isFullPref.getBoolean("EnrichIsFull", false);
    /*    if (IsFriendFull && IsFactFull && IsRestFull && IsMineFull && IsEnrichFull) {

            Intent notificationIntent = new Intent(this, MainActivity.class);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                    notificationIntent, 0);

            android.app.Notification notification = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.icon)
                    .setContentTitle("Feed the World")
                    .setContentText("Storage is Full!")
                    .setContentIntent(pendingIntent).build();

            startForeground(1, notification);

        } */
    }
}
