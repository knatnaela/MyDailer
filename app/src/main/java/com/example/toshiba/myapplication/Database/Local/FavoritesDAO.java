package com.example.toshiba.myapplication.Database.Local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.toshiba.myapplication.Database.ModelDB.Favorites;


import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface FavoritesDAO {

    @Query("SELECT * FROM Favorite")
    Flowable<List<Favorites>> getFavItems();

    @Query("SELECT EXISTS (SELECT 1 FROM Favorite WHERE id=:itemId)")
    long isFavorite(long itemId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFav(Favorites... favorites);

    @Delete
    void delete(Favorites favorites);
}
