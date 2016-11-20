package com.alexanderwolf.feedtheworld;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;

public class FactProduct extends Service {

    int ingredient;
    int FactTimer;
    int FactProduct;
    int ALLproduct;
    int storage;
    int numberOfFact;

    public FactProduct() {
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
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final Timer fact = new Timer();
        fact.schedule(new TimerTask() {
            @Override
            public void run() {
                SharedPreferences sumPref = getSharedPreferences("Producers", Context.MODE_PRIVATE);
                SharedPreferences storagePref = getSharedPreferences("Storage", Context.MODE_PRIVATE);
                SharedPreferences FactTimerPref = getSharedPreferences("Timers", Context.MODE_PRIVATE);
                SharedPreferences Ingredient = getSharedPreferences("Ingredients", Context.MODE_PRIVATE);
                ingredient = Ingredient.getInt("ingredients", 0);
                FactProduct = sumPref.getInt("FactProd", 0);
                ALLproduct = sumPref.getInt("sumProd", 0);
                storage = storagePref.getInt("storage", 0);
                numberOfFact = sumPref.getInt("NOFacts", 0);
                FactTimer = FactTimerPref.getInt("FactTimer", 5);
                SharedPreferences started = getSharedPreferences("Started", Context.MODE_PRIVATE);
                boolean factStarted = started.getBoolean("FactStarted", false);

                if ((storage - ALLproduct >= numberOfFact * 40) && factStarted) {
                    if (FactTimer >= 0 && ingredient > 1) {
                        FactTimer--;
                        SharedPreferences.Editor FactTimerEditor = FactTimerPref.edit();
                        FactTimerEditor.putInt("FactTimer", FactTimer);
                        FactTimerEditor.commit();
                    }
                    ingredient = Ingredient.getInt("ingredients", 0);
                    FactTimer = FactTimerPref.getInt("FactTimer", 5);
                    if (FactTimer == 0 && ingredient > 1) {
                        FactTimer = 5;
                        SharedPreferences.Editor FactTimerEditor = FactTimerPref.edit();
                        FactTimerEditor.putInt("FactTimer", FactTimer);
                        FactTimerEditor.commit();
                        if ((numberOfFact * 80) < ingredient) {
                            ingredient -= numberOfFact * 80;
                            FactProduct += numberOfFact * 40;
                            SharedPreferences.Editor ingredientEditor = Ingredient.edit();
                            ingredientEditor.putInt("ingredients", ingredient);
                            ingredientEditor.commit();
                            SharedPreferences.Editor Edit = sumPref.edit();
                            Edit.putInt("FactProd", FactProduct);
                            Edit.commit();

                        } else {
                            FactProduct += ingredient / 2;
                            ingredient = 0;
                            SharedPreferences.Editor ingredientEditor = Ingredient.edit();
                            ingredientEditor.putInt("ingredients", ingredient);
                            ingredientEditor.commit();
                            SharedPreferences.Editor Edit = sumPref.edit();
                            Edit.putInt("FactProd", FactProduct);
                            Edit.commit();
                            SharedPreferences.Editor startedEdit = started.edit();
                            startedEdit.putBoolean("FactStarted", false);
                            startedEdit.commit();
                            stopForeground(true);
                            stopSelf();
                            fact.cancel();
                            fact.purge();

                        }

                    }
                    else if (ingredient <= 1 || FactTimer == -1) {
                        FactTimer = 5;
                        SharedPreferences.Editor FactTimerEditor = FactTimerPref.edit();
                        FactTimerEditor.putInt("FactTimer", FactTimer);
                        FactTimerEditor.commit();

                        SharedPreferences.Editor startedEdit = started.edit();
                        startedEdit.putBoolean("FactStarted", false);
                        startedEdit.commit();

                        stopForeground(true);
                        stopSelf();
                        fact.cancel();
                        fact.purge();
                    }

                }
                else if ((factStarted) && !(storage - ALLproduct >= numberOfFact * 40)){
                    FactTimer = 5;
                    SharedPreferences.Editor FactTimerEditor = FactTimerPref.edit();
                    FactTimerEditor.putInt("FactTimer", FactTimer);
                    FactTimerEditor.commit();
                    SharedPreferences isFullPref = getSharedPreferences("Storage", Context.MODE_PRIVATE);
                    SharedPreferences.Editor isFullEdit = isFullPref.edit();
                    isFullEdit.putBoolean("FactIsFull", true);
                    isFullEdit.commit();

                    SharedPreferences.Editor startedEdit = started.edit();
                    startedEdit.putBoolean("FactStarted", false);
                    startedEdit.commit();
                    stopForeground(true);
                    stopSelf();
                    fact.cancel();
                    fact.purge();
                }

            }
        }, 1000, 1000);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences isFullPref = getSharedPreferences("Storage", Context.MODE_PRIVATE);
        SharedPreferences Ingredient = getSharedPreferences("Ingredients", Context.MODE_PRIVATE);
        SharedPreferences notiState = getSharedPreferences("Pressed", Context.MODE_PRIVATE);
        boolean IsFriendFull = isFullPref.getBoolean("FriendIsFull", false);
        boolean IsFactFull = isFullPref.getBoolean("FactIsFull", false);
        boolean IsRestFull = isFullPref.getBoolean("RestIsFull", false);
        boolean IsMineFull = isFullPref.getBoolean("MineIsFull", false);
        boolean IsEnrichFull = isFullPref.getBoolean("EnrichIsFull", false);
        boolean notiShown = isFullPref.getBoolean("notiShown", false);
        boolean isNotiOn = notiState.getBoolean("isNotiOn", true);
        ingredient = Ingredient.getInt("ingredients", 0);
        if (((IsFriendFull || IsFactFull || IsRestFull || IsMineFull || IsEnrichFull) && !notiShown) && isNotiOn) {
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(FactProduct.this)
                            .setSmallIcon(R.drawable.icon)
                            .setContentTitle("Feed the World")
                            .setContentText("Storage is Full!")
                            .setAutoCancel(true)
                            .setVibrate(new long[] {1000, 1000})
                            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            Intent resultIntent = new Intent(FactProduct.this, MainActivity.class);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(FactProduct.this);
            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(12, mBuilder.build());


            SharedPreferences.Editor notiShownEdit = isFullPref.edit();
            notiShownEdit.putBoolean("notiShown", true);
            notiShownEdit.commit();
            SharedPreferences started = getSharedPreferences("Started", Context.MODE_PRIVATE);
            SharedPreferences.Editor startedEdit = started.edit();
            startedEdit.putBoolean("FactStarted", false);
            startedEdit.commit();
        }

        if (ingredient <= 1 && isNotiOn) {
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(FactProduct.this)
                            .setSmallIcon(R.drawable.icon)
                            .setContentTitle("Feed the World")
                            .setContentText("Ingredients are gone!")
                            .setAutoCancel(true)
                            .setVibrate(new long[] {1000, 1000})
                            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            Intent resultIntent = new Intent(FactProduct.this, MainActivity.class);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(FactProduct.this);
            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(13, mBuilder.build());
        }
        stopSelf();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
