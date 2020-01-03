package com.studio.yami.moviecataloguefinal.server;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.studio.yami.moviecataloguefinal.BuildConfig;
import com.studio.yami.moviecataloguefinal.Const;
import com.studio.yami.moviecataloguefinal.model.FilmDetail;
import com.studio.yami.moviecataloguefinal.model.FilmDiscover;
import com.studio.yami.moviecataloguefinal.model.FilmList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepoServer {

    private ApiClient client;
    private Map<String, String> query = new HashMap<>();

    private MutableLiveData<List<FilmList>> multiSearch = new MutableLiveData<>();
    private MutableLiveData<List<FilmList>> movieLists = new MutableLiveData<>();
    private MutableLiveData<List<FilmList>> tvLists = new MutableLiveData<>();
    private MutableLiveData<FilmDetail> filmDetail = new MutableLiveData<>();


    public RepoServer() {
        client = new ApiClient();
        query.put(Const.API_KEY, BuildConfig.ApiKey);
        query.put(Const.LANGUAGE, Const.LANG_EN);
    }

    public void setMultiSearch(String parm){
        client.getService().getSearch(parm, query).enqueue(new Callback<FilmDiscover>() {
            @Override
            public void onResponse(@NonNull Call<FilmDiscover> call, @NonNull Response<FilmDiscover> response) {
                if (response.isSuccessful() && response.body() != null){
                    List<FilmList> lists = new ArrayList<>();
                    for (FilmList f: response.body().getResult()){
                        if(!f.getCategory().matches("person")){
                            lists.add(f);
                        }
                    }
                    multiSearch.postValue(lists);
                }
            }

            @Override
            public void onFailure(@NonNull Call<FilmDiscover> call, @NonNull Throwable t) {

            }
        });
    }

    public LiveData<List<FilmList>> getMultiSearch(){
        return multiSearch;
    }

    public LiveData<List<FilmList>> getMovieList() {
        client.getService().getFilmList(Const.MOVIE, query).enqueue(new Callback<FilmDiscover>() {
            @Override
            public void onResponse(@NonNull Call<FilmDiscover> call,@NonNull Response<FilmDiscover> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<FilmList> movie = response.body().getResult();
                    for (FilmList m: movie){
                        m.setCategory(Const.MOVIE);
                    }
                    movieLists.postValue(movie);
                }
            }

            @Override
            public void onFailure(@NonNull Call<FilmDiscover> call,@NonNull Throwable t) {

            }
        });
        return movieLists;
    }

    public LiveData<List<FilmList>> getTvList() {
        client.getService().getFilmList(Const.TV, query).enqueue(new Callback<FilmDiscover>() {
            @Override
            public void onResponse(@NonNull Call<FilmDiscover> call,@NonNull Response<FilmDiscover> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<FilmList> tv = response.body().getResult();
                    for (FilmList t: tv){
                        t.setCategory(Const.TV);
                    }
                    tvLists.postValue(tv);
                }
            }

            @Override
            public void onFailure(@NonNull Call<FilmDiscover> call,@NonNull Throwable t) {

            }
        });
        return tvLists;
    }

    public LiveData<FilmDetail> getFilmDetail(String category, int id){
        client.getService().getFilmDetail(category, id, query).enqueue(new Callback<FilmDetail>() {
            @Override
            public void onResponse(@NonNull Call<FilmDetail> call,@NonNull Response<FilmDetail> response) {
                if(response.isSuccessful() && response.body() != null){
                    FilmDetail detail = response.body();
                    filmDetail.postValue(detail);
                }
            }

            @Override
            public void onFailure(@NonNull Call<FilmDetail> call,@NonNull Throwable t) {

            }
        });
        return filmDetail;
    }

}
