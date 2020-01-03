package com.studio.yami.moviecataloguefinal.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.studio.yami.moviecataloguefinal.Const;
import com.studio.yami.moviecataloguefinal.R;
import com.studio.yami.moviecataloguefinal.activity.DetailActivity;
import com.studio.yami.moviecataloguefinal.databinding.ItemFavoriteBinding;
import com.studio.yami.moviecataloguefinal.model.Favorite;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder> {

    private Context context;
    private List<Favorite> favorites;

    public FavoriteAdapter(Context context, List<Favorite> favorites) {
        this.context = context;
        this.favorites = favorites;
    }

    @NonNull
    @Override
    public FavoriteHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemFavoriteBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_favorite, viewGroup, false);
        return new FavoriteHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteHolder favoriteHolder, int i) {
        favoriteHolder.onBind(favorites.get(i));
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    class FavoriteHolder extends RecyclerView.ViewHolder {

        ItemFavoriteBinding binding;
        private int id, bg;
        private String cat, lang;
        private Favorite favorite;

        FavoriteHolder(@NonNull ItemFavoriteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDetail();
                }
            });

            binding.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onDelete();
                }
            });
        }

        void onBind(Favorite f){
            favorite = f;
            id = f.getId();
            cat = f.getCategory();
            lang = Const.LANG_EN;
            bg = f.getBackground();
            binding.setFav(f);
        }

        private void onDetail(){
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(Const.ID, id);
            intent.putExtra(Const.CATEGORY, cat);
            intent.putExtra(Const.LANGUAGE, lang);
            intent.putExtra(Const.BACKGROUND, bg);
            context.startActivity(intent);
        }

        private void onDelete(){
            Intent intent = new Intent(Const.DELETE);
            intent.putExtra(Const.FAVORITE, favorite);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        }
    }
}
