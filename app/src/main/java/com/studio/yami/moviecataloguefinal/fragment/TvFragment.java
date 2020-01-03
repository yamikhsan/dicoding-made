package com.studio.yami.moviecataloguefinal.fragment;


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
import android.widget.ProgressBar;

import com.studio.yami.moviecataloguefinal.R;
import com.studio.yami.moviecataloguefinal.adapter.ListFilmAdapter;
import com.studio.yami.moviecataloguefinal.databinding.FragmentTvBinding;
import com.studio.yami.moviecataloguefinal.model.FilmList;
import com.studio.yami.moviecataloguefinal.vm.MainViewModel;

import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvFragment extends Fragment {

    RecyclerView rvMain;
    ProgressBar load;

    public TvFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentTvBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tv, container, false);

        rvMain = binding.rvTv;
        load = binding.loadTv;

        getViewModel();

        if(Objects.requireNonNull(getActivity()).getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            rvMain.setLayoutManager(new GridLayoutManager(getContext(), 2));
        }else {
            rvMain.setLayoutManager(new GridLayoutManager(getContext(), 4));
        }

        return binding.getRoot();
    }

    private void getViewModel(){
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getTvList().observe(this, new Observer<List<FilmList>>() {
            @Override
            public void onChanged(@Nullable List<FilmList> listFilms) {
                if (listFilms != null) {
                    buildRecyclerView(listFilms);
                    load.setVisibility(View.GONE);
                    rvMain.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void buildRecyclerView(List<FilmList> filmLists){
        ListFilmAdapter adapter = new ListFilmAdapter(getContext(), filmLists);
        rvMain.setAdapter(adapter);
    }


}
