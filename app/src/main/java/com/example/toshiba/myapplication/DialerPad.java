package com.example.toshiba.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class DialerPad extends AppCompatActivity {

    ImageView btnDelete,img_create_contact,img_add_contact,img_sms;
    LinearLayout btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn0,btnStar,btnHash;
    TextView btn_create_contact,btn_add_contact,btn_sms;

  LinearLayout create_contact_layout,add_contact_layout,send_sms_layout;
    SharedPref sharedPref;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
         loadLocale(); sharedPref = new SharedPref(this);
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
        setContentView(R.layout.activity_dialer_pad);


         final AppCompatEditText editText = findViewById(R.id.edtText);
         btnDelete = findViewById(R.id.btn_delete);
         btn1 = findViewById(R.id.button1);
         btn2 = findViewById(R.id.button2);
         btn3 = findViewById(R.id.button3);
         btn4 = findViewById(R.id.button4);
         btn5 = findViewById(R.id.button5);
         btn6 = findViewById(R.id.button6);
         btn7 = findViewById(R.id.button7);
         btn8 = findViewById(R.id.button8);
         btn9 = findViewById(R.id.button9);
         btn0 = findViewById(R.id.button0);
         btnStar = findViewById(R.id.buttonStar);
         btnHash = findViewById(R.id.buttonHash);
         img_create_contact = findViewById(R.id.img_contact);
        img_add_contact = findViewById(R.id.img_add_contact);
         btn_create_contact = findViewById(R.id.txt_create_contact);
         btn_add_contact = findViewById(R.id.txt_add_contact);
         btn_sms = findViewById(R.id.text_sms);
         img_sms = findViewById(R.id.img_sms);

         create_contact_layout = findViewById(R.id.create_contact_layout);
         add_contact_layout = findViewById(R.id.add_contact_layout);
         send_sms_layout = findViewById(R.id.send_sms_layout);
        relativeLayout = findViewById(R.id.layout);


        checkHasText(editText);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            editText.setShowSoftInputOnFocus(false);
        }
        editText.setInputType(InputType.TYPE_NULL);
        btn_add_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addToContact(editText);
            }
        });

        btn_create_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createContact(editText);
            }
        });


        btn_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MessageBox(editText.getText());
            }
        });


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(editText);
            }
        });


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onButtonClick(editText, "1");
                checkHasText(editText);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onButtonClick(editText, "2");
                checkHasText(editText);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onButtonClick(editText, "3");
                checkHasText(editText);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onButtonClick(editText, "4");
                checkHasText(editText);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onButtonClick(editText, "5");
                checkHasText(editText);
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onButtonClick(editText, "6");
                checkHasText(editText);
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onButtonClick(editText, "7");
                checkHasText(editText);
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onButtonClick(editText, "8");
                checkHasText(editText);
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onButtonClick(editText, "9");
                checkHasText(editText);

            }
        });
        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onButtonClick(editText, "0");
                checkHasText(editText);
            }
        });
        btnStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onButtonClick(editText, "*");
                checkHasText(editText);
            }
        });
        btnHash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onButtonClick(editText, "#");
                checkHasText(editText);
            }
        });

        AppCompatImageView fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             call(editText);
            }
        });


    }

    private void checkHasText(AppCompatEditText editText) {
        boolean hasText;
        hasText = editText.getText().length() <= 2;

        if (!hasText){
            relativeLayout.setVisibility(View.VISIBLE);
        relativeLayout.setAlpha(0.0f);

        relativeLayout.animate()
                .alpha(1.0f)
                .setListener(null);
        }
        else{
            relativeLayout.animate()
                    .translationY(0)
                    .alpha(0.0f)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            relativeLayout.setVisibility(View.GONE);
                        }
                    });

        }

    }

    private void addToContact(AppCompatEditText editText) {

        String phoneNumber = editText.getText().toString();
        Intent intent = new Intent(Intent.ACTION_DEFAULT,ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent,1);
    }

    private void createContact(AppCompatEditText editText) {
        String ph = editText.getText().toString();
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, ph);
        startActivity(intent);
    }

    private void MessageBox(Editable text) {


        String phone = text.toString();

      Intent intent = new Intent(Intent.ACTION_SENDTO);
      intent.addCategory(Intent.CATEGORY_DEFAULT);
      intent.setType("vnd.android-dir/mms-sms");
      intent.setData(Uri.parse("sms:"+phone));
      startActivity(intent);

    }

    private void call(AppCompatEditText editText) {

        if (editText !=null){
            if (editText.getText().length() <= 2) {

                Toast.makeText(DialerPad.this, R.string.please_enter_valid_number, Toast.LENGTH_SHORT).show();

            } else if (editText.getText().length() >= 3){
                String hash = editText.getText().toString();
                if (hash.endsWith("#"))
                {
                    String hash1 = hash.replace("#","" );
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(Uri.parse("tel: " + hash1)+Uri.encode("#")));
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel: " + hash));
                    startActivity(intent);
                }
            }
        }
    }

    private void delete(AppCompatEditText editText) {

       if (editText.getText().length() > 0 ) {
           editText.setEnabled(true);
           String srt = editText.getText().toString();
           srt = srt.substring(0, srt.length() - 1);
           editText.setText(srt);
           checkHasText(editText);
       }
       else
       {
           editText.setEnabled(false);
           checkHasText(editText);
       }

    }
    private void onButtonClick(AppCompatEditText editText, String number) {
        editText.setEnabled(true);
        String cache = editText.getText().toString();
        editText.setText(cache + number);

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
