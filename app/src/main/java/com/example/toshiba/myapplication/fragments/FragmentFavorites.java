package com.example.toshiba.myapplication.fragments;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.toshiba.myapplication.Adapters.FavoritesAdapter;
import com.example.toshiba.myapplication.Common.Common;

import com.example.toshiba.myapplication.Database.ModelDB.Favorites;
import com.example.toshiba.myapplication.Helper.RecyclerViewWithEmptySupport;
import com.example.toshiba.myapplication.R;


import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FragmentFavorites extends Fragment{

    private RecyclerViewWithEmptySupport recyclerView;

     private final CompositeDisposable compositeDisposable = new CompositeDisposable();


    private RelativeLayout rootLayout;


    public FragmentFavorites() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_fav, container, false);

        View view = inflater.inflate(R.layout.activity_main, container, false);


        recyclerView = v.findViewById(R.id.recycler_fav);

        rootLayout = v.findViewById(R.id.rootLayout);

        TextView addFav = v.findViewById(R.id.txtMakeFav);

        addFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ViewPager viewPager = getActivity().findViewById(R.id.viewPager);
                viewPager.setCurrentItem(3);
            }
        });

        RecyclerViewWithEmptySupport.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        loadFav();


        return v;

    }

    private void loadFav() {
        compositeDisposable.add(Common.favoritesRepository.getFavItems()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Consumer<List<Favorites>>() {
            @Override
            public void accept(List<Favorites> favorites) {
                displayFav(favorites);
            }
        }));
    }

    private void displayFav(List<Favorites> favorites) {


        List<Favorites> favoritesList = favorites;
        FavoritesAdapter favoritesAdapter = new FavoritesAdapter(getContext(), favorites);
            recyclerView.setAdapter(favoritesAdapter);
            recyclerView.setEmptyView(rootLayout.findViewById(R.id.rootLayout));

    }

    @Override
    public void onResume() {
        super.onResume();
       loadFav();
    }
}
