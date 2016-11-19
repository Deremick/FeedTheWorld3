package com.alexanderwolf.feedtheworld;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

public class EnrichProduct extends Service {

    int ingredient;
    int EnrichTimer;
    int EnrichProduct;
    int ALLproduct;
    int storage;
    int numberOfEnrich;
    boolean isFull;



    public EnrichProduct() {
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
                .setContentText("Brugers are on the way")
                .setContentIntent(pendingIntent).build();

        startForeground(2, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final Timer enrichTimer = new Timer();
        enrichTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                SharedPreferences sumPref = getSharedPreferences("Producters", Context.MODE_PRIVATE);
                SharedPreferences storagePref = getSharedPreferences("Storage", Context.MODE_PRIVATE);
                SharedPreferences EnrichTimerPref = getSharedPreferences("Timers", Context.MODE_PRIVATE);
                SharedPreferences Ingredient = getSharedPreferences("Ingredients", Context.MODE_PRIVATE);
                ingredient = Ingredient.getInt("ingredients", 0);
                EnrichProduct = sumPref.getInt("EnrichProd", 0);
                ALLproduct = sumPref.getInt("sumProd", 0);
                storage = storagePref.getInt("storage", 0);
                numberOfEnrich = sumPref.getInt("NOEnrichments", 0);
                EnrichTimer = EnrichTimerPref.getInt("EnrichTimer", 3);
                SharedPreferences started = getSharedPreferences("Started", Context.MODE_PRIVATE);
                boolean enrichStarted = started.getBoolean("EnrichStarted", false);

                if ((storage - ALLproduct >= numberOfEnrich * 500) && enrichStarted) {

                    if (EnrichTimer >= 0 && ingredient > 1) {
                        EnrichTimer--;
                        SharedPreferences.Editor EnrichTimerEditor = EnrichTimerPref.edit();
                        EnrichTimerEditor.putInt("EnrichTimer", EnrichTimer);
                        EnrichTimerEditor.commit();
                    }
                    ingredient = Ingredient.getInt("ingredients", 0);
                    EnrichTimer = EnrichTimerPref.getInt("EnrichTimer", 3);
                    if ((EnrichTimer == 0) && (ingredient > 1)) {
                        EnrichTimer = 3;
                        SharedPreferences.Editor EnrichTimerEditor = EnrichTimerPref.edit();
                        EnrichTimerEditor.putInt("EnrichTimer", EnrichTimer);
                        EnrichTimerEditor.commit();

                        if ((numberOfEnrich * 1000) < ingredient) {
                            ingredient -= numberOfEnrich * 1000;
                            EnrichProduct += (numberOfEnrich * 500);
                            SharedPreferences.Editor ingredientEditor = Ingredient.edit();
                            ingredientEditor.putInt("ingredients", ingredient);
                            ingredientEditor.commit();
                            SharedPreferences.Editor Edit = sumPref.edit();
                            Edit.putInt("EnrichProd", EnrichProduct);
                            Edit.commit();
                        } else {
                            EnrichProduct += ingredient / 2;
                            ingredient = 0;
                            SharedPreferences.Editor ingredientEditor = Ingredient.edit();
                            ingredientEditor.putInt("ingredients", ingredient);
                            ingredientEditor.commit();
                            SharedPreferences.Editor Edit = sumPref.edit();
                            Edit.putInt("EnrichProd", EnrichProduct);
                            Edit.commit();

                            SharedPreferences.Editor startedEdit = started.edit();
                            startedEdit.putBoolean("EnrichStarted", false);
                            startedEdit.commit();
                            stopForeground(true);
                            stopSelf();
                            enrichTimer.cancel();
                            enrichTimer.purge();

                        }

                    }

                    else if (ingredient <= 1 || EnrichTimer == -1) {
                        EnrichTimer = 3;
                        SharedPreferences.Editor EnrichTimerEditor = EnrichTimerPref.edit();
                        EnrichTimerEditor.putInt("EnrichTimer", EnrichTimer);
                        EnrichTimerEditor.commit();

                        SharedPreferences.Editor startedEdit = started.edit();
                        startedEdit.putBoolean("EnrichStarted", false);
                        startedEdit.commit();

                        stopForeground(true);
                        stopSelf();
                        enrichTimer.cancel();
                        enrichTimer.purge();
                    }
                }
                else if ((enrichStarted) && !(storage - ALLproduct >= numberOfEnrich * 500)){
                    EnrichTimer = 3;
                    SharedPreferences.Editor EnrichTimerEditor = EnrichTimerPref.edit();
                    EnrichTimerEditor.putInt("EnrichTimer", EnrichTimer);
                    EnrichTimerEditor.commit();
                    SharedPreferences isFullPref = getSharedPreferences("Storage", Context.MODE_PRIVATE);
                    SharedPreferences.Editor isFullEdit = isFullPref.edit();
                    isFullEdit.putBoolean("EnrichIsFull", true);
                    isFullEdit.commit();

                    SharedPreferences.Editor startedEdit = started.edit();
                    startedEdit.putBoolean("EnrichStarted", false);
                    startedEdit.commit();
                    stopForeground(true);
                    stopSelf();
                    enrichTimer.cancel();
                    enrichTimer.purge();


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
                    new NotificationCompat.Builder(EnrichProduct.this)
                            .setSmallIcon(R.drawable.icon)
                            .setContentTitle("Feed the World")
                            .setContentText("Storage is Full!")
                            .setAutoCancel(true)
                            .setVibrate(new long[] {1000, 1000})
                            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            Intent resultIntent = new Intent(EnrichProduct.this, MainActivity.class);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(EnrichProduct.this);
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
            startedEdit.putBoolean("EnrichStarted", false);
            startedEdit.commit();
        }

        if (ingredient <= 1 && isNotiOn) {
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(EnrichProduct.this)
                            .setSmallIcon(R.drawable.icon)
                            .setContentTitle("Feed the World")
                            .setContentText("Ingredients are gone!")
                            .setAutoCancel(true)
                            .setVibrate(new long[] {1000, 1000})
                            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            Intent resultIntent = new Intent(EnrichProduct.this, MainActivity.class);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(EnrichProduct.this);
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
