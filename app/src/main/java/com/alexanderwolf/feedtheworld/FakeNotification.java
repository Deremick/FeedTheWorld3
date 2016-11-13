package com.alexanderwolf.feedtheworld;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class FakeNotification extends Service {
    public FakeNotification() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Intent notificationIntent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        android.app.Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("Feed the World")
                .setContentText("Brugers are on the way")
                .setContentIntent(pendingIntent).build();

        startForeground(2, notification);
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }
}
