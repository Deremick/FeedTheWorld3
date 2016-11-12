package com.alexanderwolf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alexanderwolf.feedtheworld.Music_Service;
import com.alexanderwolf.feedtheworld.R;

public class Ingredients extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent music = new Intent(this, Music_Service.class);
        stopService(music);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent music = new Intent(this, Music_Service.class);
        startService(music);
    }
}
