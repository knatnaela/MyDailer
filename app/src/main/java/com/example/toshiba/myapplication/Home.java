package com.example.toshiba.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDelegate;
import android.telecom.TelecomManager;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.os.Handler;

import com.example.toshiba.myapplication.Adapters.ViewPagerAdapter;

import com.example.toshiba.myapplication.Common.Common;


import com.example.toshiba.myapplication.Database.DataSource.CartRepository;
import com.example.toshiba.myapplication.Database.DataSource.FavoritesRepository;
import com.example.toshiba.myapplication.Database.Local.CartDataSource;
import com.example.toshiba.myapplication.Database.Local.FavoritesDataSource;
import com.example.toshiba.myapplication.Database.Local.GEBETARoomDatabase;
import com.example.toshiba.myapplication.fragments.FragmentCalls;
import com.example.toshiba.myapplication.fragments.FragmentContacts;
import com.example.toshiba.myapplication.fragments.FragmentFavorites;
import com.rengwuxian.materialedittext.MaterialEditText;

import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import io.reactivex.disposables.CompositeDisposable;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.example.toshiba.myapplication.R.drawable.*;
import static com.example.toshiba.myapplication.R.drawable.img1;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private final int[] ICONS = {ic_phone_black_24dp,ic_star_golden_24dp, ic_group_black_24dp};

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private final int PERMISSION_CODE = 1000;
    LinearLayout layout;
    TextView view;
    private int[] img = {img1,img2,img3,img4,img5};
    int k;
    SharedPref sharedPref;

    private Switch mSwitch;

    MenuItem item;

    CompositeDisposable compositeDisposable;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
               if ( requestCode  == PERMISSION_CODE)
                {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    {
                        load();
                    }
                    else {
                        Toast.makeText(this, "Until you grant permission, we cannot display your calllog and contacts", Toast.LENGTH_SHORT).show();
                    }
                }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
         loadLocale();
        sharedPref = new SharedPref(this);
        if (sharedPref.loadNightModelState())
        {
            setTheme(R.style.darkTheme);
        }
        else
            setTheme(R.style.Light);
        setContentView(R.layout.activity_home);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.menu);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        compositeDisposable = new CompositeDisposable();

     //   checkDefaultDailer();


        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        layout = findViewById(R.id.nav_header);

      //  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
/*        fab.setOnClickListener(new View.OnClickListener() {
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


        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int i = 0;
            @Override
            public void run() {

                layout.setBackgroundResource(img[i]);
                i++;
                if (i > img.length  - 1)
                {
                    i = 0;
                }
                handler.postDelayed(this,50000 );
            }
        };
        handler.postDelayed(runnable,2000 );

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


        Common.gebetaRoomDatabase = GEBETARoomDatabase.getInstance(this);
        Common.cartRepository = CartRepository.getInstance(CartDataSource.getInstance(Common.gebetaRoomDatabase.cartDAO()));
        Common.favoritesRepository = FavoritesRepository.getInstance(FavoritesDataSource.getInstance(Common.gebetaRoomDatabase.favoritesDAO()));


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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search_menu) {
            startActivity(new Intent(Home.this, SearchActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
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
        } else if (id == R.id.nav_settings) {

            startActivity(new Intent(Home.this,Settings.class));
        }
        else if (id == R.id.nav_internet) {

            showInternetDialog();

        } else if (id == R.id.nav_SMS) {

            showSMSDialog();

        }else if (id == R.id.nav_voice) {

            showVoiceDialog();
        } else if (id == R.id.about) {

            startActivity(new Intent(Home.this,ActivitySample.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

    private void showVoiceDialog() {

        final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        View itemView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_layout_voice_package, null);


        Button Night_voice__Birr3 = itemView.findViewById(R.id.BirrNightVoice3);
        Button Night_voice__Birr4 = itemView.findViewById(R.id.BirrNightVoice4);
        Button Night_voice__Birr6 = itemView.findViewById(R.id.BirrNightVoice6);
        Button Night_voice__Birr9 = itemView.findViewById(R.id.BirrNightVoice9);


        Night_voice__Birr3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_voice_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                 alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialogInterface, int i) {

                         Intent intent = new Intent(Intent.ACTION_CALL);
                         intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*1*1*1")+Uri.encode("#")));
                         {
                             permission();
                             startActivity(intent);

                         }
                     }
                 }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialogInterface, int i) {
                         dialogInterface.dismiss();
                     }
                 }).show();

            }
        });

        Night_voice__Birr4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_voice_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*1*2*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }
                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });

        Night_voice__Birr6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_voice_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*1*3*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }
                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });

        Night_voice__Birr9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_voice_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*1*4*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }
                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });

        Button Daily_voice__Birr3 = itemView.findViewById(R.id.BirrDailyVoice3);
        Button Daily_voice__Birr5 = itemView.findViewById(R.id.BirrDailyVoice5);
        Button Daily_voice__Birr10 = itemView.findViewById(R.id.BirrDailyVoice10);



        Daily_voice__Birr3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_voice_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*2*1*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }
                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

            }
        });

        Daily_voice__Birr5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_voice_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*2*2*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }
                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });

        Daily_voice__Birr10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_voice_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*2*3*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }

                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });


        Button Weekly_voice__Birr15 = itemView.findViewById(R.id.BirrWeeklyVoice15);
        Button Weekly_voice__Birr20 = itemView.findViewById(R.id.BirrWeeklyVoice20);

        Weekly_voice__Birr15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_voice_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*3*1*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }
                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });


        Weekly_voice__Birr20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_voice_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*3*2*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }

                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });


        //--------Monthly Button----------

        Button Monthly_voice__Birr60 = itemView.findViewById(R.id.BirrMonthlyVoice60);
        Button Monthly_voice__Birr100 = itemView.findViewById(R.id.BirrMonthlyVoice100);
        Button Monthly_voice__Birr140 = itemView.findViewById(R.id.BirrMonthlyVoice140);
        Button Monthly_voice__Birr150 = itemView.findViewById(R.id.BirrMonthlyVoice150);
        Button Monthly_voice__Birr200 = itemView.findViewById(R.id.BirrMonthlyVoice200);
        Button Monthly_voice__Birr250 = itemView.findViewById(R.id.BirrMonthlyVoice250);
        Button Monthly_voice__Birr270 = itemView.findViewById(R.id.BirrMonthlyVoice270);
        Button Monthly_voice__Birr300 = itemView.findViewById(R.id.BirrMonthlyVoice300);
        Button Monthly_voice__Birr350 = itemView.findViewById(R.id.BirrMonthlyVoice350);
        Button Monthly_voice__Birr400 = itemView.findViewById(R.id.BirrMonthlyVoice400);
        Button Monthly_voice__Birr450 = itemView.findViewById(R.id.BirrMonthlyVoice450);
        Button Monthly_voice__Birr500 = itemView.findViewById(R.id.BirrMonthlyVoice500);
        Button Monthly_voice__Birr540 = itemView.findViewById(R.id.BirrMonthlyVoice540);
        Button Monthly_voice__Birr600 = itemView.findViewById(R.id.BirrMonthlyVoice600);
        Button Monthly_voice__Birr1350 = itemView.findViewById(R.id.BirrMonthlyVoice1350);


        //-----Monthly button Action listener

        Monthly_voice__Birr60.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_voice_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*4*1*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }
                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

            }
        });
        Monthly_voice__Birr100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_voice_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*4*2*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }
                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });
        Monthly_voice__Birr140.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_voice_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*4*3*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }
                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });
        Monthly_voice__Birr150.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_voice_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*4*4*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }
                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });
        Monthly_voice__Birr200.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_voice_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*4")+Uri.encode("#")+Uri.parse("*5*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }

                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });
        Monthly_voice__Birr250.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_voice_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*4")+ Uri.encode("#")+Uri.parse("*6*1")+Uri.encode("#")));

                        {
                            permission();
                            startActivity(intent);

                        }

                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });
        Monthly_voice__Birr270.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_voice_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*4")+Uri.encode("#")+Uri.parse("*7*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }

                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });
        Monthly_voice__Birr300.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_voice_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*4")+Uri.encode("#")+ Uri.parse("*8*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }

                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });
        Monthly_voice__Birr350.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_voice_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*4")+Uri.encode("#")+Uri.encode("#")+Uri.parse("*9*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }
                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });
        Monthly_voice__Birr400.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_voice_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*4")+Uri.encode("#")+Uri.encode("#")+Uri.parse("*10*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }
                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });
        Monthly_voice__Birr450.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_voice_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*4")+Uri.encode("#")+Uri.encode("#")+Uri.parse("*11*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }
                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });
        Monthly_voice__Birr500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_voice_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*4")+Uri.encode("#")+Uri.encode("#")+Uri.parse("*12*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }
                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });
        Monthly_voice__Birr540.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_voice_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*4")+Uri.encode("#")
                                +Uri.encode("#")+Uri.encode("#")+Uri.parse("*13*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }
                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });
        Monthly_voice__Birr600.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_voice_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*4")+Uri.encode("#")
                                +Uri.encode("#")+Uri.encode("#")+Uri.parse("*14*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }
                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });
        Monthly_voice__Birr1350.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_voice_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*1*4")+Uri.encode("#")+Uri.encode("#")
                                +Uri.encode("#")+Uri.parse("*15*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }
                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });

        builder.setView(itemView);
        builder.show();


    }

    private void permission() {
        ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
    }

    private void showSMSDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View itemView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_layout_sms_package,null);


        Button Daily_SMS_Birr2 = itemView.findViewById(R.id.BirrSMS2);
        Button Daily_SMS_Birr3 = itemView.findViewById(R.id.BirrSMS3);
        Button Daily_SMS_Birr5 = itemView.findViewById(R.id.BirrSMS5);

        Daily_SMS_Birr2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_sms_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*3*1*1*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }
                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();


            }
        });

        Daily_SMS_Birr3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_sms_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*3*1*2*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }

                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

            }
        });

        Daily_SMS_Birr5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_sms_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*3*1*3*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }

                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });

        Button Weekly_SMS_Birr10 = itemView.findViewById(R.id.BirrSMS10);
        Button Weekly_SMS_Birr15 = itemView.findViewById(R.id.BirrSMS15);

        Weekly_SMS_Birr10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_sms_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*3*2*1*1")+Uri.encode("#")));

                        {

                            permission();
                            startActivity(intent);
                        }

                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

            }
        });

        Weekly_SMS_Birr15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_sms_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*3*2*2*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }

                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

            }
        });

        Button Monthly_SMS_Birr30 = itemView.findViewById(R.id.BirrSMS30);
        Button Monthly_SMS_Birr50 = itemView.findViewById(R.id.BirrSMS50);

        Monthly_SMS_Birr30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_sms_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*3*3*1*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }

                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();


            }
        });

        Monthly_SMS_Birr50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_sms_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*3*3*2*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }
                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

            }
        });

        builder.setView(itemView);
        builder.show();
    }

    private void showInternetDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View itemView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_layout_internet_package, null);



        Button Night_internet__Birr3 = itemView.findViewById(R.id.BirrNight3);
        Button Night_internet__Birr5 = itemView.findViewById(R.id.BirrNight5);
        Button Night_internet__Birr7 = itemView.findViewById(R.id.BirrNight7);


        Night_internet__Birr3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_internet_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*1*2*4*1*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }
                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

            }
        });

        Night_internet__Birr5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_sms_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*1*2*4*2*1") + Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }
                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

            }
        });

        Night_internet__Birr7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_sms_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*1*2*4*3*1") + Uri.encode("#")));
                        {

                            permission();
                            startActivity(intent);
                        }
                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

            }
        });


        Button Daily_internet__Birr3 = itemView.findViewById(R.id.BirrDaily3);
        Button Daily_internet__Birr5 = itemView.findViewById(R.id.BirrDaily5);
        Button Daily_internet__Birr10 = itemView.findViewById(R.id.BirrDaily10);
        Button Daily_internet__Birr15 = itemView.findViewById(R.id.BirrDaily15);
        Button Daily_internet__Birr35 = itemView.findViewById(R.id.BirrDaily35);


        Daily_internet__Birr3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_internet_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*2*1*1*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }
                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

            }
        });

        Daily_internet__Birr5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_internet_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*2*1*2*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }
                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

            }
        });

        Daily_internet__Birr10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_internet_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*2*1*3*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }
                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

            }
        });

        Daily_internet__Birr15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_internet_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*2*1*4*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }
                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();


            }
        });


        Daily_internet__Birr35.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_internet_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*2*1*5*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }

                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });

        Button Weekly_internet__Birr50 = itemView.findViewById(R.id.BirrWeekly50);
        Button Weekly_internet__Birr60 = itemView.findViewById(R.id.BirrWeekly60);
        Button Weekly_internet__Birr80 = itemView.findViewById(R.id.BirrWeekly80);

        Weekly_internet__Birr50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_internet_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*2*2*1*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }
                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();


            }
        });

        Weekly_internet__Birr60.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_internet_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*2*2*2*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }
                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

            }
        });

        Weekly_internet__Birr80.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_internet_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*2*2*3*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }
                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

            }
        });


        Button Monthly_internet__Birr55 = itemView.findViewById(R.id.BirrMonthly55);
        Button Monthly_internet__Birr100 = itemView.findViewById(R.id.BirrMonthly100);
        Button Monthly_internet__Birr190 = itemView.findViewById(R.id.BirrMonthly190);
        Button Monthly_internet__Birr350 = itemView.findViewById(R.id.BirrMonthly350);
        Button Monthly_internet__Birr600 = itemView.findViewById(R.id.BirrMonthly600);
        Button Monthly_internet__Birr700 = itemView.findViewById(R.id.BirrMonthly700);
        Button Monthly_internet__Birr1300 = itemView.findViewById(R.id.BirrMonthly1300);
        Button Monthly_internet__Birr1800 = itemView.findViewById(R.id.BirrMonthly1800);

        Monthly_internet__Birr55.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_internet_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*2*3*1*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }
                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

            }
        });

        Monthly_internet__Birr100.setOnClickListener(new View.OnClickListener() {

             @Override
            public void onClick(View view) {


                 AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                 alertDialog.setTitle(R.string.self_internet_package);
                 alertDialog.setMessage(R.string.do_u_want_buy_package);

                 alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialogInterface, int i) {

                         Intent intent = new Intent(Intent.ACTION_CALL);
                         intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*2*3*2*1")+Uri.encode("#")));
                         {
                             permission();
                             startActivity(intent);

                         }

                     }
                 }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialogInterface, int i) {
                         dialogInterface.dismiss();
                     }
                 }).show();

             }
        });

        Monthly_internet__Birr190.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_internet_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*2*3*3*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }
                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

            }
        });

        Monthly_internet__Birr350.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_internet_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*2*3*4*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }
                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

            }
        });

        Monthly_internet__Birr600.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_internet_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*2*3")+Uri.encode("#")+Uri.parse("*5*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }
                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

            }
        });

        Monthly_internet__Birr700.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_internet_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*2*3")+Uri.encode("#")+Uri.parse("*6*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }

                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

            }
        });

        Monthly_internet__Birr1300.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_internet_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*2*3")+Uri.encode("#")+Uri.parse("*7*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);

                        }

                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

            }
        });

        Monthly_internet__Birr1800.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_internet_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*1*2*3")+Uri.encode("#")+Uri.parse("*8*1")+Uri.encode("#")));
                        {
                            permission();
                            startActivity(intent);
                        }

                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();


            }
        });

        Button Weekend_internet__Birr35 = itemView.findViewById(R.id.BirrWeekend35);
        Button Weekend_internet__Birr60 = itemView.findViewById(R.id.BirrWeekend60);
        Button Weekend_internet__Birr110 = itemView.findViewById(R.id.BirrWeekend110);

        Weekend_internet__Birr35.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_internet_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*1*2*5*1*1") + Uri.encode("#")));
                        {

                            startActivity(intent);
                        }
                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

            }
        });

        Weekend_internet__Birr60.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_internet_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*1*2*5*2*1") + Uri.encode("#")));
                        {


                            startActivity(intent);
                        }

                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

            }
        });

        Weekend_internet__Birr110.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
                alertDialog.setTitle(R.string.self_internet_package);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*1*2*5*3*1") + Uri.encode("#")));
                        {


                            startActivity(intent);
                        }

                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

            }
        });


        builder.setView(itemView);
        builder.show();

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

        for (int i = 0; i < tabLayout.getTabCount(); i++) {


            TabLayout.Tab tab = tabLayout.getTabAt(i);

            tab.setIcon(ICONS[i]);
        }

    }


}
