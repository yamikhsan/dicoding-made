package com.studio.yami.moviecataloguefinal.vm;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import com.studio.yami.moviecataloguefinal.local.RepoLocal;
import com.studio.yami.moviecataloguefinal.model.Favorite;
import com.studio.yami.moviecataloguefinal.model.FilmDetail;
import com.studio.yami.moviecataloguefinal.model.FilmList;
import com.studio.yami.moviecataloguefinal.server.RepoServer;

import java.util.List;

class RepoMain {

    private RepoServer repoServer;

    private RepoLocal repoLocal;
    private LiveData<List<Favorite>> favorite;

    RepoMain(Application application){
        repoServer = new RepoServer();
        repoLocal = new RepoLocal(application);
        favorite = repoLocal.getFavorite();
    }

    void setMultiSearch(String query){
        repoServer.setMultiSearch(query);
    }

    LiveData<List<FilmList>> getMultiSearch(){
        return repoServer.getMultiSearch();
    }

    LiveData<List<FilmList>> getMovieList(){
        return repoServer.getMovieList();
    }

    LiveData<List<FilmList>> getTvList(){
        return repoServer.getTvList();
    }

    LiveData<FilmDetail> getFilmDetail(String category, int id){
        return repoServer.getFilmDetail(category, id);
    }

    LiveData<List<Favorite>> getFavorite(){
        return favorite;
    }

    void insertFav(Favorite f){
        repoLocal.insert(f);
    }

    void deleteFav(Favorite f){
        repoLocal.delete(f);
    }
}
