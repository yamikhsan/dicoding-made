package com.studio.yami.moviecataloguefinal.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.studio.yami.moviecataloguefinal.Const;
import com.studio.yami.moviecataloguefinal.R;
import com.studio.yami.moviecataloguefinal.local.FavoriteDB;
import com.studio.yami.moviecataloguefinal.model.Favorite;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StackRemoteViews implements RemoteViewsService.RemoteViewsFactory {

    private final Context context;

    private List<Favorite> favorites = new ArrayList<>();
    private final List<Bitmap> widgetItems = new ArrayList<>();

    StackRemoteViews(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @SuppressLint("CheckResult")
    @Override
    public void onDataSetChanged() {
        widgetItems.clear();
        favorites.clear();

        FavoriteDB db = FavoriteDB.getAppDatabase(context);
        favorites = db.favoriteDao().listAll();

        if (favorites != null){

            for (Favorite f: favorites){

                Bitmap bitmap = null;
                try {
                    bitmap = Glide.with(context)
                            .asBitmap()
                            .load("https://image.tmdb.org/t/p/w342" + f.getPoster())
                            .submit()
                            .get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                widgetItems.add(bitmap);

            }
        }

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return widgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        Favorite f = favorites.get(i);
        String widget = f.getName() + " " + "(" + f.getDate() + ")";
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        rv.setImageViewBitmap(R.id.imageView, widgetItems.get(i));
        rv.setTextViewText(R.id.tv_widget, widget);

        Bundle extras = new Bundle();
        extras.putInt(Const.ID, f.getId());
        extras.putString(Const.CATEGORY, f.getCategory());
        extras.putInt(Const.BACKGROUND, f.getBackground());
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

}
