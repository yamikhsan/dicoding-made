package com.studio.yami.moviecataloguefinal.server;

import com.studio.yami.moviecataloguefinal.Const;
import com.studio.yami.moviecataloguefinal.model.FilmDetail;
import com.studio.yami.moviecataloguefinal.model.FilmDiscover;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {

    @GET("discover/{"+ Const.CATEGORY +"}")
    Call<FilmDiscover> getFilmList(@Path(Const.CATEGORY) String category,
                                   @QueryMap Map<String, String> parm);

    @GET("{"+ Const.CATEGORY +"}/{"+ Const.ID +"}")
    Call<FilmDetail> getFilmDetail(@Path(Const.CATEGORY) String category,
                                   @Path(Const.ID) int id,
                                   @QueryMap Map<String, String> parm);

    @GET("search/multi")
    Call<FilmDiscover> getSearch(@Query("query") String query,
                                 @QueryMap Map<String, String> parm);

    @GET("discover/" + Const.MOVIE)
    Call<FilmDiscover> getReleaseToday(@QueryMap Map<String, String> date,
                                       @QueryMap Map<String, String> parm);
}
