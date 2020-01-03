package com.studio.yami.moviecataloguefinal.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FilmDiscover {

    @SerializedName("results")
    private List<FilmList> result;

    public List<FilmList> getResult() {
        return result;
    }
}
