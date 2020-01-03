package com.studio.yami.moviecataloguefinal.model;

import com.google.gson.annotations.SerializedName;
import com.studio.yami.moviecataloguefinal.Const;
import com.studio.yami.moviecataloguefinal.DateTimeFormat;
import com.studio.yami.moviecataloguefinal.R;

public class FilmList {

    @SerializedName(Const.ID)
    private int id;

    @SerializedName(value = Const.TITLE, alternate = Const.NAME)
    private String title;

    @SerializedName(value = Const.RELEASE_DATE, alternate = Const.FIRST_AIR_DATE)
    private String date;

    @SerializedName(Const.POSTER)
    private String backdrop;

    @SerializedName(Const.VOTE)
    private double score;

    @SerializedName("media_type")
    private String category;

    private int background;

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date == null ?"-" :DateTimeFormat.formatDate(date, "yyyy");
    }

    public String getBackdrop() {

        return backdrop;
    }

    public String getScore() {
        return String.valueOf(score);
    }

    public int getBackgroud(){
        setBackground();
        return background;
    }

    private void setBackground() {
        int red = R.drawable.circle_red;
        int yellow = R.drawable.circle_yellow;
        int green = R.drawable.circle_green;
        if(score > 6.66){
            background = green;
        }
        else if (score > 3.33){
            background = yellow;
        }
        else {
            background = red;
        }
    }

    public int getId() {
        return id;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public String getCategory(){
        return category;
    }
}
