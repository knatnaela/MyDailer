package com.example.toshiba.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 *
 */
public class ActivitySample extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sample_view);
        SampleHelper.with(this).init().loadAbout();
    }
}
