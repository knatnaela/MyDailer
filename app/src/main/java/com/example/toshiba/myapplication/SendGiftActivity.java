package com.example.toshiba.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import com.google.android.material.tabs.TabLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.toshiba.myapplication.Adapters.ViewPagerAdapter;
import com.example.toshiba.myapplication.Common.Common;
import com.example.toshiba.myapplication.fragments.FragmentInternetPackage;
import com.example.toshiba.myapplication.fragments.FragmentSMSPackage;
import com.example.toshiba.myapplication.fragments.FragmentVoicePackage;


import java.util.List;
import java.util.Locale;


public class SendGiftActivity extends AppCompatActivity {

    SharedPref sharedPref;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ImageView imgBanner;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        setContentView(R.layout.activity_send_gift);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        imgBanner=  findViewById(R.id.imgBanner);
        load();
        setImage();
    }

    private void load() {

        RequestOptions requestOptions = new RequestOptions()
                .error(R.drawable.ic_launcher_background);
        Drawable iconResource = null;

        Common.currentTab = "voice";

        iconResource = ContextCompat.getDrawable(this,R.drawable.calling);
        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(iconResource)
                .into(imgBanner);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new FragmentVoicePackage(),"Voice Package");
        adapter.addFragment(new FragmentInternetPackage(),"Internet Package" );
        adapter.addFragment(new FragmentSMSPackage(),"SMS Package");
        viewPager.setAdapter(adapter);


        viewPager.setOffscreenPageLimit(4);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                int position = 0;
                RequestOptions requestOptions = new RequestOptions()
                        .error(R.drawable.ic_launcher_background);
                Drawable iconResource = null;

                iconResource = ContextCompat.getDrawable(SendGiftActivity.this,R.drawable.calling);
                Glide.with(SendGiftActivity.this)
                        .setDefaultRequestOptions(requestOptions)
                        .load(iconResource)
                        .into(imgBanner);

                position = tab.getPosition();

                if (position == 0)
                {

                    iconResource = ContextCompat.getDrawable(SendGiftActivity.this,R.drawable.calling);
                    Glide.with(SendGiftActivity.this)
                            .setDefaultRequestOptions(requestOptions)
                            .load(iconResource)
                            .into(imgBanner);
                    Common.currentTab = "voice";
                }
                else  if (position == 1)
                {

                    iconResource = ContextCompat.getDrawable(SendGiftActivity.this,R.drawable.internetimg);
                    Glide.with(SendGiftActivity.this)
                            .setDefaultRequestOptions(requestOptions)
                            .load(iconResource)
                            .into(imgBanner);

                    Common.currentTab = "internet";
                }
                else  if (position == 2)
                {

                    iconResource = ContextCompat.getDrawable(SendGiftActivity.this,R.drawable.sms);
                    Glide.with(SendGiftActivity.this)
                            .setDefaultRequestOptions(requestOptions)
                            .load(iconResource)
                            .into(imgBanner);

                  Common.currentTab = "sms";
               }

            }
          @Override
          public void onTabUnselected(TabLayout.Tab tab) {

          }

          @Override
         public void onTabReselected(TabLayout.Tab tab) {
            }
        });
   }

   private void showPremiumDialog(String imageView, String name, final String number) {

       AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View itemView = LayoutInflater.from(this)
                .inflate(R.layout.premium,null );


        ImageView contact_Image = itemView.findViewById(R.id.img_contact);
        TextView contact_name = itemView.findViewById(R.id.contact_name);
        TextView contact_number = itemView.findViewById(R.id.contact_number);


        if (name !=null) {
            contact_name.setText(name);
        }
        else {
            contact_name.setText(R.string.unnamed);
        }
        contact_number.setText(number);
        if (imageView !=null) {
            Glide.with(this)
                    .load(imageView)
                    .into(contact_Image);
        }
        else {
            contact_Image.setImageResource(R.drawable.person);
        }
        Button Premium = itemView.findViewById(R.id.Premium);

        Premium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*4*1*1*1"+"*"+number+"*1")+Uri.encode("#")));
                {
                    permission();
                    startActivity(intent);

                }
            }
        });

        builder.setView(itemView);
        builder.show();
    }



    private void permission() {

        ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
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

    private Fragment getVisibleFragment() {
        FragmentManager fragmentManager = SendGiftActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }

    public void setImage(){
        RequestOptions requestOptions = new RequestOptions()
                .error(R.drawable.ic_launcher_background);
        Drawable iconResource = null;


        if (getVisibleFragment() instanceof  FragmentInternetPackage)
        {
            iconResource = ContextCompat.getDrawable(this,R.drawable.internetimg);
            Glide.with(this)
                    .setDefaultRequestOptions(requestOptions)
                    .load(iconResource)
                    .into(imgBanner);

        }
        else  if (getVisibleFragment() instanceof  FragmentVoicePackage)
        {
            iconResource = ContextCompat.getDrawable(this,R.drawable.calling);
            Glide.with(this)
                    .setDefaultRequestOptions(requestOptions)
                    .load(iconResource)
                    .into(imgBanner);

        }


    }
}
