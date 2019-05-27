package com.example.toshiba.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.telephony.TelephonyManager;

import java.lang.reflect.Method;
/*
public class IncomingCallReceiver extends BroadcastReceiver{



    @Override
    public void onReceive(Context context, Intent intent) {

        ITelephony telephony;
        try {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);

            if (state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING))
            {
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                try {
                    Method m = tm.getClass().getDeclaredMethod("getItelephony");
                    m.setAccessible(true);

                    telephony = (ITelephony)
                }
            }
        }
    }
}
*/