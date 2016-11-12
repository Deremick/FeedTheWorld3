package com.alexanderwolf.feedtheworld;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class Music_Service extends Service {

    MediaPlayer song;

    public Music_Service() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        song = MediaPlayer.create(Music_Service.this, R.raw.recording);
        song.setLooping(true);
        song.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        song.pause();
        song.release();
    }



    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
