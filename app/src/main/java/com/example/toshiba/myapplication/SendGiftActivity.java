package com.example.toshiba.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.toshiba.myapplication.Common.Common;
import com.example.toshiba.myapplication.Model.ModelContacts;
import com.example.toshiba.myapplication.ViewHolder.ContactViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

import br.com.bloder.magic.view.MagicButton;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SendGiftActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION = 1001;
    private static final int REEQUEST = 1222;
    MagicButton btn_call, btn_internet, btn_sms,btn_common;

    ContactViewHolder adapter;

    Context context;

    List<ModelContacts> mListContacts;

    android.app.Dialog mDialog,Dialog;
    SharedPref sharedPref;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

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
        setContentView(R.layout.activity_send_gift);

        btn_call = findViewById(R.id.magic_button_voice);

        btn_internet = findViewById(R.id.magic_button_internet);

        btn_sms = findViewById(R.id.magic_button_SMS);

        btn_common = findViewById(R.id.magic_button_common);



        btn_call.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showVoiceDialog(Common.imageView, Common.name, Common.number);
            }
        });

        btn_internet.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showInternetDialog(Common.imageView, Common.name, Common.number);
            }
        });

        btn_sms.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showSMSDialog(Common.imageView, Common.name, Common.number);
            }
        });

        btn_common.setMagicButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPremiumDialog(Common.imageView,Common.name,Common.number);
            }
        });


    }

    private void showPremiumDialog(String imageView, String name, final String number) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View itemView = LayoutInflater.from(this)
                .inflate(R.layout.premium,null );



        //View
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
            Picasso.with(this)
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

    private void showVoiceDialog(String imageView, String name, final String number) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View itemView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_layout_voice_package, null);

        
        //View
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
            Picasso.with(this)
                    .load(imageView)
                    .into(contact_Image);
        }
        else {
             contact_Image.setImageResource(R.drawable.person);
        }
        Button Night_voice__Birr3 = itemView.findViewById(R.id.BirrNightVoice3);
        Button Night_voice__Birr4 = itemView.findViewById(R.id.BirrNightVoice4);
        Button Night_voice__Birr6 = itemView.findViewById(R.id.BirrNightVoice6);
        Button Night_voice__Birr9 = itemView.findViewById(R.id.BirrNightVoice9);

        Night_voice__Birr3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*1*1*1"+ "*" + number + "*1")+Uri.encode("#")));
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*1*2*1"+ "*" + number + "*1")+Uri.encode("#")));
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*1*3*1"+ "*" + number + "*1")+Uri.encode("#")));
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*1*4*1"+ "*" + number + "*1")+Uri.encode("#")));
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*2*1*1"+ "*" + number + "*1")+Uri.encode("#")));
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


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*2*2*1"+ "*" + number + "*1")+Uri.encode("#")));
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


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*2*3*1"+ "*" + number + "*1")+Uri.encode("#")));
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*3*1*1"+ "*" + number + "*1")+Uri.encode("#")));
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


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*3*2*1"+ "*" + number + "*1")+Uri.encode("#")));
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*4*1*1"+ "*" + number + "*1")+Uri.encode("#")));
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*4*2*1"+ "*" + number + "*1")+Uri.encode("#")));
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*4*3*1"+ "*" + number + "*1")+Uri.encode("#")));
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*4*4*1"+ "*" + number + "*1")+Uri.encode("#")));
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*4")+Uri.encode("#")
                                +Uri.parse("*5*1"+ "*" + number + "*1")+Uri.encode("#")));
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


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*4")+Uri.encode("#")+Uri.parse("*6*1"+ "*" + number + "*1")+Uri.encode("#")));
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*4")+Uri.encode("#")
                                +Uri.parse("*7*1" + "*" + number + "*1")+Uri.encode("#")));
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*4")+Uri.encode("#")
                                +Uri.parse("*8*1"+ "*" + number + "*1")+Uri.encode("#")));
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


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*4")+Uri.encode("#")
                                +Uri.encode("#")+Uri.parse("*9*1"+ "*" + number + "*1")+Uri.encode("#")));
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*4")+Uri.encode("#")
                                +Uri.encode("#")+Uri.parse("*10*1"+ "*" + number + "*1")+Uri.encode("#")));
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*4")+Uri.encode("#")
                                +Uri.encode("#")+Uri.parse("*11*1"+ "*" + number + "*1")+Uri.encode("#")));
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*4")+Uri.encode("#")
                                +Uri.encode("#")+Uri.parse("*12*1"+ "*" + number + "*1")+Uri.encode("#")));
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



                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*4")+Uri.encode("#")
                                +Uri.encode("#")+Uri.encode("#")+ Uri.parse("*13*1"+ "*" + number + "*1")+Uri.encode("#")));
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


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*4")+Uri.encode("#")
                                +Uri.encode("#")+Uri.encode("#")+Uri.parse("*14*1"+ "*" + number + "*1")+Uri.encode("#")));
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



                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*1*4")+Uri.encode("#")
                                +Uri.encode("#")+Uri.encode("#")+Uri.parse("*15*1"+ "*" + number + "*1")+Uri.encode("#")));
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


    private void showInternetDialog(String imageView, String name, final String number) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View itemView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_layout_internet_package, null);


        //View
        ImageView contact_Image = itemView.findViewById(R.id.img_contact);
        TextView contact_name = itemView.findViewById(R.id.contact_name);
        TextView contact_number = itemView.findViewById(R.id.contact_number);
        if (name !=null)
        {
            contact_name.setText(name);
        }
        else
        {
            contact_name.setText(R.string.unnamed);
        }
        contact_number.setText(number);

        if (imageView !=null)
        {
            Picasso.with(this)
                    .load(imageView)
                    .into(contact_Image);
        }
        else
        {
            contact_Image.setImageResource(R.drawable.person);
        }
        Button Night_internet__Birr3 = itemView.findViewById(R.id.BirrNight3);
        Button Night_internet__Birr5 = itemView.findViewById(R.id.BirrNight5);
        Button Night_internet__Birr7 = itemView.findViewById(R.id.BirrNight7);

        Night_internet__Birr3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*2*2*4*1*1" + "*" + number + "*1") + Uri.encode("#")));
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*2*2*4*2*1" + "*" + number + "*1") + Uri.encode("#")));
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" + "*999*1*2*2*4*3*1" + "*" + number + "*1") + Uri.encode("#")));
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


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*2*1*1*1"+"*"+number+"*1")+Uri.encode("#")));
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


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*2*1*2*1"+"*"+number+"*1")+Uri.encode("#")));
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*2*1*3*1"+"*"+number+"*1")+Uri.encode("#")));
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*2*1*4*1"+"*"+number+"*1")+Uri.encode("#")));
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*2*1*5*1"+"*"+number+"*1")+Uri.encode("#")));
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*2*2*1*1"+"*"+number+"*1")+Uri.encode("#")));
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*2*2*2*1"+"*"+number+"*1")+Uri.encode("#")));
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*2*2*3*1"+"*"+number+"*1")+Uri.encode("#")));
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*2*3*1*1"+"*"+number+"*1")+Uri.encode("#")));
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


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*2*3*2*1"+"*"+number+"*1")+Uri.encode("#")));
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


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*2*3*3*1"+"*"+number+"*1")+Uri.encode("#")));
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


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*2*3*4*1"+"*"+number+"*1")+Uri.encode("#")));
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*2*3")+Uri.encode("#")+Uri.parse("*5*1"+"*"+number+"*1")+Uri.encode("#")));
                        {
                            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){

                            }
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*2*3")+Uri.encode("#")+Uri.parse("*6*1"+"*"+number+"*1")+Uri.encode("#")));
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*2*3")+Uri.encode("#")+Uri.parse("*7*1"+"*"+number+"*1")+Uri.encode("#")));
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*2*3")+Uri.encode("#")+Uri.parse("*8*1"+"*"+number+"*1")+Uri.encode("#")));
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

    private void showSMSDialog(String ImageView, String name, final String number) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View itemView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_layout_sms_package,null);

        //View
        ImageView contact_Image = itemView.findViewById(R.id.img_contact);
        TextView contact_name = itemView.findViewById(R.id.contact_name);
        TextView contact_number = itemView.findViewById(R.id.contact_number);

        if (name !=null)
        {
            contact_name.setText(name);
        }
        else
        {
            contact_name.setText(R.string.unnamed);
        }
        contact_number.setText(number);

        if (ImageView !=null)
        {
            Picasso.with(this)
                    .load(ImageView)
                    .into(contact_Image);
        }
        else
        {
            contact_Image.setImageResource(R.drawable.person);
        }
        Button Daily_SMS_Birr2 = itemView.findViewById(R.id.BirrSMS2);
        Button Daily_SMS_Birr3 = itemView.findViewById(R.id.BirrSMS3);
        Button Daily_SMS_Birr5 = itemView.findViewById(R.id.BirrSMS5);

        Daily_SMS_Birr2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*3*1*1*1"+"*"+number+"*1")+Uri.encode("#")));
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*3*1*2*1"+"*"+number+"*1")+Uri.encode("#")));
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*3*1*3*1"+"*"+number+"*1")+Uri.encode("#")));
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*3*2*1*1"+"*"+number+"*1")+Uri.encode("#")));
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*3*2*2*1"+"*"+number+"*1")+Uri.encode("#")));
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*3*3*1*1"+"*"+number+"*1")+Uri.encode("#")));
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SendGiftActivity.this);
                alertDialog.setTitle(R.string.send_gift);
                alertDialog.setMessage(R.string.do_u_want_buy_package);

                alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse(Uri.parse("tel:" +"*999*1*2*3*3*2*1"+"*"+number+"*1")+Uri.encode("#")));
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
