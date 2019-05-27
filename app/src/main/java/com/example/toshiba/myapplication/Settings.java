package com.example.toshiba.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.toshiba.myapplication.Common.Common;
import com.gdacciaro.iOSDialog.iOSDialog;

import java.util.Locale;

import io.paperdb.Paper;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Settings extends AppCompatActivity {

    private Switch mSwitch,royal,dark;

    SharedPref sharedPref;

    String Radio;

    Context context;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLocale();
        sharedPref = new SharedPref(this);
        if (sharedPref.loadNightModelState())
        {
            setTheme(R.style.darkTheme);
        }
        else
            setTheme(R.style.Light);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        mSwitch = findViewById(R.id.mySwitch);
        royal = findViewById(R.id.royal);
        dark = findViewById(R.id.dark);
        if (sharedPref.loadNightModelState())
        {
            mSwitch.setChecked(true);
        }

        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                {
                    sharedPref.setNightModeState(true);
                    restartApp();

                }
                else {
                    sharedPref.setNightModeState(false);
                    restartApp();
                }
            }
        });

        royal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                royal.setChecked(false);

                AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);

                builder.setTitle("Change Theme");
                builder.setMessage("This feature is for pro version !!");
                builder.setIcon(R.drawable.ic_color_lens_black_24dp);
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                });

                builder.show();
            }
        });


        dark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                dark.setChecked(false);

                final AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);

                builder.setTitle("Change Theme");
                builder.setMessage("This feature is for pro version !!");
                builder.setIcon(R.drawable.ic_color_lens_black_24dp);
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.show();
            }
        });

        final RadioButton rdi_Language_eng = findViewById(R.id.language_eng);
        final RadioButton rdi_Language_amh = findViewById(R.id.language_amh);
        final RadioButton rdi_Language_oro = findViewById(R.id.language_oro);
        final RadioButton rdi_Language_tig = findViewById(R.id.language_tig);
        final RadioButton rdi_Language_gez = findViewById(R.id.language_gez);
        Paper.init(this);

        String Check = Paper.book().read(Common.check);

        if (Check == null || Check.equals("1"))
        {
            rdi_Language_eng.setChecked(true);
        }
        else if (Check.equals("2"))
        {
            rdi_Language_amh.setChecked(true);
        }



        rdi_Language_eng.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               if (rdi_Language_eng.isChecked())
               {
                   Paper.book().write(Common.check,"1" );
                   setLocale("en");
                   restartApp();
               }
            }
        });

        rdi_Language_amh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               if (rdi_Language_amh.isChecked())
               {
                   Paper.book().write(Common.check,"2" );
                   setLocale("am");
                   restartApp();

               }
            }
        });

        rdi_Language_oro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                Paper.init(Settings.this);

                AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);

                builder.setTitle("Change Language");
                builder.setIcon(R.drawable.ic_language_black_24dp);
                builder.setMessage("This feature is for pro version !!");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                        String Check = Paper.book().read(Common.check);

                        if (Check == null || Check.equals("1"))
                        {
                            rdi_Language_eng.setChecked(true);
                        }
                        else if (Check.equals("2"))
                        {
                            rdi_Language_amh.setChecked(true);
                        }

                    }
                });

                builder.show();
            }
        });


        rdi_Language_tig.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                Paper.init(Settings.this);

             AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);

                builder.setTitle("Change Language");
                builder.setIcon(R.drawable.ic_language_black_24dp);
                builder.setMessage("This feature is for pro version !!");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();

                        String Check = Paper.book().read(Common.check);

                        if (Check == null || Check.equals("1"))
                        {
                            rdi_Language_eng.setChecked(true);
                        }
                        else if (Check.equals("2"))
                        {
                            rdi_Language_amh.setChecked(true);
                        }

                    }
                });

                builder.show();
            }
        });

        rdi_Language_gez.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                Paper.init(Settings.this);

                AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);

                builder.setTitle("Change Language");
                builder.setIcon(R.drawable.ic_language_black_24dp);
                builder.setMessage("This feature is for pro version !!");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();

                        String Check = Paper.book().read(Common.check);

                        if (Check == null || Check.equals("1"))
                        {
                            rdi_Language_eng.setChecked(true);
                        }
                        else if (Check.equals("2"))
                        {
                            rdi_Language_amh.setChecked(true);
                        }

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
