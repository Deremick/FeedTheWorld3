package com.alexanderwolf.feedtheworld;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Stock extends Service {

    float n;
    float CurrentArfolyam = 20;


    public Stock() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Intent notificationIntent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("Feed the World")
                .setContentText("New Stock Value!")
                .setContentIntent(pendingIntent).build();

        startForeground(3, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);

        Timer a = new Timer();
        a.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Random rand = new Random();
                n = rand.nextFloat() * 2 - 1;
                SharedPreferences Arfolyam = getSharedPreferences("Arfolyam", Context.MODE_PRIVATE);
                CurrentArfolyam = Arfolyam.getFloat("arfolyam", 20);
                SharedPreferences.Editor ArfolyamEdit = Arfolyam.edit();
                if (CurrentArfolyam + n > 10) {
                    ArfolyamEdit.putFloat("arfolyam", CurrentArfolyam + n);
                    ArfolyamEdit.commit();
                }
                else {
                    CurrentArfolyam = 10.2f;
                    ArfolyamEdit.putFloat("arfolyam", CurrentArfolyam);
                    ArfolyamEdit.commit();
                }
                if (CurrentArfolyam + n > 45) {
                    CurrentArfolyam = 44.7f;
                    ArfolyamEdit.putFloat("arfolyam", CurrentArfolyam);
                    ArfolyamEdit.commit();
                }
                stopSelf();
            }
        }, 1000, 1000);


        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


}
