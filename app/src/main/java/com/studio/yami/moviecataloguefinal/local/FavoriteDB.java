package com.studio.yami.moviecataloguefinal.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.studio.yami.moviecataloguefinal.model.Favorite;

@Database(entities = {Favorite.class}, version = 2, exportSchema = false)
public abstract class FavoriteDB extends RoomDatabase {

    public abstract FavoriteDao favoriteDao();

    private static FavoriteDB INSTANCE;

    public static FavoriteDB getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), FavoriteDB.class, "db_favorite")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
