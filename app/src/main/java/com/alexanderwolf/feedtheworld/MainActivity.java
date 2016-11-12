package com.alexanderwolf.feedtheworld;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.alexanderwolf.Buy;
import com.alexanderwolf.Ingredients;
import com.alexanderwolf.Settings;
import com.alexanderwolf.Storage;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    int FriendTimer;
    static int FriendProduct = 0;
    int numberOfFriends = 0;

    int RestTimer;
    static int RestProduct = 0;
    int numberOfRests = 0;

    int FactTimer;
    static int FactProduct = 0;
    int numberOfFact = 0;

    int MineTimer;
    static int MineProduct = 0;
    int numberOfMine = 0;

    int EnrichTimer;
    static int EnrichProduct = 0;
    int numberOfEnrich = 0;

    static int productSum = 0;

    boolean friendsStarted = false;
    boolean restStarted = false;
    boolean factStarted = false;
    boolean mineStarted = false;
    boolean enrichStarted = false;

    int storage;
    int ALLproduct = 0;
    boolean megallit = true;
    float Money;
    int pressed;
    boolean isGoing;

    boolean firstStart;
    private static TextView MoneyText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MoneyText = (TextView) findViewById(R.id.MoneyText);

        SharedPreferences firstPref = getSharedPreferences("FirstStart", Context.MODE_PRIVATE);
        SharedPreferences money = getSharedPreferences("Money", Context.MODE_PRIVATE);
        firstStart = firstPref.getBoolean("firstStart", true);

        if (firstStart) {

            SharedPreferences.Editor moneyEdit = money.edit();
            moneyEdit.putFloat("money", 100110);
            moneyEdit.commit();


            SharedPreferences.Editor firstEdit = firstPref.edit();
            firstEdit.putBoolean("firstStart", false);
            firstEdit.commit();
        }
        Money = money.getFloat("money", 0);
        MoneyText.setText("Money: " + new DecimalFormat("###,###,###.##").format(Money) + " $$");


        SharedPreferences pressedPref = getSharedPreferences("Pressed", Context.MODE_PRIVATE);
        pressed = pressedPref.getInt("pressed", 1);

        if (pressed % 2 == 0) {
            Intent music = new Intent(MainActivity.this, Music_Service.class);
            startService(music);
        }
        else {
            Intent music = new Intent(MainActivity.this, Music_Service.class);
            stopService(music);
        }


        final Button Settings = (Button) findViewById(R.id.Settings_button);

        Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog settings = new Dialog(MainActivity.this);

                settings.setTitle("Settings");
                settings.setContentView(R.layout.activity_settings);
                settings.show();

                SharedPreferences pressedPref = getSharedPreferences("Pressed", Context.MODE_PRIVATE);
                pressed = pressedPref.getInt("pressed", 1);
                final Button musicOnOff = (Button) settings.findViewById(R.id.musicSwitcher);
                if (pressed % 2 != 0) {
                    musicOnOff.setText("Off");
                }
                else {
                    musicOnOff.setText("On");
                }

             //   final Button musicOnOff = (Button) settings.findViewById(R.id.musicSwitcher);
                musicOnOff.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SharedPreferences pressedPref = getSharedPreferences("Pressed", Context.MODE_PRIVATE);
                        pressed = pressedPref.getInt("pressed", 1);

                        if (pressed % 2 != 0) {
                            Intent music = new Intent(MainActivity.this, Music_Service.class);
                            startService(music);
                            //   SharedPreferences pressedPref = getSharedPreferences("Pressed", Context.MODE_PRIVATE);
                            pressed++;
                            SharedPreferences.Editor pressedEdit = pressedPref.edit();
                            pressedEdit.putInt("pressed", pressed);
                            pressedEdit.commit();
                            SharedPreferences.Editor isgoingEditor = pressedPref.edit();
                            isgoingEditor.putBoolean("isGoing", true);
                            isgoingEditor.commit();
                            musicOnOff.setText("On");


                        }
                        else {
                            Intent music = new Intent(MainActivity.this, Music_Service.class);
                            stopService(music);
                            //    SharedPreferences pressedPref = getSharedPreferences("Pressed", Context.MODE_PRIVATE);
                            pressed++;
                            SharedPreferences.Editor pressedEdit = pressedPref.edit();
                            pressedEdit.putInt("pressed", pressed);
                            pressedEdit.commit();
                            SharedPreferences.Editor isgoingEditor = pressedPref.edit();
                            isgoingEditor.putBoolean("isGoing", false);
                            isgoingEditor.commit();
                            musicOnOff.setText("Off");


                        }
                    }
                });
            }
        });

        SharedPreferences numberofFriends = getSharedPreferences("Producters", Context.MODE_PRIVATE);
        numberOfFriends = numberofFriends.getInt("NOFriends", 0);

        SharedPreferences numberOfRestaurants = getSharedPreferences("Producters", Context.MODE_PRIVATE);
        numberOfRests = numberOfRestaurants.getInt("NORests", 0);

        SharedPreferences numberOfFactories = getSharedPreferences("Producters", Context.MODE_PRIVATE);
        numberOfFact = numberOfFactories.getInt("NOFacts", 0);

        SharedPreferences numberOfMines = getSharedPreferences("Producters", Context.MODE_PRIVATE);
        numberOfMine = numberOfMines.getInt("NOMines", 0);

        SharedPreferences numberOfEnrichments = getSharedPreferences("Producters", Context.MODE_PRIVATE);
        numberOfEnrich = numberOfEnrichments.getInt("NOEnrichments", 0);

        SharedPreferences sumPref = getSharedPreferences("Producters", Context.MODE_PRIVATE);
        productSum = sumPref.getInt("sumProd", 0);
        productSum = sumPref.getInt("sumProd", 0);
        FriendProduct = sumPref.getInt("FriendProd", 0);
        RestProduct = sumPref.getInt("RestProd", 0);
        FactProduct = sumPref.getInt("FactProd", 0);
        MineProduct = sumPref.getInt("MineProd", 0);
        EnrichProduct = sumPref.getInt("EnrichProd", 0);

        if (numberOfRests == 0) {
            final Button startRest = (Button) findViewById(R.id.start_Restaurant);
            startRest.setClickable(false);
        }
        if (numberOfFact == 0) {
            final Button startFact = (Button) findViewById(R.id.start_Factory);
            startFact.setClickable(false);
        }
        if (numberOfMine == 0) {
            final Button startMine = (Button) findViewById(R.id.start_BurgerMine);
            startMine.setClickable(false);
        }
        if (numberOfEnrich == 0) {
            final Button startEnrich = (Button) findViewById(R.id.start_BurgerEnrichment);
            startEnrich.setClickable(false);
        }

        final TextView textFriendsTimer = (TextView) findViewById(R.id.Timer_Friends);
        final TextView textFriends = (TextView) findViewById(R.id.NumberOfFriends);

        final TextView textRestTimer = (TextView) findViewById(R.id.Timer_Restaurant);
        final TextView textRest = (TextView) findViewById(R.id.NumberOfRest);

        final TextView textFactTimer = (TextView) findViewById(R.id.Timer_Factory);
        final TextView textFact = (TextView) findViewById(R.id.NumberOfFact);

        final TextView textMineTimer = (TextView) findViewById(R.id.Timer_Mine);
        final TextView textMine = (TextView) findViewById(R.id.NumberOfMine);

        final TextView textEnrichTimer = (TextView) findViewById(R.id.Timer_Enrich);
        final TextView textEnrich = (TextView) findViewById(R.id.NumberOfEnrich);

        final TextView SUM = (TextView) findViewById(R.id.SUM);

        final TextView IngredientsText = (TextView) findViewById(R.id.IngredientsText);


        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(100);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (numberOfRests > 0 && !restStarted) {
                                    final Button startRest = (Button) findViewById(R.id.start_Restaurant);
                                    startRest.setClickable(true);
                                }
                                if (numberOfFact > 0 && !factStarted) {
                                    final Button startFact = (Button) findViewById(R.id.start_Factory);
                                    startFact.setClickable(true);
                                }
                                if (numberOfMine > 0 && !mineStarted) {
                                    final Button startMine = (Button) findViewById(R.id.start_BurgerMine);
                                    startMine.setClickable(true);
                                }
                                if (numberOfEnrich > 0 && !enrichStarted) {
                                    final Button startEnrich = (Button) findViewById(R.id.start_BurgerEnrichment);
                                    startEnrich.setClickable(true);
                                }

                                SharedPreferences numberofFriends = getSharedPreferences("Producters", Context.MODE_PRIVATE);
                                numberOfFriends = numberofFriends.getInt("NOFriends", 0);

                                SharedPreferences numberOfRestaurants = getSharedPreferences("Producters", Context.MODE_PRIVATE);
                                numberOfRests = numberOfRestaurants.getInt("NORests", 0);

                                SharedPreferences numberOfFactories = getSharedPreferences("Producters", Context.MODE_PRIVATE);
                                numberOfFact = numberOfFactories.getInt("NOFacts", 0);

                                SharedPreferences numberOfMines = getSharedPreferences("Producters", Context.MODE_PRIVATE);
                                numberOfMine = numberOfMines.getInt("NOMines", 0);

                                SharedPreferences numberOfEnrichments = getSharedPreferences("Producters", Context.MODE_PRIVATE);
                                numberOfEnrich = numberOfEnrichments.getInt("NOEnrichments", 0);

                                SharedPreferences Timers = getSharedPreferences("Timers", Context.MODE_PRIVATE);
                                FriendTimer = Timers.getInt("FriendTimer", 15);
                                RestTimer = Timers.getInt("RestTimer", 10);
                                FactTimer = Timers.getInt("FactTimer", 5);
                                MineTimer = Timers.getInt("MineTimer", 5);
                                EnrichTimer = Timers.getInt("EnrichTimer", 3);

                                textFriendsTimer.setText(Integer.toString(FriendTimer));
                                textFriends.setText(Integer.toString(numberOfFriends));

                                textRestTimer.setText(Integer.toString(RestTimer));
                                textRest.setText(Integer.toString(numberOfRests));

                                textFactTimer.setText(Integer.toString(FactTimer));
                                textFact.setText(Integer.toString(numberOfFact));

                                textMineTimer.setText(Integer.toString(MineTimer));
                                textMine.setText(Integer.toString(numberOfMine));

                                textEnrichTimer.setText(Integer.toString(EnrichTimer));
                                textEnrich.setText(Integer.toString(numberOfEnrich));

                                SharedPreferences money = getSharedPreferences("Money", Context.MODE_PRIVATE);
                                Money = money.getFloat("money", 0);
                                MoneyText.setText("Money: " + new DecimalFormat("###,###,###.##").format(Money) + " $$");

                                SharedPreferences sumPref = getSharedPreferences("Producters", Context.MODE_PRIVATE);
                                productSum = sumPref.getInt("sumProd", 0);
                                productSum = sumPref.getInt("sumProd", 0);
                                FriendProduct = sumPref.getInt("FriendProd", 0);
                                RestProduct = sumPref.getInt("RestProd", 0);
                                FactProduct = sumPref.getInt("FactProd", 0);
                                MineProduct = sumPref.getInt("MineProd", 0);
                                EnrichProduct = sumPref.getInt("EnrichProd", 0);

                                SharedPreferences storagePref = getSharedPreferences("Storage", Context.MODE_PRIVATE);
                                storage = storagePref.getInt("storage", 0);


                                SUM.setText(new DecimalFormat("###,###,###").format(storage) + " / " + new DecimalFormat("###,###,###").format(productSum + EnrichProduct + MineProduct + FactProduct + RestProduct + FriendProduct));

                               // if (productSum + EnrichProduct + MineProduct + FactProduct + RestProduct + FriendProduct <= storage) {

                                    SharedPreferences.Editor sumEdit = sumPref.edit();
                                    sumEdit.putInt("sumProd", productSum + EnrichProduct + MineProduct + FactProduct + RestProduct + FriendProduct);
                                    sumEdit.commit();

                                    sumEdit.putInt("FriendProd", 0);
                                    sumEdit.commit();
                                    sumEdit.putInt("RestProd", 0);
                                    sumEdit.commit();
                                    sumEdit.putInt("FactProd", 0);
                                    sumEdit.commit();
                                    sumEdit.putInt("MineProd", 0);
                                    sumEdit.commit();
                                    sumEdit.putInt("EnrichProd", 0);
                                    sumEdit.commit();
                                SharedPreferences ingredient = getSharedPreferences("Ingredients", Context.MODE_PRIVATE);

                                SharedPreferences.Editor storageEdit = storagePref.edit();
                                storageEdit.putInt("storage", storage);
                                int ingredients = ingredient.getInt("ingredients", 0);


                                IngredientsText.setText(new DecimalFormat("###,###,###").format(ingredients));
                                }
                        });
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        t.start();

    }

    public void startFriends(View view) {
        friendsStarted = true;
        final Button startFriends = (Button) findViewById(R.id.Start_Friends);
        startFriends.setClickable(false);
        Intent FriendIntent = new Intent(this, FriendProduct.class);
        startService(FriendIntent);
    }


    public void startRestaurant(View view) {
        restStarted = true;
        final Button startRest = (Button) findViewById(R.id.start_Restaurant);
        startRest.setClickable(false);
        Intent RestIntent = new Intent(this, RestProduct.class);
        startService(RestIntent);
    }

    public void startFactory(View view) {
        factStarted = true;
        final Button startFact = (Button) findViewById(R.id.start_Factory);
        startFact.setClickable(false);
        Intent FactIntent = new Intent(this, FactProduct.class);
        startService(FactIntent);
    }


    public void startMine(View view) {
        mineStarted = true;
        final Button startMine = (Button) findViewById(R.id.start_BurgerMine);
        startMine.setClickable(false);
        Intent MineIntent = new Intent(this, MineProduct.class);
        startService(MineIntent);


    /*    Timer mine = new Timer();
        mine.schedule(new TimerTask() {
            @Override
            public void run() {
                SharedPreferences sumPref = getSharedPreferences("Producters", Context.MODE_PRIVATE);
                SharedPreferences storagePref = getSharedPreferences("Storage", Context.MODE_PRIVATE);
                storage = storagePref.getInt("storage", 0);
                ALLproduct = sumPref.getInt("sumProd", 0);
                if (ALLproduct < storage) {
                    if (MineTimer >= 0 && ingredient > 0) {
                        MineTimer--;

                    }
                    if (MineTimer == 0 && ingredient > 0) {
                        MineTimer = 5;
                    }

                }
            }
        }, 1000, 1000); */
    }


    public void startEnrich(View view) {
        enrichStarted = true;
        final Button startEnrich = (Button) findViewById(R.id.start_BurgerEnrichment);
        startEnrich.setClickable(false);
        Intent enrichIntent = new Intent(this, com.alexanderwolf.feedtheworld.EnrichProduct.class);
        startService(enrichIntent);
    }

   /* public int sumP() {

                if (FriendTimer == 0 || RestTimer == 0 || FactTimer == 0 || MineTimer == 0 || EnrichTimer == 0) {
                    productSum = FriendProduct + RestProduct + FactProduct + MineProduct + EnrichProduct;

                    SharedPreferences sumPref = getSharedPreferences("Producters", Context.MODE_PRIVATE);
                    SharedPreferences.Editor sumEdit = sumPref.edit();
                    sumEdit.putInt("sumProd", productSum);
                    sumEdit.commit();

                }
        return productSum;
    } */

    public void Shop(View view) {
        megallit = false;
        Intent shop = new Intent("com.alexanderwolf.Buy");
        shop.setClass(MainActivity.this, Buy.class);
        startActivity(shop);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (megallit == true) {
            Intent music = new Intent(this, Music_Service.class);
            stopService(music);
            megallit = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (megallit == false) {
            SharedPreferences pressedPref = getSharedPreferences("Pressed", Context.MODE_PRIVATE);
            pressed = pressedPref.getInt("pressed", 1);
            isGoing = pressedPref.getBoolean("isGoing", false);

            if ((pressed % 2 == 0) && isGoing == false) {
                Intent music = new Intent(MainActivity.this, Music_Service.class);
                startService(music);
            }
            else {
                Intent music = new Intent(MainActivity.this, Music_Service.class);
                stopService(music);
            }

        }
        megallit = true;

    }

 /*   public void Settings() {
        final Button Settings = (Button) findViewById(R.id.Settings_button);

        Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog settings = new Dialog(MainActivity.this);
                settings.setTitle("Settings");
                settings.setContentView(R.layout.activity_settings);
                settings.show();

                final ToggleButton musicOnOff = (ToggleButton) settings.findViewById(R.id.toggleButton2);
                musicOnOff.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (musicOnOff.isChecked()) {
                            Intent music = new Intent(MainActivity.this, Music_Service.class);
                            startService(music);
                        }
                        if (!(musicOnOff.isChecked())) {
                            Intent music = new Intent(MainActivity.this, Music_Service.class);
                            stopService(music);
                        }
                    }
                });
            }
        });


    } */

    public void StorageAct(View view) {
        Intent storageAct = new Intent("com.alexanderwolf.Storage");
        storageAct.setClass(MainActivity.this, Storage.class);
        startActivity(storageAct);
    }

  /*  public void IngredientsShop(View view) {
        Intent ingredient = new Intent("com.alexanderwolf.Ingredients");
        ingredient.setClass(MainActivity.this, Ingredients.class);
        startActivity(ingredient);
    } */
}



