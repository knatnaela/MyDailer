package com.example.toshiba.myapplication.Database.Local;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.toshiba.myapplication.Database.ModelDB.Cart;
import com.example.toshiba.myapplication.Database.ModelDB.Favorites;


@Database(entities = {Cart.class, Favorites.class},version = 1)
public abstract class GEBETARoomDatabase extends RoomDatabase {

    public abstract CartDAO cartDAO();

    public abstract FavoritesDAO favoritesDAO();

    private static GEBETARoomDatabase instance;

    public static GEBETARoomDatabase getInstance(Context context)
    {
        if (instance == null)
            instance = Room.databaseBuilder(context,GEBETARoomDatabase.class,"GEBETA")
                    .allowMainThreadQueries()
                    .build();

        return instance;
    }


}
