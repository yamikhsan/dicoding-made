package com.studio.yami.moviecataloguefinal.model;

import com.google.gson.annotations.SerializedName;
import com.studio.yami.moviecataloguefinal.Const;
import com.studio.yami.moviecataloguefinal.DateTimeFormat;

import java.util.List;

public class FilmDetail {

    @SerializedName(Const.BACKDROP)
    private String backdrop;

    @SerializedName(Const.POSTER)
    private String poster;

    @SerializedName(value = Const.TITLE, alternate = Const.NAME)
    private String title;

    @SerializedName(Const.OVERVIEW)
    private String overview;

    @SerializedName(Const.STATUS)
    private String status;

    @SerializedName(value = Const.RELEASE_DATE, alternate = Const.FIRST_AIR_DATE)
    private String date;

    @SerializedName(Const.VOTE)
    private double score;

    @SerializedName(Const.GENRE)
    private List<Genres> genres;

    @SerializedName(Const.RUNTIME)
    private int runtime;

    @SerializedName(Const.EPISODE_RUNTIME)
    private int[] episodeRuntime;

    private String duration;

    private String coma = ", ";

    public String getBackdrop() {
        return backdrop;
    }

    public String getPoster() {
        return poster;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return DateTimeFormat.formatDate(date,"dd MMMM yyyy");
    }

    public String getYear() {
        return DateTimeFormat.formatDate(date,"yyyy");
    }

    public String getScore() {
        return String.valueOf(score);
    }

    public String getGenres() {
        StringBuilder builder = new StringBuilder();
        for (int i=0; i<genres.size(); i++){
            builder.append(genres.get(i).getName());
            if(i != genres.size() - 1){
                builder.append(coma);
            }
        }
        return builder.toString();
    }

    public String getDuration() {
        setDuration();
        return duration;
    }

    private void setDuration() {
        String m = " m";
        if (runtime != 0 && episodeRuntime == null) {
            String rt = String.valueOf(runtime);
            duration = rt + m;
        }
        if (runtime == 0 && episodeRuntime != null){
            StringBuilder builder = new StringBuilder();
            for (int i=0; i<episodeRuntime.length; i++){
                builder.append(episodeRuntime[i]);
                builder.append(m);
                if (i != episodeRuntime.length - 1){
                    builder.append(coma);
                }
            }
            duration = builder.toString();
        }
    }
}
