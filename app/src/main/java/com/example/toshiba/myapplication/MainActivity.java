package com.example.toshiba.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.toshiba.myapplication.Common.Common;

import com.example.toshiba.myapplication.Database.DataSource.FavoritesRepository;
import com.example.toshiba.myapplication.Database.Local.FavoritesDataSource;
import com.example.toshiba.myapplication.Database.Local.GEBETARoomDatabase;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;
import java.util.Locale;
import android.provider.Settings;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    // --Commented out by Inspection (8/6/2019 12:18 PM):private static final int PERMISSION_CODE = 1001;
    // --Commented out by Inspection (8/6/2019 12:18 PM):Button btnContinue;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        loadLocale();
        sharedPref = new SharedPref(this);
        if (sharedPref.loadNightModelState())
        {
            setTheme(R.style.darkTheme);
        }
        else if (sharedPref.loadDarkModelState())
        {
            setTheme(R.style.darkerTheme);
        }
        else if (sharedPref.loadRoyalModelState())
        {
            setTheme(R.style.royalTheme);
        }
        else if (sharedPref.loadLightModelState())
        {
            setTheme(R.style.lightTheme);
        }
        else
            setTheme(R.style.darkTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDB();

        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CALL_PHONE,
                        Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_CONTACTS

                        )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                        if (report.areAllPermissionsGranted())
                        {
                            startActivity(new Intent(MainActivity.this,Home.class));
                        }
                        if (report.isAnyPermissionPermanentlyDenied())
                        {
                            Toast.makeText(MainActivity.this, getResources().getString(R.string.you_must_accept_this_permission_to_use_our_app), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }

    private void initDB() {


        Common.gebetaRoomDatabase = GEBETARoomDatabase.getInstance(this);
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
