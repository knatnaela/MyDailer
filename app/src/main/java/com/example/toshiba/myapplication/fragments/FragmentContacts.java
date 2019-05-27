package com.example.toshiba.myapplication.fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.toshiba.myapplication.Adapters.ContactsRecyclerViewAdapter;
import com.example.toshiba.myapplication.Helper.RecyclerViewWithEmptySupport;
import com.example.toshiba.myapplication.Model.ModelContacts;
import com.example.toshiba.myapplication.R;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.rey.material.widget.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FragmentContacts extends Fragment {

    private View v,view;
    private RecyclerViewWithEmptySupport recyclerView;

    MaterialSearchBar searchBar;

    ContactsRecyclerViewAdapter adapter;
    RelativeLayout relativeLayout;

    TextView addContacts;



    public FragmentContacts() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_contacts, container, false);

        recyclerView = v.findViewById(R.id.recycler_contacts);
        relativeLayout = v.findViewById(R.id.rootLayout);

        addContacts = v.findViewById(R.id.txtAddContact);

        addContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ph = "";
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, ph);
                startActivity(intent);
            }
        });

        adapter = new ContactsRecyclerViewAdapter(getContext(), getContacts());

        RecyclerViewWithEmptySupport.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setEmptyView(relativeLayout.findViewById(R.id.rootLayout));
        recyclerView.setAdapter(adapter);

        return v;
    }

    private List<ModelContacts> getContacts(){

       List<ModelContacts> lists = new ArrayList<>();

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
             ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.READ_CONTACTS},1000);

        Cursor cursor = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
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
                    lists.add(new ModelContacts(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),
                            cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)),
                            cursor.getString(cursor.getColumnIndex( ContactsContract.RawContacts.CONTACT_ID)),
                            cursor.getString(photo)));



                } while (cursor.moveToNext());
            }
        }

        return lists;
    }

    @Override
    public void onResume() {
        super.onResume();

        adapter = new ContactsRecyclerViewAdapter(getContext(), getContacts());

        RecyclerViewWithEmptySupport.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setEmptyView(relativeLayout.findViewById(R.id.rootLayout));
        recyclerView.setAdapter(adapter);

    }
}
