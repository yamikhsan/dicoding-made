package com.studio.yami.moviecataloguefinal.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.database.Cursor;

import com.studio.yami.moviecataloguefinal.model.Favorite;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Query("SELECT * FROM Favorite ORDER BY id ASC")
    LiveData<List<Favorite>> readAll();

    @Query("SELECT * FROM Favorite ORDER BY id ASC")
    List<Favorite> listAll();

    @Query("SELECT * FROM Favorite ORDER BY id ASC")
    Cursor getAll();

    @Insert
    void insert(Favorite f);

    @Delete
    void delete(Favorite f);

}
