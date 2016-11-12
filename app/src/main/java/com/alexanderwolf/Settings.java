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
    Button musicOnOff = (Button) findViewById(R.id.musicSwitcher);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences pressedPref = getSharedPreferences("Pressed", Context.MODE_PRIVATE);
        pressed = pressedPref.getInt("pressed", 1);



    }

    public void musicSwitch(View view) {

        SharedPreferences pressedPref = getSharedPreferences("Pressed", Context.MODE_PRIVATE);
        pressed = pressedPref.getInt("pressed", 1);

        if (pressed % 2 != 0) {
            Intent music = new Intent(Settings.this, Music_Service.class);
            startService(music);
         //   SharedPreferences pressedPref = getSharedPreferences("Pressed", Context.MODE_PRIVATE);
            SharedPreferences.Editor pressedEdit = pressedPref.edit();
            pressed++;
            pressedEdit.putInt("pressed", pressed);
            musicOnOff.setText("On");


        }
        else {
            Intent music = new Intent(Settings.this, Music_Service.class);
            stopService(music);
        //    SharedPreferences pressedPref = getSharedPreferences("Pressed", Context.MODE_PRIVATE);
            SharedPreferences.Editor pressedEdit = pressedPref.edit();
            pressed++;
            pressedEdit.putInt("pressed", pressed);
            musicOnOff.setText("Off");


        }
    }

  /*  @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences pressedPref = getSharedPreferences("Pressed", Context.MODE_PRIVATE);
        pressed = pressedPref.getBoolean("pressed", true);

        if (pressed == true) {
            musicOnOff.setChecked(true);
        }
        else {
            musicOnOff.setChecked(false);
        }
    } */
}
