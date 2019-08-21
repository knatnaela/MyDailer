package com.example.toshiba.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.CompoundButton;
import android.widget.RadioButton;

import java.util.Locale;


public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        final RadioButton mDefault = findViewById(R.id.mySwitch);
        final RadioButton royal = findViewById(R.id.royal);
        final RadioButton dark = findViewById(R.id.dark);
        final RadioButton light = findViewById(R.id.light);

      /*  if (checkTheme.equals("dark"))
        {
            mDefault.setChecked(true);
            dark.setChecked(false);
            royal.setChecked(false);
            light.setChecked(false);
        }
        else if (checkTheme.equals("darker")){
            dark.setChecked(true);
            mDefault.setChecked(false);
            royal.setChecked(false);
            light.setChecked(false);
        }
        else if (checkTheme.equals("royal")){
            royal.setChecked(true);
            dark.setChecked(false);
            mDefault.setChecked(false);
            light.setChecked(false);
        }
        else if (checkTheme.equals("light")){
            royal.setChecked(false);
            dark.setChecked(false);
            mDefault.setChecked(false);
            light.setChecked(true);
        }*/

        mDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    mDefault.setChecked(true);
                    dark.setChecked(false);
                    royal.setChecked(false);
                    light.setChecked(false);
                    restartApp();
                }
            }
        });

        dark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    dark.setChecked(true);
                    mDefault.setChecked(false);
                    royal.setChecked(false);
                    light.setChecked(false);
                    restartApp();
                }
            }
        });

        royal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    royal.setChecked(true);
                    dark.setChecked(false);
                    mDefault.setChecked(false);
                    light.setChecked(false);
                    restartApp();
                }
            }
        });

        light.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    royal.setChecked(false);
                    dark.setChecked(false);
                    mDefault.setChecked(false);
                    light.setChecked(true);
                    restartApp();
                }
            }
        });

        final RadioButton rdi_Language_eng = findViewById(R.id.language_eng);
        final RadioButton rdi_Language_amh = findViewById(R.id.language_amh);
        final RadioButton rdi_Language_oro = findViewById(R.id.language_oro);
        final RadioButton rdi_Language_tig = findViewById(R.id.language_tig);
        final RadioButton rdi_Language_gez = findViewById(R.id.language_gez);




        rdi_Language_eng.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               if (b)
               {
                   setLocale("en");
                   restartApp();
               }
            }
        });

        rdi_Language_amh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               if (b)
               {
                   setLocale("am");
                   restartApp();

               }
            }
        });

        rdi_Language_oro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {



                AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);

                builder.setTitle("Change Language");
                builder.setIcon(R.drawable.ic_language_black_24dp);
                builder.setMessage("This feature is for pro version !!");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();



                    }
                });

                builder.show();
            }
        });


        rdi_Language_tig.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


             AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);

                builder.setTitle("Change Language");
                builder.setIcon(R.drawable.ic_language_black_24dp);
                builder.setMessage("This feature is for pro version !!");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();



                    }
                });

                builder.show();
            }
        });

        rdi_Language_gez.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {



                AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);

                builder.setTitle("Change Language");
                builder.setIcon(R.drawable.ic_language_black_24dp);
                builder.setMessage("This feature is for pro version !!");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();


                    }
                });

                builder.show();
            }
        });

       if (rdi_Language_oro.isChecked())
        {
            setLocale("om");
            recreate();
        }
        else if (rdi_Language_tig.isChecked())
        {
            setLocale("ti");
            recreate();

        }
        else if (rdi_Language_gez.isChecked())
       {
           setLocale("gez");
           recreate();
       }
    }


    private void restartApp() {

        startActivity(new Intent(getApplicationContext(),Home.class));
        finish();
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
