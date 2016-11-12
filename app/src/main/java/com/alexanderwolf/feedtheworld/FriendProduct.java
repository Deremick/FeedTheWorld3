package com.alexanderwolf.feedtheworld;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
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
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Timer friend = new Timer();
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
                if (storage - ALLproduct >= numberOfFriends) {
                    if (FriendTimer >= 0 && ingredient > 1) {
                        FriendTimer--;
                        SharedPreferences.Editor FriendTimerEditor = friendTimer.edit();
                        FriendTimerEditor.putInt("FriendTimer", FriendTimer);
                        FriendTimerEditor.commit();

                    }
                    FriendTimer = friendTimer.getInt("FriendTimer", 0);
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
                        }

                    }

                }
            }

        }, 1000, 1000);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
