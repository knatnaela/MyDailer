package com.example.toshiba.myapplication.fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.toshiba.myapplication.Adapters.CallsRecyclerViewAdapter;

import com.example.toshiba.myapplication.DialerPad;
import com.example.toshiba.myapplication.Helper.RecyclerViewWithEmptySupport;
import com.example.toshiba.myapplication.Model.ModelCalls;
import com.example.toshiba.myapplication.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentCalls extends Fragment {


    private RelativeLayout relativeLayout;
    private RecyclerViewWithEmptySupport recyclerView;
    private CallsRecyclerViewAdapter adapter;

    public FragmentCalls() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_calls, container, false);

        recyclerView = v.findViewById(R.id.recycler_calls);
        relativeLayout = v.findViewById(R.id.rootLayout);

        initLayoutReferences();

        TextView makeCall = v.findViewById(R.id.txtMakeCall);
        FloatingActionButton fab = v.findViewById(R.id.fab);

        makeCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialerPad();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialerPad();
            }
        });

       adapter = new CallsRecyclerViewAdapter(getContext(), getCalls());

        RecyclerViewWithEmptySupport.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setEmptyView(relativeLayout.findViewById(R.id.rootLayout));

        recyclerView.setAdapter(adapter);


        return v;
    }

    private void initLayoutReferences() {


        recyclerView.setHasFixedSize(true);

        int columns;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            columns = 2;
        } else {
            columns = getResources().getInteger(R.integer.no_of_columns);
        }
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), columns);

        recyclerView.setLayoutManager(layoutManager);
        adapter = new CallsRecyclerViewAdapter(getContext() , getCalls());
        recyclerView.setAdapter(adapter);
    }

    private void dialerPad() {

        startActivity(new Intent(getActivity(), DialerPad.class));
    }

    public List<ModelCalls> getCalls() {


        List<ModelCalls> parentObjects = new ArrayList<>();


        int PERMISSION_CODE = 1000;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED)

                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CALL_LOG}, PERMISSION_CODE);

            Cursor cursor = getContext().getContentResolver().query(CallLog.Calls.CONTENT_URI, null,
                    null, null, CallLog.Calls.DATE + " DESC");

        if (cursor !=null) {
            while (cursor.moveToNext()) {
                int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
                int name = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
                int date = cursor.getColumnIndex(CallLog.Calls.DATE);
                int type = cursor.getColumnIndex(CallLog.Calls.TYPE);

                int id = cursor.getColumnIndex(CallLog.Calls.NUMBER);

                int photo = 0;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    photo = cursor.getColumnIndex(CallLog.Calls.CACHED_PHOTO_URI);
                }
                Date date1 = new Date(Long.valueOf(cursor.getString(date)));
                String month_date, week_day, month;
                month_date = (String) DateFormat.format("dd", date1);
                week_day = (String) DateFormat.format("EEEE", date1);
                month = (String) DateFormat.format("MMMM", date1);
                parentObjects.add(new ModelCalls(cursor.getString(name), cursor.getString(number), week_day + " " + month_date
                        + " " + month, cursor.getString(photo),cursor.getString(id), cursor.getString(type)));
            }

            cursor.close();
        }

        return parentObjects;

    }

    @Override
    public void onResume() {
        super.onResume();

        adapter = new CallsRecyclerViewAdapter(getContext(), getCalls());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setEmptyView(relativeLayout.findViewById(R.id.rootLayout));
        recyclerView.setAdapter(adapter);

    }
}
