package com.studio.yami.moviecataloguefinal.fragment.favorite;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studio.yami.moviecataloguefinal.R;
import com.studio.yami.moviecataloguefinal.adapter.SectionsPagerAdapter;
import com.studio.yami.moviecataloguefinal.databinding.FragmentFavoriteBinding;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    FavMovieFragment movie = new FavMovieFragment();
    FavTvFragment tv = new FavTvFragment();

    SectionsPagerAdapter sectionsPagerAdapter;
    ViewPager viewPager;
    TabLayout tabs;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentFavoriteBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false);

        sectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        viewPager = binding.viewPager;
        tabs = binding.tabs;

        sectionsPagerAdapter.addFragment(getResources().getString(R.string.movie), movie);
        sectionsPagerAdapter.addFragment(getResources().getString(R.string.tv), tv);

        viewPager.setAdapter(sectionsPagerAdapter);
        tabs.setupWithViewPager(viewPager);

        return binding.getRoot();
    }

}
