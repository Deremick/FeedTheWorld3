package com.alexanderwolf.feedtheworld;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.widget.Button;

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
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timer fact = new Timer();
        fact.schedule(new TimerTask() {
            @Override
            public void run() {
                SharedPreferences sumPref = getSharedPreferences("Producters", Context.MODE_PRIVATE);
                SharedPreferences storagePref = getSharedPreferences("Storage", Context.MODE_PRIVATE);
                SharedPreferences FactTimerPref = getSharedPreferences("Timers", Context.MODE_PRIVATE);
                SharedPreferences Ingredient = getSharedPreferences("Ingredients", Context.MODE_PRIVATE);
                ingredient = Ingredient.getInt("ingredients", 0);
                FactProduct = sumPref.getInt("FactProd", 0);
                ALLproduct = sumPref.getInt("sumProd", 0);
                storage = storagePref.getInt("storage", 0);
                numberOfFact = sumPref.getInt("NOFacts", 0);
                FactTimer = FactTimerPref.getInt("FactTimer", 5);
                if (storage - ALLproduct >= numberOfFact * 40) {
                    if (FactTimer >= 0 && ingredient > 1) {
                        FactTimer--;
                        SharedPreferences.Editor FactTimerEditor = FactTimerPref.edit();
                        FactTimerEditor.putInt("FactTimer", FactTimer);
                        FactTimerEditor.commit();
                    }
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
