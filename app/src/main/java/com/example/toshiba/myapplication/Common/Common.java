package com.example.toshiba.myapplication.Common;
import com.example.toshiba.myapplication.Database.DataSource.CartRepository;
import com.example.toshiba.myapplication.Database.DataSource.FavoritesRepository;
import com.example.toshiba.myapplication.Database.Local.GEBETARoomDatabase;
import com.example.toshiba.myapplication.Model.ModelCalls;
import com.example.toshiba.myapplication.Model.ModelContacts;
import com.example.toshiba.myapplication.Model.ModelDelete;

import java.util.ArrayList;
import java.util.List;

public class Common {

    public static String number = null;
    public static String name = null;
    public static ModelCalls list = null;
    public static String imageView;


    public static String check = "1";
    public static boolean visible = false;


    //Database

    public static GEBETARoomDatabase gebetaRoomDatabase;
    public static CartRepository cartRepository;
    public static FavoritesRepository favoritesRepository;

    public static ModelCalls currentCall;

    public static ModelContacts currentContacts;



    public static ModelCalls contacts;

    public static List<ModelDelete> modelDeleteList = new ArrayList<>();

    public static List<String> numberAdded = new ArrayList<>();
}
