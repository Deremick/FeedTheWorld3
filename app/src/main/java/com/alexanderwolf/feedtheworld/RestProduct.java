package com.alexanderwolf.feedtheworld;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

public class RestProduct extends Service {

    int ingredient;
    int RestTimer;
    int RestProduct;
    int ALLproduct;
    int storage;
    int numberOfRests;

    public RestProduct() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Timer rest = new Timer();
        rest.schedule(new TimerTask() {
            @Override
            public void run() {
                SharedPreferences sumPref = getSharedPreferences("Producters", Context.MODE_PRIVATE);
                SharedPreferences storagePref = getSharedPreferences("Storage", Context.MODE_PRIVATE);
                SharedPreferences RestTimerPref = getSharedPreferences("Timers", Context.MODE_PRIVATE);
                SharedPreferences Ingredient = getSharedPreferences("Ingredients", Context.MODE_PRIVATE);
                ingredient = Ingredient.getInt("ingredients", 0);
                RestProduct = sumPref.getInt("RestProd", 0);
                ALLproduct = sumPref.getInt("sumProd", 0);
                storage = storagePref.getInt("storage", 0);
                numberOfRests = sumPref.getInt("NORests", 0);
                RestTimer = RestTimerPref.getInt("RestTimer", 10);

                if (storage - ALLproduct >=  numberOfRests * 10) {
                    if (RestTimer >= 0 && ingredient > 1) {
                        RestTimer--;
                        SharedPreferences.Editor RestTimerEditor = RestTimerPref.edit();
                        RestTimerEditor.putInt("RestTimer", RestTimer);
                        RestTimerEditor.commit();
                    }
                    if (RestTimer == 0 && ingredient > 1) {
                        RestTimer = 10;
                        SharedPreferences.Editor RestTimerEditor = RestTimerPref.edit();
                        RestTimerEditor.putInt("RestTimer", RestTimer);
                        RestTimerEditor.commit();
                        if ((numberOfRests * 20) < ingredient) {
                            ingredient -= numberOfRests * 20;
                            RestProduct += numberOfRests * 10;
                            SharedPreferences.Editor ingredientEditor = Ingredient.edit();
                            ingredientEditor.putInt("ingredients", ingredient);
                            ingredientEditor.commit();

                            SharedPreferences.Editor Edit = sumPref.edit();
                            Edit.putInt("RestProd", RestProduct);
                            Edit.commit();

                        } else {
                            RestProduct += ingredient / 2;
                            ingredient = 0;
                            SharedPreferences.Editor ingredientEditor = Ingredient.edit();
                            ingredientEditor.putInt("ingredients", ingredient);
                            ingredientEditor.commit();
                            SharedPreferences.Editor Edit = sumPref.edit();
                            Edit.putInt("RestProd", RestProduct);
                            Edit.commit();
                        }

                    }


                }
                else {
                    RestTimer = 10;
                    SharedPreferences.Editor RestTimerEditor = RestTimerPref.edit();
                    RestTimerEditor.putInt("RestTimer", RestTimer);
                    RestTimerEditor.commit();
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
