package com.studio.yami.moviecataloguefinal.vm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.studio.yami.moviecataloguefinal.model.Favorite;
import com.studio.yami.moviecataloguefinal.model.FilmDetail;
import com.studio.yami.moviecataloguefinal.model.FilmList;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private RepoMain repoMain;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repoMain = new RepoMain(application);
    }

    public void setMultiSearch(String query){
        repoMain.setMultiSearch(query);
    }

    public LiveData<List<FilmList>> getMultiSearch(){
        return repoMain.getMultiSearch();
    }

    public LiveData<List<FilmList>> getMovieList(){
        return repoMain.getMovieList();
    }

    public LiveData<List<FilmList>> getTvList(){
        return repoMain.getTvList();
    }

    public LiveData<FilmDetail> getFilmDetail(String category, int id){
        return repoMain.getFilmDetail(category, id);
    }

    public LiveData<List<Favorite>> getFavorite(){
        return repoMain.getFavorite();
    }

    public void insetFav(Favorite f){
        repoMain.insertFav(f);
    }

    public void deleteFav(Favorite f){
        repoMain.deleteFav(f);
    }

}
