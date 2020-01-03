package com.studio.yami.moviecataloguefinal.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.studio.yami.moviecataloguefinal.Const;
import com.studio.yami.moviecataloguefinal.R;
import com.studio.yami.moviecataloguefinal.databinding.ActivityDetailBinding;
import com.studio.yami.moviecataloguefinal.model.Favorite;
import com.studio.yami.moviecataloguefinal.model.FilmDetail;
import com.studio.yami.moviecataloguefinal.vm.MainViewModel;
import com.studio.yami.moviecataloguefinal.widget.FavoriteWidget;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private MainViewModel viewModel;
    private boolean favStatus = false;
    private Drawable enable, disable;
    private ImageView ivFav;
    private final Favorite favorite = new Favorite();

    private ScrollView scrollView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        scrollView = binding.scrollView2;
        progressBar = binding.progressBar;

        ivFav = binding.top.ivFav;
        ivFav.setVisibility(View.GONE);

        String category = getIntent().getStringExtra(Const.CATEGORY);
        final int id = getIntent().getIntExtra(Const.ID, 0);
        int bg = getIntent().getIntExtra(Const.BACKGROUND, 0);

        favorite.setCategory(category);
        favorite.setId(id);
        favorite.setBackground(bg);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getFilmDetail(category, id).observe(this, new Observer<FilmDetail>() {
            @Override
            public void onChanged(@Nullable FilmDetail filmDetail) {
                if(filmDetail != null){
                    binding.setDetail(filmDetail);
                    ivFav.setVisibility(View.VISIBLE);
                    favorite.setName(filmDetail.getTitle());
                    favorite.setPoster(filmDetail.getPoster());
                    favorite.setDate(filmDetail.getYear());
                    favorite.setScore(filmDetail.getScore());
                    progressBar.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                }
            }
        });

        viewModel.getFavorite().observe(this, new Observer<List<Favorite>>() {
            @Override
            public void onChanged(@Nullable List<Favorite> favorites) {
                if(favorites != null){
                    for (Favorite f: favorites){
                        if(id == f.getId()){
                            favStatus = true;
                            setFavStatus();
                            return;
                        }
                    }

                }
            }
        });

    }

    private void setFavStatus(){
        enable = ContextCompat.getDrawable(this, R.drawable.ic_favorite_pink_24dp);
        disable = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_pink_24dp);
        ivFav.setImageDrawable(favStatus? enable: disable);
    }

    public void handleFavorite(View view) {
        FavoriteWidget.updateFavoriteWidget(this);
        if(favStatus){
            ivFav.setImageDrawable(disable);
            favStatus = false;
            viewModel.deleteFav(favorite);
            onToast(getResources().getString(R.string.remove_msg));
        }else {
            ivFav.setImageDrawable(enable);
            favStatus = true;
            viewModel.insetFav(favorite);
            onToast(getResources().getString(R.string.add_fav));
        }
    }

    private void onToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
