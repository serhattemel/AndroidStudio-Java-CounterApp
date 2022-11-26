package com.example.counter_2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView value,deneme,deneme4;
    Button minus, plus, setup;

    SetupClass setupClass;
    int upperLimit;
    int lowerLimit;
    int currentValue;

    boolean upperVib;
    boolean lowerVib;
    boolean upperSound;
    boolean lowerSound;

    Vibrator vibrator = null;
    MediaPlayer player = null;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent splash = new Intent(MainActivity.this, SplashScreen.class);
        startActivity(splash);
        value = (TextView) findViewById(R.id.value);
        deneme = (TextView) findViewById(R.id.deneme);
        deneme4 = (TextView) findViewById(R.id.deneme4);
        minus = (Button) findViewById(R.id.minus);
        plus = (Button) findViewById(R.id.plus);
        setup = (Button) findViewById(R.id.setup);

        Context context = getApplicationContext();
        setupClass = SetupClass.getInstance(context);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        player = MediaPlayer.create(context, R.raw.beeptone);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                valueUpdate(+1);
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valueUpdate(-1);
            }
        });

        setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SetupActivity.class);
                startActivity(i);

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupClass.loadValues();
        getPreferences();
        deneme.setText(String.valueOf(upperLimit));
        deneme4.setText(String.valueOf(lowerLimit));
    }

    private void getPreferences() {
        currentValue = setupClass.currentValue;
        upperLimit = setupClass.upperLimit;
        lowerLimit = setupClass.lowerLimit;
        upperVib = setupClass.upperVib;
        lowerVib = setupClass.lowerVib;
        upperSound = setupClass.upperSound;
        lowerSound = setupClass.lowerSound;
    }

    @Override
    protected void onPause() {

        super.onPause();
        setupClass.setValues(currentValue);
    }
    //Vibrator vibrator = null;
    //MediaPlayer player = null;
    //vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    //player = MediaPlayer.create(context, R.raw.beeptone);
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if(action==KeyEvent.ACTION_UP)
                    valueUpdate(+5);
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if(action==KeyEvent.ACTION_DOWN)
                    valueUpdate(-5);
                break;


        }
        return super.dispatchKeyEvent(event);
    }

    private void valueUpdate(int step) {
        if (step < 0) {
            if (currentValue + step < lowerLimit)
            {
                currentValue = lowerLimit;
                if (setupClass.lowerSound == true)
                    player.start();
                if (setupClass.lowerVib == true &&Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    Toast.makeText(getApplicationContext(),"titreşim",Toast.LENGTH_SHORT).show();
                    vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
                }
            }
            else
            {
                currentValue+=step;
            }

        }
        else
        {
            if (currentValue + step > upperLimit)
            {
                currentValue = upperLimit;
                if (setupClass.upperSound == true)
                    player.start();
                if (setupClass.upperVib == true&&Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                {
                    Toast.makeText(getApplicationContext(),"titreşim",Toast.LENGTH_SHORT).show();
                    vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
                }
            }
            else
            {
                currentValue = currentValue + step;
            }
        }
        value.setText(String.valueOf(currentValue));
    }
}