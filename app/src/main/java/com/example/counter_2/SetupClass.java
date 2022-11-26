package com.example.counter_2;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

public class SetupClass {
    int upperLimit;
    int lowerLimit;
    int currentValue;

    boolean upperVib;
    boolean lowerVib;
    boolean upperSound;
    boolean lowerSound;

    Context context;
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;

    static SetupClass setupClass = null;

        private SetupClass(Context context) {
        this.context = context;
        sharedPrefs = context.getSharedPreferences("setup", Context.MODE_PRIVATE);
        editor = sharedPrefs.edit();
    }
    public static SetupClass getInstance(Context context) {
        if(setupClass == null)
        {
            setupClass = new SetupClass(context);
        }

        return setupClass;
    }

    public void setValues(int upperLimit, int lowerLimit, int currentValue,
                          boolean upperVib, boolean lowerVib, boolean upperSound,boolean lowerSound) {

        this.upperLimit = upperLimit;
        this.lowerLimit = lowerLimit;
        this.currentValue = currentValue;

        this.upperVib = upperVib;
        this.lowerVib = lowerVib;
        this.upperSound= upperSound;
        this.lowerSound = lowerSound;
        saveValues();
    }
    public void setValues(int currentValue) {

        this.currentValue = currentValue;
        saveValues();
    }
    public void saveValues(){
        editor.putInt("upperLimit", upperLimit);
        editor.putInt("lowerLimit", lowerLimit);
        editor.putInt("currentValue", currentValue);
        editor.putBoolean("upperVib", upperVib);
        editor.putBoolean("upperSound", upperSound);
        editor.putBoolean("lowerVib", lowerVib);
        editor.putBoolean("lowerSound", lowerSound);
        editor.commit();
    }
    public void loadValues(){
        upperLimit=sharedPrefs.getInt("upperLimit",25);
        lowerLimit=sharedPrefs.getInt("lowerLimit",0);
        currentValue=sharedPrefs.getInt("currentValue",10);
        upperVib=sharedPrefs.getBoolean("upperVib",true);
        upperSound=sharedPrefs.getBoolean("upperSound",true);
        lowerVib=sharedPrefs.getBoolean("lowerVib",true);
        lowerSound=sharedPrefs.getBoolean("lowerSound",true);
    
    }
}
