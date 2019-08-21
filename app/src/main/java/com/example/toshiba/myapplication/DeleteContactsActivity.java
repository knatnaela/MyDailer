package com.example.toshiba.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.provider.CallLog;
import android.provider.ContactsContract;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.toshiba.myapplication.Adapters.DeleteContactsRecyclerViewAdapter;
import com.example.toshiba.myapplication.Common.Common;
import com.example.toshiba.myapplication.Helper.ContactHelper;
import com.example.toshiba.myapplication.Model.ModelContacts;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DeleteContactsActivity extends AppCompatActivity {


    // --Commented out by Inspection (8/6/2019 12:18 PM):List<ModelContacts> localDataSource = new ArrayList<>();


    RecyclerView recyclerView;

    DeleteContactsRecyclerViewAdapter adapter;
   // --Commented out by Inspection (8/6/2019 12:18 PM): SharedPref sharedPref;

    // --Commented out by Inspection (8/6/2019 12:18 PM):CheckBox checkBox;

    // --Commented out by Inspection (8/6/2019 12:18 PM):int number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLocale();
      SharedPref  sharedPref = new SharedPref(this);
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


        int PERMISSION_CODE = 1000;
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
