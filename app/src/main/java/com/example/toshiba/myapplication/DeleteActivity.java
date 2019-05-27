package com.example.toshiba.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.toshiba.myapplication.Adapters.CallsRecyclerViewAdapter;
import com.example.toshiba.myapplication.Adapters.ContactsRecyclerViewAdapter;
import com.example.toshiba.myapplication.Adapters.DeleteRecyclerViewAdapter;
import com.example.toshiba.myapplication.Common.Common;
import com.example.toshiba.myapplication.Model.ModelCalls;
import com.example.toshiba.myapplication.Model.ModelContacts;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.rey.material.widget.CheckBox;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class DeleteActivity extends AppCompatActivity {


    List<ModelCalls> localDataSource = new ArrayList<>();


    RecyclerView recyclerView;

    DeleteRecyclerViewAdapter adapter;
    SharedPref sharedPref;
    private int PERMISSION_CODE = 1000;

    CheckBox checkBox;

    int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        sharedPref = new SharedPref(this);
        if (sharedPref.loadNightModelState())
        {
            setTheme(R.style.darkTheme);
        }
        else
            setTheme(R.style.Light);
        setContentView(R.layout.activity_delete);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Delete Call log");
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_delete);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        getAll();

    }

    private void getAll() {

        List<ModelCalls> lists = new ArrayList<>();


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED)

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALL_LOG}, PERMISSION_CODE);

        Cursor cursor = this.getContentResolver().query(CallLog.Calls.CONTENT_URI, null,
                null, null, CallLog.Calls.DATE + " DESC");

        if (cursor !=null) {
            while (cursor.moveToNext()) {
                 number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
                int name = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
                int date = cursor.getColumnIndex(CallLog.Calls.DATE);
                int type = cursor.getColumnIndex(CallLog.Calls.TYPE);

                int id = cursor.getColumnIndex(CallLog.Calls._ID);



                int photo = 0;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    photo = cursor.getColumnIndex(CallLog.Calls.CACHED_PHOTO_URI);
                }
                Date date1 = new Date(Long.valueOf(cursor.getString(date)));
                String month_date, week_day, month;
                month_date = (String) DateFormat.format("dd", date1);
                week_day = (String) DateFormat.format("EEEE", date1);
                month = (String) DateFormat.format("MMMM", date1);
                lists.add(new ModelCalls(cursor.getString(name), cursor.getString(number), week_day + " " + month_date
                        + " " + month, cursor.getString(photo),cursor.getString(id), cursor.getString(type)));
            }

            cursor.close();
        }

        displayLists(lists);
    }

    private void displayLists(List<ModelCalls> lists) {

            localDataSource = lists;
            adapter = new DeleteRecyclerViewAdapter(this, lists);
            recyclerView.setAdapter(adapter);

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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.delete, menu);

        getMenuInflater().inflate(R.menu.submenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.delete_menu) {

            for (String line:Common.numberAdded) {

                Uri uri = Uri.parse("content://call_log/calls");

                this.getContentResolver().delete(uri, CallLog.Calls.NUMBER + " = ?", new String[]{line});

                Toast.makeText(this, "CallLog has been deleted !", Toast.LENGTH_SHORT).show();


                getAll();
            }

        }

        if (id == R.id.sub)
        {
           this.getContentResolver().delete(CallLog.Calls.CONTENT_URI,Common.currentCall.getNumber() ,null );
            Toast.makeText(this, "All CallLogs has been deleted !", Toast.LENGTH_SHORT).show();
            getAll();
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        getAll();
        super.onResume();
    }
}