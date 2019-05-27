package com.example.toshiba.myapplication.Database.Local;



import com.example.toshiba.myapplication.Database.DataSource.IFavoritesDataSource;
import com.example.toshiba.myapplication.Database.ModelDB.Favorites;

import java.util.List;

import io.reactivex.Flowable;

public class FavoritesDataSource implements IFavoritesDataSource {

    private FavoritesDAO favoritesDAO;
    private static FavoritesDataSource instance;

    public FavoritesDataSource(FavoritesDAO favoritesDAO) {
        this.favoritesDAO = favoritesDAO;
    }

    public static FavoritesDataSource getInstance(FavoritesDAO favoritesDAO){

        if (instance == null)
            instance = new FavoritesDataSource(favoritesDAO);

        return instance;
    }

    @Override
    public Flowable<List<Favorites>> getFavItems() {
        return favoritesDAO.getFavItems();
    }

    @Override
    public long isFavorite(long itemId) {
        return favoritesDAO.isFavorite(itemId);
    }

    @Override
    public void insertFav(Favorites... favorites) {

        favoritesDAO.insertFav(favorites);
    }

    @Override
    public void delete(Favorites favorites) {

        favoritesDAO.delete(favorites);

    }
}
