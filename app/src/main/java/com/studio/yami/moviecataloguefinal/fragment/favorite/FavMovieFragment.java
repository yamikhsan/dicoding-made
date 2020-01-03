package com.studio.yami.moviecataloguefinal.fragment.favorite;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studio.yami.moviecataloguefinal.Const;
import com.studio.yami.moviecataloguefinal.R;
import com.studio.yami.moviecataloguefinal.adapter.FavoriteAdapter;
import com.studio.yami.moviecataloguefinal.databinding.FragmentFavMovieBinding;
import com.studio.yami.moviecataloguefinal.model.Favorite;
import com.studio.yami.moviecataloguefinal.vm.MainViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavMovieFragment extends Fragment {

    RecyclerView rvMain;

    public FavMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentFavMovieBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fav_movie, container, false);

        rvMain = binding.rvFavMovie;

        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getFavorite().observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(@Nullable List<Favorite> favorites) {
                if(favorites != null){
                    List<Favorite> list = new ArrayList<>();
                    for(Favorite f: favorites){
                        if(f.getCategory().matches(Const.MOVIE)){
                            list.add(f);
                        }
                    }
                    FavoriteAdapter adapter = new FavoriteAdapter(getContext(), list);
                    rvMain.setAdapter(adapter);
                    if(Objects.requireNonNull(getActivity()).getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
                        rvMain.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    }else {
                        rvMain.setLayoutManager(new GridLayoutManager(getContext(), 4));
                    }
                }
            }
        });

        return binding.getRoot();
    }

}
