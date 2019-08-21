package com.example.toshiba.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import com.example.toshiba.myapplication.Database.Local.GEBETARoomDatabase;
import com.google.android.material.tabs.TabLayout;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.toshiba.myapplication.Adapters.ViewPagerAdapter;

import com.example.toshiba.myapplication.Common.Common;


import com.example.toshiba.myapplication.Database.Local.FavoritesDataSource;
import com.example.toshiba.myapplication.Helper.SelfPackageActivity;
import com.example.toshiba.myapplication.fragments.FragmentCalls;
import com.example.toshiba.myapplication.fragments.FragmentContacts;
import com.example.toshiba.myapplication.fragments.FragmentFavorites;
import com.rengwuxian.materialedittext.MaterialEditText;

import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import io.reactivex.disposables.CompositeDisposable;

import static com.example.toshiba.myapplication.R.drawable.*;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final int[] ICONS = {ic_phone_black_24dp,ic_star_golden_24dp, ic_group_black_24dp};

    private TabLayout tabLayout;
    private ViewPager viewPager;

  private final int PERMISSION_CODE = 1000;
     LinearLayout layout;
    TextView view;int k;
    SharedPref sharedPref;
    private Switch mSwitch;
    MenuItem item;

    CompositeDisposable compositeDisposable;

    FavoritesDataSource favoritesDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = new SharedPref(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
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
         loadLocale();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.menu);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        compositeDisposable = new CompositeDisposable();

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        layout = findViewById(R.id.nav_header);

       /* FloatingActionButton fab = findViewById(R.id.fab);
       fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialerPad();

            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.setDrawerSlideAnimationEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        layout = headerView.findViewById(R.id.nav_header);

        initDB();

        load();

    }

/*   private void checkDefaultDailer() {

        startActivity(new Intent(Home.this,Main.class));
    }*/


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initDB() {
        favoritesDataSource = new FavoritesDataSource(GEBETARoomDatabase.getInstance(this).favoritesDAO());
    }

    private void dialerPad() {

        startActivity(new Intent(Home.this, DialerPad.class));


    }

    boolean isBackButtonClicked = false;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (isBackButtonClicked) {
               finishAffinity();
            }
            this.isBackButtonClicked = true;
            Toast.makeText(this, R.string.back_pressed, Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.search_menu) {
            startActivity(new Intent(Home.this, SearchActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();

        if (id == R.id.check_balance) {

            checkBalance();

        } else if (id == R.id.recharge_phone) {

            rechargeDialog();

        } else if (id == R.id.nav_share) {

            share();

        } else if (id == R.id.nav_more) {

            if (!menu.findItem(R.id.nav_Pre).isVisible() &&
                    !menu.findItem(R.id._borrow_service).isVisible()) {
                menu.findItem(R.id.nav_Pre).setVisible(true);
                menu.findItem(R.id._borrow_service).setVisible(true);
                return true;
            }
            if (menu.findItem(R.id._borrow_service).isVisible() &&
                    menu.findItem(R.id.nav_Pre).isVisible()) {
                menu.findItem(R.id._borrow_service).setVisible(false);
                menu.findItem(R.id.nav_Pre).setVisible(false);
                return true;
            }

        }else if (id == R.id.nav_Pre) {

            showPremiumDialog();
        }else if (id == R.id._borrow_service) {

            showBorrowDialog();
        }/* else if (id == R.id.nav_language) {

            showLanguageDialog();
        }*/else if (id == R.id.nav_theme) {

            showThemeDialog();
        }
        else if (id == R.id.nav_internet) {

          //  showInternetDialog();
            Common.giftActivity = false;
            Common.currentTab = "internet";
            startActivity(new Intent(Home.this, SelfPackageActivity.class)
            .putExtra("fragmentName","internetPackage"));

        } else if (id == R.id.nav_SMS) {

           // showSMSDialog();
            Common.giftActivity = false;
            Common.currentTab = "sms";
            startActivity(new Intent(Home.this, SelfPackageActivity.class)
                    .putExtra("fragmentName","smsPackage"));

        }else if (id == R.id.nav_voice) {

            /*showVoiceDialog();*/
            Common.giftActivity = false;
            Common.currentTab = "voice";
            startActivity(new Intent(Home.this, SelfPackageActivity.class)
                    .putExtra("fragmentName","voicePackage"));
        } else if (id == R.id.about) {

            startActivity(new Intent(Home.this,ActivitySample.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showLanguageDialog() {

        AlertDialog.Builder languageLayout = new AlertDialog.Builder(this);

        View itemView = LayoutInflater.from(this)
                .inflate(R.layout.language_layout,null);

        RadioButton btnEnglish = itemView.findViewById(R.id.language_eng);
        RadioButton btnAmharic = itemView.findViewById(R.id.language_amh);

        btnAmharic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {

                    setLocale("am");
                    restartApp();
                }
            }
        });

        btnEnglish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    setLocale("en");
                    restartApp();
                }
            }
        });


        languageLayout.setView(itemView);
        languageLayout.show();

    }
    private void restartApp() {

        startActivity(new Intent(getApplicationContext(),Home.class));
        finish();
    }

    private void showThemeDialog() {

        AlertDialog.Builder theme = new AlertDialog.Builder(this);

        View itemView = LayoutInflater.from(this)
                .inflate(R.layout.layout_theme,null);

        RadioButton rdi_night_mode = itemView.findViewById(R.id.rdi_night);
        RadioButton rdi_royal_mode = itemView.findViewById(R.id.rdi_royal);
        RadioButton rdi_light_mode = itemView.findViewById(R.id.rdi_light);
        RadioButton rdi_darker_mode = itemView.findViewById(R.id.rdi_darker);

        rdi_night_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    sharedPref.setNightModeState(true);
                    sharedPref.setLightModeState(false);
                    sharedPref.setDarkModeState(false);
                    sharedPref.setRoyalModeState(false);
                    restartApp();
                }
            }
        });

        rdi_royal_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {
                    sharedPref.setNightModeState(false);
                    sharedPref.setLightModeState(false);
                    sharedPref.setDarkModeState(false);
                    sharedPref.setRoyalModeState(true);
                    restartApp();
                }
            }
        });

        rdi_darker_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {

                    sharedPref.setNightModeState(false);
                    sharedPref.setLightModeState(false);
                    sharedPref.setDarkModeState(true);
                    sharedPref.setRoyalModeState(false);
                    restartApp();
                }
            }
        });

        rdi_light_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                {

                    sharedPref.setNightModeState(false);
                    sharedPref.setLightModeState(true);
                    sharedPref.setDarkModeState(false);
                    sharedPref.setRoyalModeState(false);
                    restartApp();
                }
            }
        });

        theme.setView(itemView);
        theme.show();
    }

    private void showBorrowDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View itemView = LayoutInflater.from(this)
                .inflate(R.layout.borrow_layout,null);

        Button birr100 = itemView.findViewById(R.id.Birr100credit);
        Button birr50 = itemView.findViewById(R.id.Birr50credit);
        Button birr25 = itemView.findViewById(R.id.Birr25credit);
        Button birr15 = itemView.findViewById(R.id.Birr15credit);
        Button birr10 = itemView.findViewById(R.id.Birr10credit);
        Button birr5 = itemView.findViewById(R.id.Birr5credit);


        birr100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(Uri.parse("tel:" +"*810*1*1")+Uri.encode("#")));
                {
                    permission();
                    startActivity(intent);

                }
            }

        });

        birr50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(Uri.parse("tel:" +"*810*1*2")+Uri.encode("#")));
                {
                    permission();
                    startActivity(intent);

                }
            }

        });

        birr25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(Uri.parse("tel:" +"*810*1*3")+Uri.encode("#")));
                {
                    permission();
                    startActivity(intent);

                }
            }

        });

        birr15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(Uri.parse("tel:" +"*810*1*4")+Uri.encode("#")));
                {
                    permission();
                    startActivity(intent);

                }
            }

        });

        birr10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(Uri.parse("tel:" +"*810*1*5")+Uri.encode("#")));
                {
                    permission();
                    startActivity(intent);

                }
            }

        });


        birr5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(Uri.parse("tel:" +"*810*1*6")+Uri.encode("#")));
                {
                    permission();
                    startActivity(intent);

                }
            }

        });

        builder.setView(itemView);
        builder.show();


    }

    private void showPremiumDialog() {

       AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View itemView = LayoutInflater.from(this)
                .inflate(R.layout.premium,null);

        Button Premium = itemView.findViewById(R.id.Premium);

        Premium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*4*1*1*1")+Uri.encode("#")));
                {
                    permission();
                    startActivity(intent);

                }
            }
        });



        builder.setView(itemView);
        builder.show();
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

    private void share() {


        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String shareBody = "Your body here";
        String Sharebdy = "Your subject here";
        intent.putExtra(Intent.EXTRA_SUBJECT,Sharebdy);
        intent.putExtra(Intent.EXTRA_TEXT,shareBody);
        startActivity(Intent.createChooser(intent,"Share Via"));

    }


    private void permission() {
        ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
    }

    private void rechargeDialog() {

        final Dialog mDialog = new Dialog(this);


        mDialog.setContentView(R.layout.dialog_recharge);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



        final MaterialEditText cardNumber = mDialog.findViewById(R.id.cardNumber);
        Button btnSend = mDialog.findViewById(R.id.dialog_btn_send);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cardNumber.getText().toString().isEmpty())
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                    builder.setTitle("Recharge Phone");
                    builder.setMessage("card number can't be empty  !!");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
                }
                else if (cardNumber.length() >14)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                    builder.setTitle("Recharge Phone");
                    builder.setMessage("You have entered  invalid card number please check card number again !!");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.dismiss();
                        }
                    });

                    builder.show();
                }
                else {
                    recharge(cardNumber.getText().toString());
                    mDialog.dismiss();
                }


            }
        });

        mDialog.show();
    }

    private void recharge(String cardNumber) {

            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse(Uri.parse("tel:" +"*805" +"*"+cardNumber)+Uri.encode("#")));
            {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){

                }
                startActivity(intent);

        }

    }

    private void checkBalance() {

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(Uri.parse("tel:" + "*804") + Uri.encode("#")));
        {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            }
            startActivity(intent);

        }
    }

    private void load() {


        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new FragmentCalls(),"");
        adapter.addFragment(new FragmentFavorites(),"" );
        adapter.addFragment(new FragmentContacts(),"");
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(4);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {


            TabLayout.Tab tab = tabLayout.getTabAt(i);

            tab.setIcon(ICONS[i]);
        }

    }
}
