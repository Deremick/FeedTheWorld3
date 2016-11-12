package com.alexanderwolf.feedtheworld;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.CheckedInputStream;

public class MineProduct extends Service {

    int ALLproduct;
    int storage;
    int MineTimer;
    int ingredient;
    int numberOfMine;
    int Mineproduct;
    public MineProduct() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Timer mine = new Timer();
        mine.schedule(new TimerTask() {




            @Override
            public void run() {
                SharedPreferences sumPref = getSharedPreferences("Producters", Context.MODE_PRIVATE);
                SharedPreferences storagePref = getSharedPreferences("Storage", Context.MODE_PRIVATE);
                SharedPreferences MineTimerPref = getSharedPreferences("Timers", Context.MODE_PRIVATE);
                SharedPreferences Ingredient = getSharedPreferences("Ingredients", Context.MODE_PRIVATE);
                ingredient = Ingredient.getInt("ingredients", 0);
                ALLproduct = sumPref.getInt("sumProd", 0);
                Mineproduct = sumPref.getInt("MineProd", 0);
                ALLproduct = sumPref.getInt("sumProd", 0);
                storage = storagePref.getInt("storage", 0);
                numberOfMine = sumPref.getInt("NOMines", 0);
                MineTimer = MineTimerPref.getInt("MineTimer", 5);

                if (storage - ALLproduct >= numberOfMine * 100) {
                    if (MineTimer >= 0 && ingredient > 1) {
                        MineTimer--;
                        SharedPreferences.Editor MineTimerEditor = MineTimerPref.edit();
                        MineTimerEditor.putInt("MineTimer", MineTimer);
                        MineTimerEditor.commit();
                    }
                    if (MineTimer == 0 && ingredient > 1) {
                        MineTimer = 5;
                        SharedPreferences.Editor MineTimerEditor = MineTimerPref.edit();
                        MineTimerEditor.putInt("MineTimer", MineTimer);
                        MineTimerEditor.commit();
                        if ((numberOfMine * 200) < ingredient) {
                            ingredient -= numberOfMine * 200;
                            Mineproduct += numberOfMine * 100;
                            SharedPreferences.Editor ingredientEditor = Ingredient.edit();
                            ingredientEditor.putInt("ingredients", ingredient);
                            ingredientEditor.commit();
                            SharedPreferences.Editor Edit = sumPref.edit();
                            Edit.putInt("MineProd", Mineproduct);
                            Edit.commit();

                        } else {
                            Mineproduct += ingredient / 2;
                            ingredient = 0;
                            SharedPreferences.Editor ingredientEditor = Ingredient.edit();
                            ingredientEditor.putInt("ingredients", ingredient);
                            ingredientEditor.commit();
                            SharedPreferences.Editor Edit = sumPref.edit();
                            Edit.putInt("MineProd", Mineproduct);
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
