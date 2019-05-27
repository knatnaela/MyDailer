package com.example.toshiba.myapplication.Database.DataSource;



import com.example.toshiba.myapplication.Database.ModelDB.Favorites;

import java.util.List;

import io.reactivex.Flowable;

public class FavoritesRepository implements IFavoritesDataSource {

    private IFavoritesDataSource favoritesDataSource;

    public FavoritesRepository(IFavoritesDataSource favoritesDataSource) {
        this.favoritesDataSource = favoritesDataSource;
    }

    private static FavoritesRepository instance;
    public static FavoritesRepository getInstance(IFavoritesDataSource  favoritesDataSource)
    {
        if (instance == null)
            instance = new FavoritesRepository(favoritesDataSource);
        return instance;
    }



    @Override
    public Flowable<List<Favorites>> getFavItems() {
        return favoritesDataSource.getFavItems();
    }

    @Override
    public long isFavorite(long itemId) {
        return favoritesDataSource.isFavorite(itemId);
    }

    @Override
    public void insertFav(Favorites... favorites) {

        favoritesDataSource.insertFav(favorites);
    }

    @Override
    public void delete(Favorites favorites) {

        favoritesDataSource.delete(favorites);
    }
}
