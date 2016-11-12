package com.alexanderwolf.feedtheworld;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by User on 2016.11.06..
 */



public class Services extends Service {

    int product = 2;
    int numberOfEnrich = 0;
    boolean onGoing = false;

/*    final class TheThread implements Runnable{
        int serviceId;
        TheThread(int serviceId) {
            this.serviceId  =serviceId;
        }

        @Override
        public void run() {
            synchronized (this) {
                try {
                    wait(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stopSelf(this.serviceId);
            }
        }

    } */


    @Override
    public void onCreate() {
        super.onCreate();
        onGoing = true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

            Timer t = new Timer();
            t.schedule(new TimerTask() {


                @Override
                public void run() {

                    SharedPreferences numberOfEnrichments = getSharedPreferences("Producters", Context.MODE_PRIVATE);
                    numberOfEnrich = numberOfEnrichments.getInt("NOEnrichments", 0);
                    if (numberOfEnrich < 20) {
                        numberOfEnrich++;
                    }


                    SharedPreferences.Editor enrichEditor = numberOfEnrichments.edit();
                    enrichEditor.putInt("NOEnrichments", numberOfEnrich);
                    enrichEditor.commit();

                }

            }, 1000, 1000);





        Toast.makeText(Services.this, "Service started", Toast.LENGTH_LONG).show();
       // Thread thread = new Thread(new TheThread(startId));
        //thread.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(Services.this, "Service destroyed", Toast.LENGTH_LONG).show();
        onGoing = false;

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
