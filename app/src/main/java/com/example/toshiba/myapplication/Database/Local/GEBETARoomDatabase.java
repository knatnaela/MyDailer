package com.example.toshiba.myapplication.Database.Local;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.toshiba.myapplication.Database.ModelDB.Favorites;


@Database(entities = {Favorites.class},version = 2,exportSchema = false)
public abstract class GEBETARoomDatabase extends RoomDatabase {


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
