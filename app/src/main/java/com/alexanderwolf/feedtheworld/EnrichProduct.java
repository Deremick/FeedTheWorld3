package com.alexanderwolf.feedtheworld;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
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



    public EnrichProduct() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Timer enrichTimer = new Timer();
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


                if (storage - ALLproduct >= numberOfEnrich * 500) {

                    if (EnrichTimer >= 0 && ingredient > 1) {
                        EnrichTimer--;
                        SharedPreferences.Editor EnrichTimerEditor = EnrichTimerPref.edit();
                        EnrichTimerEditor.putInt("EnrichTimer", EnrichTimer);
                        EnrichTimerEditor.commit();
                    }
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

                        }

                    }
                }
                else {
                    EnrichTimer = 3;
                    SharedPreferences.Editor EnrichTimerEditor = EnrichTimerPref.edit();
                    EnrichTimerEditor.putInt("EnrichTimer", EnrichTimer);
                    EnrichTimerEditor.commit();
                }
            }

        }, 1000, 1000);
        return START_NOT_STICKY;
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
