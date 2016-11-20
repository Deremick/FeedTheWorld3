package com.alexanderwolf;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.alexanderwolf.feedtheworld.Music_Service;
import com.alexanderwolf.feedtheworld.R;

public class Settings extends AppCompatActivity {

    int pressed;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences pressedPref = getSharedPreferences("Pressed", Context.MODE_PRIVATE);
        pressed = pressedPref.getInt("pressed", 1);



    }

}
