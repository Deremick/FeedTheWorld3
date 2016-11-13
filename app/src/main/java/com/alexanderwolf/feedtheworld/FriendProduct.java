package com.alexanderwolf.feedtheworld;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

public class FriendProduct extends Service {

    int ingredient;
    int FriendTimer = 15;
    int FriendProduct;
    int ALLproduct;
    int storage;
    int numberOfFriends;

    public FriendProduct() {
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

            final Timer friend = new Timer();
            friend.schedule(new TimerTask() {

                @Override
                public void run() {
                    SharedPreferences sumPref = getSharedPreferences("Producters", Context.MODE_PRIVATE);
                    SharedPreferences storagePref = getSharedPreferences("Storage", Context.MODE_PRIVATE);
                    SharedPreferences friendTimer = getSharedPreferences("Timers", Context.MODE_PRIVATE);
                    SharedPreferences Ingredient = getSharedPreferences("Ingredients", Context.MODE_PRIVATE);
                    ingredient = Ingredient.getInt("ingredients", 0);
                    FriendProduct = sumPref.getInt("FriendProd", 0);
                    ALLproduct = sumPref.getInt("sumProd", 0);
                    storage = storagePref.getInt("storage", 0);
                    numberOfFriends = sumPref.getInt("NOFriends", 0);
                        FriendTimer = friendTimer.getInt("FriendTimer", 15);
                    SharedPreferences started = getSharedPreferences("Started", Context.MODE_PRIVATE);
                    boolean FriendsStarted = started.getBoolean("FriendsStarted", false);
                        if ((storage - ALLproduct >= numberOfFriends) && FriendsStarted)  {
                            if (FriendTimer >= 0 && ingredient > 1) {
                                FriendTimer--;
                                SharedPreferences.Editor FriendTimerEditor = friendTimer.edit();
                                FriendTimerEditor.putInt("FriendTimer", FriendTimer);
                                FriendTimerEditor.commit();

                            }
                            ingredient = Ingredient.getInt("ingredients", 0);
                            FriendTimer = friendTimer.getInt("FriendTimer", 15);
                            if (FriendTimer == 0 && ingredient > 1) {
                                FriendTimer = 15;
                                SharedPreferences.Editor FriendTimerEditor = friendTimer.edit();
                                FriendTimerEditor.putInt("FriendTimer", FriendTimer);
                                FriendTimerEditor.commit();
                                if ((numberOfFriends * 2) < ingredient) {
                                    ingredient -= numberOfFriends * 2;
                                    FriendProduct += numberOfFriends;
                                    SharedPreferences.Editor ingredientEditor = Ingredient.edit();
                                    ingredientEditor.putInt("ingredients", ingredient);
                                    ingredientEditor.commit();
                                    SharedPreferences.Editor FriendEdit = sumPref.edit();
                                    FriendEdit.putInt("FriendProd", FriendProduct);
                                    FriendEdit.commit();

                                } else {
                                    FriendProduct += ingredient / 2;
                                    ingredient = 0;
                                    SharedPreferences.Editor ingredientEditor = Ingredient.edit();
                                    ingredientEditor.putInt("ingredients", ingredient);
                                    ingredientEditor.commit();
                                    SharedPreferences.Editor FriendEdit = sumPref.edit();
                                    FriendEdit.putInt("FriendProd", FriendProduct);
                                    FriendEdit.commit();
                                    SharedPreferences.Editor startedEdit = started.edit();
                                    startedEdit.putBoolean("FriendsStarted", false);
                                    startedEdit.commit();
                                    stopForeground(true);
                                    stopSelf();
                                    friend.cancel();
                                    friend.purge();
                                }

                            }
                            else if (ingredient == 0 || FriendTimer == -1){
                                FriendTimer = 15;
                                SharedPreferences.Editor FriendTimerEditor = friendTimer.edit();
                                FriendTimerEditor.putInt("FriendTimer", FriendTimer);
                                FriendTimerEditor.commit();

                                SharedPreferences.Editor startedEdit = started.edit();
                                startedEdit.putBoolean("FriendsStarted", false);
                                startedEdit.commit();

                                stopForeground(true);
                                stopSelf();
                                friend.cancel();
                                friend.purge();
                            }


                        } else if (FriendsStarted && !(storage - ALLproduct >= numberOfFriends)){
                            FriendTimer = 15;
                            SharedPreferences.Editor FriendTimerEditor = friendTimer.edit();
                            FriendTimerEditor.putInt("FriendTimer", FriendTimer);
                            FriendTimerEditor.commit();
                            SharedPreferences isFullPref = getSharedPreferences("Storage", Context.MODE_PRIVATE);
                            SharedPreferences.Editor isFullEdit = isFullPref.edit();
                            isFullEdit.putBoolean("FriendIsFull", true);
                            isFullEdit.commit();

                            SharedPreferences.Editor startedEdit = started.edit();
                            startedEdit.putBoolean("FriendsStarted", false);
                            startedEdit.commit();
                            stopForeground(true);
                            stopSelf();
                            friend.cancel();
                            friend.purge();
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
        boolean IsFriendFull = isFullPref.getBoolean("FriendIsFull", false);
        boolean IsFactFull = isFullPref.getBoolean("FactIsFull", false);
        boolean IsRestFull = isFullPref.getBoolean("RestIsFull", false);
        boolean IsMineFull = isFullPref.getBoolean("MineIsFull", false);
        boolean IsEnrichFull = isFullPref.getBoolean("EnrichIsFull", false);
        boolean notiShown = isFullPref.getBoolean("notiShown", false);
        ingredient = Ingredient.getInt("ingredients", 0);
        if ((IsFriendFull || IsFactFull || IsRestFull || IsMineFull || IsEnrichFull) && !notiShown) {
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(FriendProduct.this)
                            .setSmallIcon(R.drawable.icon)
                            .setContentTitle("Feed the World")
                            .setContentText("Storage is Full!")
                            .setAutoCancel(true)
                            .setVibrate(new long[] {1000, 1000})
                            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            Intent resultIntent = new Intent(FriendProduct.this, MainActivity.class);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(FriendProduct.this);
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
            startedEdit.putBoolean("FriendsStarted", false);
            startedEdit.commit();
            stopForeground(true);
        }

        if (ingredient == 0) {
            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(FriendProduct.this)
                            .setSmallIcon(R.drawable.icon)
                            .setContentTitle("Feed the World")
                            .setContentText("Ingredients are gone!")
                            .setAutoCancel(true)
                            .setVibrate(new long[] {1000, 1000})
                            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
            Intent resultIntent = new Intent(FriendProduct.this, MainActivity.class);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(FriendProduct.this);
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
