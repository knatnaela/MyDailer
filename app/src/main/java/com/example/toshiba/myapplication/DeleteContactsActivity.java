package com.example.toshiba.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.CallLog;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.toshiba.myapplication.Adapters.DeleteContactsRecyclerViewAdapter;
import com.example.toshiba.myapplication.Adapters.DeleteRecyclerViewAdapter;
import com.example.toshiba.myapplication.Common.Common;
import com.example.toshiba.myapplication.Helper.ContactHelper;
import com.example.toshiba.myapplication.Model.ModelCalls;
import com.example.toshiba.myapplication.Model.ModelContacts;
import com.example.toshiba.myapplication.ViewHolder.DeleteContactsViewHolder;
import com.rey.material.widget.CheckBox;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class DeleteContactsActivity extends AppCompatActivity {


    List<ModelContacts> localDataSource = new ArrayList<>();


    RecyclerView recyclerView;

    DeleteContactsRecyclerViewAdapter adapter;
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
        setContentView(R.layout.activity_delete_contacts);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Delete Contacts");
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_delete_contacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getAll();
    }

    private void getAll() {


        List<ModelContacts> lists = new ArrayList<>();


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_CODE);

        Cursor cursor = this.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                null,null, ContactsContract.Contacts.DISPLAY_NAME + " ASC");

        int photo = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            assert cursor != null;
            photo = cursor.getColumnIndex(CallLog.Calls.CACHED_PHOTO_URI);
        }

        assert cursor != null;
        if (cursor.getCount() > 0)
        {
            if (cursor.moveToFirst())
            {
                do {
                    lists.add(new ModelContacts(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                    )),cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)),cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)),cursor.getString(photo)));



                } while (cursor.moveToNext());
            }
        }

        displayLists(lists);
    }

    private void displayLists(List<ModelContacts> lists) {

        localDataSource = lists;
        adapter = new DeleteContactsRecyclerViewAdapter(this, lists);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.delete, menu);

         return true;
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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.delete_menu) {

            deleteContacts();
        }





        return super.onOptionsItemSelected(item);
    }

    private void deleteContacts() {

        for (String line:Common.numberAdded)
        {
            ContactHelper.deleteContact(getContentResolver(),line);
        }

        Toast.makeText(this, "Contact has been deleted !", Toast.LENGTH_SHORT).show();
        getAll();
    }


    @Override
    protected void onResume() {
        getAll();
        super.onResume();
    }
}
