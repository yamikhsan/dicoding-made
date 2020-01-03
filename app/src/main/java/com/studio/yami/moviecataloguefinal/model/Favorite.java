package com.studio.yami.moviecataloguefinal.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity
public class Favorite implements Parcelable {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "poster")
    private String poster;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "score")
    private String score;

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "background")
    private int background;

    public void setId(int id) {
        this.id = id;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public int getId() {
        return id;
    }

    public String getPoster() {
        return poster;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getScore() {
        return score;
    }

    public int getBackground() {
        return background;
    }

    public String getCategory() {
        return category;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.poster);
        dest.writeString(this.name);
        dest.writeString(this.date);
        dest.writeString(this.score);
        dest.writeString(this.category);
        dest.writeInt(this.background);
    }

    public Favorite() {
    }

    protected Favorite(Parcel in) {
        this.id = in.readInt();
        this.poster = in.readString();
        this.name = in.readString();
        this.date = in.readString();
        this.score = in.readString();
        this.category = in.readString();
        this.background = in.readInt();
    }

    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel source) {
            return new Favorite(source);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };
}
