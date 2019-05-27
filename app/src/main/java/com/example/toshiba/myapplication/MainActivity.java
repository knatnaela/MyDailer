package com.example.toshiba.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.widget.Button;

import com.example.toshiba.myapplication.Common.Common;

import com.example.toshiba.myapplication.Database.DataSource.CartRepository;
import com.example.toshiba.myapplication.Database.DataSource.FavoritesRepository;
import com.example.toshiba.myapplication.Database.Local.CartDataSource;
import com.example.toshiba.myapplication.Database.Local.FavoritesDataSource;
import com.example.toshiba.myapplication.Database.Local.GEBETARoomDatabase;

import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1001;
    Button btnContinue;
    SharedPref sharedPref;

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            startActivity(new Intent(MainActivity.this,Home.class));
        }
    };


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        loadLocale();
        sharedPref = new SharedPref(this);
        if (sharedPref.loadNightModelState())
        {
            setTheme(R.style.darkTheme);
        }
        else
            setTheme(R.style.Light);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/CALIBRI.TTF")
                .setFontAttrId(R.attr.fontPath)
                .build());
        setContentView(R.layout.activity_main);


        initDB();


    handler.postDelayed(runnable,500 );




    }

    private void initDB() {


        Common.gebetaRoomDatabase = GEBETARoomDatabase.getInstance(this);
        Common.cartRepository = CartRepository.getInstance(CartDataSource.getInstance(Common.gebetaRoomDatabase.cartDAO()));
        Common.favoritesRepository = FavoritesRepository.getInstance(FavoritesDataSource.getInstance(Common.gebetaRoomDatabase.favoritesDAO()));


    }

    private void setLocale(String lang) {

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    public void loadLocale() {
        SharedPreferences pref = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = pref.getString("My_Lang", "");
        setLocale(language);
    }


}
