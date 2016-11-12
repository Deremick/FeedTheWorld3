package com.alexanderwolf;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alexanderwolf.feedtheworld.Music_Service;
import com.alexanderwolf.feedtheworld.R;
import com.alexanderwolf.feedtheworld.Stock;

import java.text.DecimalFormat;
import java.util.Random;

public class Storage extends AppCompatActivity {
   // float CurrentArfolyam = 2;
    float n;
    float q;
    float money;
    int allProduct;
    int pressed;
    boolean isGoing;

    SeekBar ProductSeekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        final TextView prod = (TextView) findViewById(R.id.ProductSeekBar);
        final TextView arf = (TextView) findViewById(R.id.arfolyam);
        SharedPreferences Product = getSharedPreferences("Producters", Context.MODE_PRIVATE);
        int allProduct = Product.getInt("sumProd", 0);
        SharedPreferences Arfolyam = getSharedPreferences("Arfolyam", Context.MODE_PRIVATE);
        q = Arfolyam.getFloat("arfolyam", 20);

        final TextView Money = (TextView) findViewById(R.id.money);
        SharedPreferences Moneyka = getSharedPreferences("Money", Context.MODE_PRIVATE);
        money = Moneyka.getFloat("money", 0);
        Money.setText(new DecimalFormat("###,###,###.##").format(money) + " $$");

        arf.setText(new DecimalFormat("##.##").format(q) + " $$");
        prod.setText(new DecimalFormat("###,###,###").format(allProduct));
        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(100);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                final TextView prod = (TextView) findViewById(R.id.ProductSeekBar);
                           //     final TextView arf = (TextView) findViewById(R.id.arfolyam);
                                SharedPreferences Product = getSharedPreferences("Producters", Context.MODE_PRIVATE);
                                int allproduct = Product.getInt("sumProd", 0);
                                prod.setText(new DecimalFormat("###,###,###").format(allproduct));
                                SharedPreferences Moneyka = getSharedPreferences("Money", Context.MODE_PRIVATE);
                                money = Moneyka.getFloat("money", 0);
                                final TextView Money = (TextView) findViewById(R.id.money);
                                Money.setText(new DecimalFormat("###,###,###.##").format(money) + " $$");
                             //   tozsde();
                               // SharedPreferences Arfolyam = getSharedPreferences("Arfolyam", Context.MODE_PRIVATE);
                                //q = Arfolyam.getFloat("arfolyam", 2);
                                //arf.setText(Float.toString(q));

                              //  SharedPreferences.Editor qEdit = Arfolyam.edit();
                              //  qEdit.putFloat("arfolyam", )
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        t.start();

        Thread Tozsde = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(3600000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final TextView arf = (TextView) findViewById(R.id.arfolyam);

                                tozsde();
                                SharedPreferences Arfolyam = getSharedPreferences("Arfolyam", Context.MODE_PRIVATE);
                                q = Arfolyam.getFloat("arfolyam", 20);
                                arf.setText(new DecimalFormat("##.##").format(q) + " $$");

                                //Intent Stock = new Intent(Storage.this, Stock.class);
                               // stopService(new Intent (Storage.this, Stock.class));
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Tozsde.start();

    }

    public void tozsde() {
        Intent stock = new Intent(this, Stock.class);
        startService(stock);
    }

    public void sell(View view) {
        SharedPreferences Product = getSharedPreferences("Producters", Context.MODE_PRIVATE);
        allProduct = Product.getInt("sumProd", 0);

        SharedPreferences Arfolyam = getSharedPreferences("Arfolyam", Context.MODE_PRIVATE);
        q = Arfolyam.getFloat("arfolyam", 20);

        SharedPreferences Moneyka = getSharedPreferences("Money", Context.MODE_PRIVATE);
        money = Moneyka.getFloat("money", 0);

        final TextView Money = (TextView) findViewById(R.id.money);
        Money.setText(new DecimalFormat("###,###,###.##").format(money + q * allProduct) + " $$");
        SharedPreferences.Editor Moneyedit = Moneyka.edit();
        Moneyedit.putFloat("money", money + q * allProduct);
        Moneyedit.commit();

        allProduct = 0;

        SharedPreferences.Editor prodEdit = Product.edit();
        prodEdit.putInt("sumProd", allProduct);
        prodEdit.commit();
        prodEdit.putInt("FriendProd", 0);
        prodEdit.commit();
        prodEdit.putInt("RestProd", 0);
        prodEdit.commit();
        prodEdit.putInt("FactProd", 0);
        prodEdit.commit();
        prodEdit.putInt("MineProd", 0);
        prodEdit.commit();
        prodEdit.putInt("EnrichProd", 0);
        prodEdit.commit();
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
            Intent music = new Intent(Storage.this, Music_Service.class);
            startService(music);
        }
        else {
            Intent music = new Intent(Storage.this, Music_Service.class);
            stopService(music);
        }
    }

    public void newStock(View view) {
        SharedPreferences Moneyka = getSharedPreferences("Money", Context.MODE_PRIVATE);
        money = Moneyka.getFloat("money", 0);
        if (money >= 50000) {
            Random n = new Random();
            SharedPreferences Arfolyam = getSharedPreferences("Arfolyam", Context.MODE_PRIVATE);
            SharedPreferences.Editor stockEdit = Arfolyam.edit();
            stockEdit.putFloat("arfolyam", (n.nextInt(41 - 10) + 10) + (n.nextFloat() * 2 - 1));
            stockEdit.commit();
            final TextView arf = (TextView) findViewById(R.id.arfolyam);
            q = Arfolyam.getFloat("arfolyam", 20);
            arf.setText(new DecimalFormat("##.##").format(q) + " $$");
            SharedPreferences.Editor moneyEdit = Moneyka.edit();
            moneyEdit.putFloat("money", money - 50000);
            moneyEdit.commit();
        }
        else {
            Toast.makeText(this, "Not enough Money :(", Toast.LENGTH_SHORT).show();
        }

    }

    public void SellProduct(View view) {
        ProductSeekBar = (SeekBar) findViewById(R.id.ProductSeekBar);
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
    }

}






