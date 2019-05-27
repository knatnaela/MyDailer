package com.example.toshiba.myapplication.Database.DataSource;


import com.example.toshiba.myapplication.Database.ModelDB.Favorites;


import java.util.List;

import io.reactivex.Flowable;

public interface IFavoritesDataSource {

    Flowable<List<Favorites>> getFavItems();

    long  isFavorite(long itemId);

    void insertFav(Favorites... favorites);

    void delete(Favorites favorites);
}
