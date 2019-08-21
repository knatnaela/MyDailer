package com.example.toshiba.myapplication.Common;

import com.example.toshiba.myapplication.Database.DataSource.FavoritesRepository;
import com.example.toshiba.myapplication.Database.Local.GEBETARoomDatabase;
import com.example.toshiba.myapplication.Model.Packages;
import com.example.toshiba.myapplication.Model.ModelCalls;
import com.example.toshiba.myapplication.Model.ModelContacts;

import java.util.ArrayList;
import java.util.List;

import static com.example.toshiba.myapplication.Adapters.SendGiftAdapter.VIEWTYPE_GIFT;
import static com.example.toshiba.myapplication.Adapters.SendGiftAdapter.VIEWTYPE_GROUP;


public class Common {

public static String number = null;
public static String name = null;
public static ModelCalls list = null;
public static String imageView;


public static String check = "1";
public static boolean visible = false;


    //Database

    public static GEBETARoomDatabase gebetaRoomDatabase;
    public static FavoritesRepository favoritesRepository;

    public static ModelCalls currentCall;
//
    public static ModelContacts currentContacts;


    public static final List<String> numberAdded = new ArrayList<>();

    public static final List<String> durations = new ArrayList<>();
    public static String currentTab;
    public static String setNightModeState = "1";
    public static boolean giftActivity;


    public static ArrayList<Packages> addDuration(ArrayList<Packages>list){

        int i = 0;
        ArrayList<Packages> customList = new ArrayList<>();
        Packages firstPosition = new Packages();
        firstPosition.setDuration(list.get(0).getDuration());
        firstPosition.setViewType(VIEWTYPE_GROUP);
        durations.add(list.get(0).getDuration());
        customList.add(firstPosition);


        for (i = 0;i<list.size()-1;i++){
            Packages gift = new Packages();
            String duration = list.get(i).getDuration();
            String duration2 = list.get(i+1).getDuration();

            if (duration.equals(duration2)){
                list.get(i).setViewType(VIEWTYPE_GIFT);
                customList.add(list.get(i));
            }
            else
            {
                list.get(i).setViewType(VIEWTYPE_GIFT);
                customList.add(list.get(i));
                gift.setDuration(duration2);
                gift.setViewType(VIEWTYPE_GROUP);
                durations.add(duration2);
                customList.add(gift);
            }
        }

        list.get(i).setViewType(VIEWTYPE_GIFT);
        customList.add(list.get(i));
        return customList;
    }

}












