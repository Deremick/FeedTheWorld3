package com.alexanderwolf;

import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alexanderwolf.feedtheworld.MainActivity;
import com.alexanderwolf.feedtheworld.Music_Service;
import com.alexanderwolf.feedtheworld.R;

import java.text.DecimalFormat;


public class Buy extends AppCompatActivity {

    public int numberOfEnrich = 0;
    public int numberOfMine = 0;
    public int numberOfFactory = 0;
    public int numberOfRestaurant = 0;
    public int numberOfFriend = 0;
    public int storage = 0;

    private static EditText IngredientsText;
    private static SeekBar ingredientsSeekBar;
    private static TextView IngredientsTextView;
    private static TextView MoneyText;
    int progressValue;
    int ingredients;
    int pressed;
    boolean isGoing;
    float Money;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        SharedPreferences money = getSharedPreferences("Money", Context.MODE_PRIVATE);
        Money = money.getFloat("money", 0);
        MoneyText = (TextView) findViewById(R.id.MoneyText);
        MoneyText.setText("Money: " + new DecimalFormat("###,###,###.##").format(Money) + " $$");

        SharedPreferences numberOfFriends = getSharedPreferences("Producters", Context.MODE_PRIVATE);
        numberOfFriend = numberOfFriends.getInt("NOFriends", 0);

        SharedPreferences numberOfRests = getSharedPreferences("Producters", Context.MODE_PRIVATE);
        numberOfRestaurant = numberOfRests.getInt("NORests", 0);

        SharedPreferences numberOfFactories = getSharedPreferences("Producters", Context.MODE_PRIVATE);
        numberOfFactory = numberOfFactories.getInt("NOFacts", 0);

        SharedPreferences numberOfMines = getSharedPreferences("Producters", Context.MODE_PRIVATE);
        numberOfMine = numberOfMines.getInt("NOMines", 0);

        SharedPreferences numberOfEnrichments = getSharedPreferences("Producters", Context.MODE_PRIVATE);
        numberOfEnrich = numberOfEnrichments.getInt("NOEnrichments", 0);

        SharedPreferences storagePref = getSharedPreferences("Storage", Context.MODE_PRIVATE);
        storage = storagePref.getInt("storage", 0);

        Ingredients();
    }

    public void buyFriend(View view) {

        SharedPreferences numberOfFriends = getSharedPreferences("Producters", Context.MODE_PRIVATE);
        SharedPreferences money = getSharedPreferences("Money", Context.MODE_PRIVATE);
        Money = money.getFloat("money", 0);
        if (Money > 100) {
            numberOfFriend++;
            SharedPreferences.Editor MoneyEdit = money.edit();
            MoneyEdit.putFloat("money", Money - 100);
            MoneyEdit.commit();
            SharedPreferences.Editor friendEditor = numberOfFriends.edit();
            friendEditor.putInt("NOFriends", numberOfFriend);
            friendEditor.commit();
            Money = money.getFloat("money", 0);
            ingredientsSeekBar.setMax((Math.round(Money) / 2) - 1);
            IngredientsText.setHint("Max: " + new DecimalFormat("###,###,###").format(ingredientsSeekBar.getMax()));
            MoneyText.setText("Money: " + new DecimalFormat("###,###,###.##").format(Money) + " $$");
        }
        else {
            Toast.makeText(this, "You don't have enough money :(", Toast.LENGTH_SHORT).show();
        }

    }

    public void buyRestaurant(View view) {

        SharedPreferences numberOfRests = getSharedPreferences("Producters", Context.MODE_PRIVATE);
        SharedPreferences money = getSharedPreferences("Money", Context.MODE_PRIVATE);
        Money = money.getFloat("money", 0);
        if (Money > 5000) {
            numberOfRestaurant++;
            SharedPreferences.Editor MoneyEdit = money.edit();
            MoneyEdit.putFloat("money", Money - 5000);
            MoneyEdit.commit();
            SharedPreferences.Editor RestEditor = numberOfRests.edit();
            RestEditor.putInt("NORests", numberOfRestaurant);
            RestEditor.commit();
            Money = money.getFloat("money", 0);
            ingredientsSeekBar.setMax((Math.round(Money) / 2) - 1);
            IngredientsText.setHint("Max: " + new DecimalFormat("###,###,###").format(ingredientsSeekBar.getMax()));
            MoneyText.setText("Money: " + new DecimalFormat("###,###,###.##").format(Money) + " $$");
        }
        else {
            Toast.makeText(this, "You don't have enough money :(", Toast.LENGTH_SHORT).show();
        }
    }

    public void buyFactory(View view) {

        SharedPreferences numberOfFactories = getSharedPreferences("Producters", Context.MODE_PRIVATE);
        SharedPreferences money = getSharedPreferences("Money", Context.MODE_PRIVATE);
        Money = money.getFloat("money", 0);
        if (Money > 50000) {
            numberOfFactory++;
            SharedPreferences.Editor MoneyEdit = money.edit();
            MoneyEdit.putFloat("money", Money - 50000);
            MoneyEdit.commit();
            SharedPreferences.Editor FactEditor = numberOfFactories.edit();
            FactEditor.putInt("NOFacts", numberOfFactory);
            FactEditor.commit();
            Money = money.getFloat("money", 0);
            ingredientsSeekBar.setMax((Math.round(Money) / 2) - 1);
            IngredientsText.setHint("Max: " + new DecimalFormat("###,###,###").format(ingredientsSeekBar.getMax()));
            MoneyText.setText("Money: " + new DecimalFormat("###,###,###.##").format(Money) + " $$");
        }
        else {
            Toast.makeText(this, "You don't have enough money :(", Toast.LENGTH_SHORT).show();
        }
    }

    public void buyMine(View view) {
        SharedPreferences numberOfMines = getSharedPreferences("Producters", Context.MODE_PRIVATE);
        SharedPreferences money = getSharedPreferences("Money", Context.MODE_PRIVATE);
        Money = money.getFloat("money", 0);
        if (Money > 1500000) {
            numberOfMine++;
            SharedPreferences.Editor MoneyEdit = money.edit();
            MoneyEdit.putFloat("money", Money - 1500000);
            MoneyEdit.commit();
            SharedPreferences.Editor MineEditor = numberOfMines.edit();
            MineEditor.putInt("NOMines", numberOfMine);
            MineEditor.commit();
            Money = money.getFloat("money", 0);
            ingredientsSeekBar.setMax((Math.round(Money) / 2) - 1);
            IngredientsText.setHint("Max: " + new DecimalFormat("###,###,###").format(ingredientsSeekBar.getMax()));
            MoneyText.setText("Money: " + new DecimalFormat("###,###,###.##").format(Money) + " $$");
        }
        else {
            Toast.makeText(this, "You don't have enough money :(", Toast.LENGTH_SHORT).show();
        }
    }

    public void buyEnrich(View view) {

        SharedPreferences numberOfEnrichments = getSharedPreferences("Producters", Context.MODE_PRIVATE);
        SharedPreferences money = getSharedPreferences("Money", Context.MODE_PRIVATE);
        Money = money.getFloat("money", 0);
        if (Money > 10000000) {
            numberOfEnrich++;
            SharedPreferences.Editor MoneyEdit = money.edit();
            MoneyEdit.putFloat("money", Money - 10000000);
            MoneyEdit.commit();
            SharedPreferences.Editor EnrichEditor = numberOfEnrichments.edit();
            EnrichEditor.putInt("NOEnrichments", numberOfEnrich);
            EnrichEditor.commit();
            Money = money.getFloat("money", 0);
            ingredientsSeekBar.setMax((Math.round(Money) / 2) - 1);
            IngredientsText.setHint("Max: " + new DecimalFormat("###,###,###").format(ingredientsSeekBar.getMax()));
            MoneyText.setText("Money: " + new DecimalFormat("###,###,###.##").format(Money) + " $$");
        }
        else {
            Toast.makeText(this, "You don't have enough money :(", Toast.LENGTH_SHORT).show();
        }


      /*  final Button start = (Button) findViewById(R.id.start_BurgerEnrichment);
        start.setClickable(true); */

    }

    public void buyStorage(View view) {
        SharedPreferences money = getSharedPreferences("Money", Context.MODE_PRIVATE);
        Money = money.getFloat("money", 0);
        if (Money > 100000) {
            storage += 10000;
            SharedPreferences.Editor MoneyEdit = money.edit();
            MoneyEdit.putFloat("money", Money - 100000);
            MoneyEdit.commit();
            SharedPreferences storagePref = getSharedPreferences("Storage", Context.MODE_PRIVATE);
            SharedPreferences.Editor storageEditor = storagePref.edit();
            storageEditor.putInt("storage", storage);
            storageEditor.commit();
            Money = money.getFloat("money", 0);
            ingredientsSeekBar.setMax((Math.round(Money) / 2) - 1);
            IngredientsText.setHint("Max: " + new DecimalFormat("###,###,###").format(ingredientsSeekBar.getMax()));
            MoneyText.setText("Money: " + new DecimalFormat("###,###,###.##").format(Money) + " $$");
        }
        else {
            Toast.makeText(this, "You don't have enough money :(", Toast.LENGTH_SHORT).show();
        }
    }


    public void Ingredients() {
        ingredientsSeekBar = (SeekBar) findViewById(R.id.seekBar);
        IngredientsText = (EditText) findViewById(R.id.editText);
        IngredientsTextView = (TextView) findViewById(R.id.IngredientsTextView);
        SharedPreferences money = getSharedPreferences("Money", Context.MODE_PRIVATE);
        Money = money.getFloat("money", 0);
        ingredientsSeekBar.setMax((Math.round(Money) / 2) - 1);
        IngredientsText.setHint("Max: " + new DecimalFormat("###,###,###").format(ingredientsSeekBar.getMax()));


        ingredientsSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                progressValue = i;
                IngredientsTextView.setText(new DecimalFormat("###,###,###").format(i));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                IngredientsText.setVisibility(View.GONE);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (progressValue == 0) {
                    IngredientsText.setVisibility(View.VISIBLE);
                }

            }
        });



    }

    public void BuyIngredients(View view) {
        SharedPreferences ingredient = getSharedPreferences("Ingredients", Context.MODE_PRIVATE);
        SharedPreferences money = getSharedPreferences("Money", Context.MODE_PRIVATE);
        if ((progressValue != 0) && (Math.round(Money) >= progressValue * 2)) {
            ingredients = ingredient.getInt("ingredients", 0);
            SharedPreferences.Editor ingredientEditor = ingredient.edit();
            ingredientEditor.putInt("ingredients", ingredients += progressValue);
            ingredientEditor.commit();
            Money = money.getFloat("money", 0);
            SharedPreferences.Editor moneyEdit = money.edit();
            moneyEdit.putFloat("money", Money -= progressValue * 2);
            moneyEdit.commit();
            ingredientsSeekBar.setMax((Math.round(Money) / 2) - 1);
            IngredientsText.setHint("Max: " + new DecimalFormat("###,###,###").format(ingredientsSeekBar.getMax()));
            MoneyText.setText("Money: " + new DecimalFormat("###,###,###.##").format(Money) + " $$");
        }
        else {
            progressValue = Integer.parseInt(IngredientsText.getText().toString());
             if (Math.round(Money) >= (progressValue * 2)) {
                 SharedPreferences.Editor ingredientEditor = ingredient.edit();
                 ingredientEditor.putInt("ingredients", ingredients += progressValue);
                 ingredientEditor.commit();
                 Money = money.getFloat("money", 0);
                 SharedPreferences.Editor moneyEdit = money.edit();
                 moneyEdit.putFloat("money", Money -= progressValue * 2);
                 moneyEdit.commit();
                 ingredientsSeekBar.setMax((Math.round(Money) / 2) - 1);
                 IngredientsText.setHint("Max: " + new DecimalFormat("###,###,###").format(ingredientsSeekBar.getMax()));
                 MoneyText.setText("Money: " + new DecimalFormat("###,###,###.##").format(Money) + " $$");
             }
            else {
                 Toast.makeText(Buy.this, "Not enough money :(", Toast.LENGTH_SHORT).show();
             }
        }

        ingredientsSeekBar.setProgress(0);
        IngredientsText.setVisibility(View.VISIBLE);
        IngredientsText.setText("");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
        SharedPreferences pressedPref = getSharedPreferences("Pressed", Context.MODE_PRIVATE);
        pressed = pressedPref.getInt("pressed", 1);
        isGoing = pressedPref.getBoolean("isGoing", false);

        if ((pressed % 2 == 0) && isGoing == false) {
            Intent music = new Intent(Buy.this, Music_Service.class);
            startService(music);
        } else {
            Intent music = new Intent(Buy.this, Music_Service.class);
            stopService(music);
        }


    }
    //pah
}

