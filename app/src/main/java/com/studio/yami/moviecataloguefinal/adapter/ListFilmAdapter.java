package com.studio.yami.moviecataloguefinal.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studio.yami.moviecataloguefinal.Const;
import com.studio.yami.moviecataloguefinal.R;
import com.studio.yami.moviecataloguefinal.activity.DetailActivity;
import com.studio.yami.moviecataloguefinal.databinding.ItemLayoutBinding;
import com.studio.yami.moviecataloguefinal.model.FilmList;

import java.util.List;

public class ListFilmAdapter extends RecyclerView.Adapter<ListFilmAdapter.FilmHolder> {

    private Context context;
    private List<FilmList> filmList;

    public ListFilmAdapter(Context context, List<FilmList> filmList) {
        this.context = context;
        this.filmList = filmList;
    }

    @NonNull
    @Override
    public FilmHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_layout, viewGroup, false);
        return new FilmHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmHolder filmHolder, int i) {
        filmHolder.onBind(filmList.get(i));
    }

    @Override
    public int getItemCount() {
        return filmList.size();
    }


    class FilmHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemLayoutBinding binding;
        private int id, bg;
        private String cat;

        FilmHolder(@NonNull ItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        void onBind(FilmList film){
            id = film.getId();
            cat = film.getCategory();
            bg = film.getBackgroud();
            binding.setFilm(film);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(Const.ID, id);
            intent.putExtra(Const.CATEGORY, cat);
            intent.putExtra(Const.BACKGROUND, bg);
            context.startActivity(intent);
        }
    }
}
