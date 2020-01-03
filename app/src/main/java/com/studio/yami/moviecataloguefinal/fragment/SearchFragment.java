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
import android.widget.RadioGroup;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.studio.yami.moviecataloguefinal.Const;
import com.studio.yami.moviecataloguefinal.R;
import com.studio.yami.moviecataloguefinal.adapter.ListFilmAdapter;
import com.studio.yami.moviecataloguefinal.databinding.FragmentSearchBinding;
import com.studio.yami.moviecataloguefinal.model.FilmList;
import com.studio.yami.moviecataloguefinal.vm.MainViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    private MainViewModel viewModel;
    private RecyclerView frSearch;
    private ProgressBar loading;
    FloatingSearchView searchView;
    RadioGroup group;

    List<FilmList> films = new ArrayList<>();
    ListFilmAdapter adapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentSearchBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        frSearch = binding.rvSearch;
        group = binding.rbGroup;
        loading = binding.progessSearch;
        searchView = binding.floatingSearchView;

        searchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {

            }

            @Override
            public void onSearchAction(String currentQuery) {
                loading.setVisibility(View.VISIBLE);
                viewModel.setMultiSearch(currentQuery);
            }
        });

        viewModel.getMultiSearch().observe(this, new Observer<List<FilmList>>() {
            @Override
            public void onChanged(@Nullable List<FilmList> filmLists) {
                if (filmLists != null) {
                    films = filmLists;
                    int i = group.getCheckedRadioButtonId();
                    setCategory(filmLists, i);
                }
            }
        });

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            frSearch.setLayoutManager(new GridLayoutManager(getContext(), 2));
        } else {
            frSearch.setLayoutManager(new GridLayoutManager(getContext(), 4));
        }

        if(savedInstanceState == null){
            binding.rbAll.setChecked(true);
        }

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(films != null){
                    setCategory(films, i);
                }
            }
        });

        return binding.getRoot();
    }

    private List<FilmList> checkCategory(List<FilmList> lists, String category){
        List<FilmList> filmLists = new ArrayList<>();
        for (FilmList film: lists){
            if(film.getCategory().equals(category)){
                filmLists.add(film);
            }
        }
        return filmLists;
    }

    private void setCategory(List<FilmList> filmLists, int i){
        List<FilmList> favLists = new ArrayList<>();
        switch (i) {
            case R.id.rb_all:
                favLists = filmLists;
                break;
            case R.id.rb_movie:
                favLists = checkCategory(filmLists, Const.MOVIE);
                break;
            case R.id.rb_tv:
                favLists = checkCategory(filmLists, Const.TV);
                break;
        }

        adapter = new ListFilmAdapter(getContext(), favLists);
        frSearch.setAdapter(adapter);
        loading.setVisibility(View.GONE);
    }

}
