package com.studio.yami.moviecataloguefinal.local;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.studio.yami.moviecataloguefinal.model.Favorite;

import java.util.List;

public class RepoLocal {

    private FavoriteDao dao;
    private LiveData<List<Favorite>> readAll;

    public RepoLocal(Application application){
        FavoriteDB bd = FavoriteDB.getAppDatabase(application);
        dao = bd.favoriteDao();
        readAll = dao.readAll();
    }

    public LiveData<List<Favorite>> getFavorite(){
        return readAll;
    }

    public void insert(Favorite f){
        new insertAsync(dao).execute(f);
    }

    public void delete(Favorite f){
        new deleteAsync(dao).execute(f);
    }

    private static class insertAsync extends AsyncTask<Favorite, Void, Void> {

        private FavoriteDao daoAsync;

        insertAsync(FavoriteDao o){
            this.daoAsync = o;
        }

        @Override
        protected Void doInBackground(Favorite... favorites) {
            daoAsync.insert(favorites[0]);
            return null;
        }
    }

    private static class deleteAsync extends AsyncTask<Favorite, Void, Void>{

        private FavoriteDao daoAsync;

        deleteAsync(FavoriteDao o){
            this.daoAsync = o;
        }

        @Override
        protected Void doInBackground(Favorite... favorites) {
            daoAsync.delete(favorites[0]);
            return null;
        }
    }

}
