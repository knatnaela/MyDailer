package com.example.toshiba.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {


    SharedPreferences mPreferences;

    public SharedPref(Context context) {

        mPreferences = context.getSharedPreferences("filename",Context.MODE_PRIVATE );
    }

    public void setNightModeState(Boolean state){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean("NightMode",state );
        editor.apply();
    }

    public Boolean loadNightModelState(){

        Boolean state = mPreferences.getBoolean("NightMode",true );
        return state;
    }
}
