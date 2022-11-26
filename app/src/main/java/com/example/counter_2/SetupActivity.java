package com.example.counter_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaTimestamp;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;

public class SetupActivity extends AppCompatActivity {

    int upperLimit =20;
    int lowerLimit =10;
    Button uPlus,UMinus,dPlus,dMinus;
    EditText upvalue,dvalue;
    CheckBox upVib;
    CheckBox upSound;
    CheckBox lowVib;
    CheckBox lowSound;

    SetupClass setupClass;
    Context context;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        uPlus=(Button)findViewById(R.id.up_plus);
        UMinus=(Button)findViewById(R.id.up_minus);
        upvalue=(EditText) findViewById(R.id.upperLimit);
        dPlus=(Button)findViewById(R.id.low_plus);
        dMinus=(Button)findViewById(R.id.low_minus);
        dvalue=(EditText) findViewById(R.id.lowerLimit);

        upSound=(CheckBox)findViewById(R.id.up_sound);
        lowSound=(CheckBox)findViewById(R.id.low_sound);
        upVib=(CheckBox)findViewById(R.id.up_vib);
        lowVib=(CheckBox)findViewById(R.id.low_vib);




        context = getApplicationContext();
        sharedPrefs=context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        editor = sharedPrefs.edit();

        setupClass = SetupClass.getInstance(context);


        upvalue.setText(String.valueOf(setupClass.upperLimit));
        dvalue.setText(String.valueOf(setupClass.lowerLimit));

        uPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upperLimit++;
                //int upperLimit, int lowerLimit, int currentValue,
                //boolean upperVib, boolean lowerVib, boolean upperSound,boolean lowerSound
                upvalue.setText(String.valueOf(upperLimit));

            }

        });
        UMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(upperLimit-1==lowerLimit)
                    return;
                upperLimit--;
                upvalue.setText(String.valueOf(upperLimit));

            }

        });

        dPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lowerLimit+1==upperLimit)
                    return;
                lowerLimit++;
                dvalue.setText(String.valueOf(lowerLimit));

            }

        });
        dMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lowerLimit--;
                dvalue.setText(String.valueOf(lowerLimit));

            }

        });


    }

    @Override
    protected void onPause() {
        super.onPause();

        SetupClass setupClass = SetupClass.getInstance(context);
        setupClass.setValues(upperLimit,lowerLimit,setupClass.currentValue,upVib.isChecked(),lowVib.isChecked(),upSound.isChecked(),lowSound.isChecked());
        editor.putInt("upperLimit", upperLimit);
        editor.commit();
    }
    @Override
    protected void onResume() {
        super.onResume();
        setupClass.loadValues();
        getPreferences();
    }
    private void getPreferences() {
        upperLimit = setupClass.upperLimit;
        lowerLimit = setupClass.lowerLimit;
        upSound.setChecked(setupClass.upperSound);
        upVib.setChecked(setupClass.upperVib);
        lowSound.setChecked(setupClass.lowerSound);
        lowVib.setChecked(setupClass.lowerVib);
    }
}