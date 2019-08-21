package com.example.toshiba.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {


    private final SharedPreferences mPreferences;

    public SharedPref(Context context) {

        mPreferences = context.getSharedPreferences("filename",Context.MODE_PRIVATE );
    }

    private void setNightModeState(Boolean state){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean("NightMode",state );
        editor.apply();
    }

    private void setRoyalModeState(Boolean state){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean("RoyalMode",state );
        editor.apply();
    }

    private void setDarkModeState(Boolean state){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean("DarkMode",state );
        editor.apply();
    }


    private void setLightModeState(Boolean state){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putBoolean("LightMode",state );
        editor.apply();
    }

    public Boolean loadNightModelState(){

        return mPreferences.getBoolean("NightMode",false );
    }

    public Boolean loadRoyalModelState(){

        return mPreferences.getBoolean("RoyalMode",false );
    }

    public Boolean loadDarkModelState(){

        return mPreferences.getBoolean("DarkMode",false );
    }


    public Boolean loadLightModelState(){

        return mPreferences.getBoolean("LightMode",false );
    }


}
