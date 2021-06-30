package com.example.a1114049_1090780_iiatimd_app;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Recipe.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract RecipeDAO recipeDAO();

    private static AppDatabase instance;

    static synchronized AppDatabase getInstance(Context context){
        if (instance == null){
            instance = create(context);
        }
        return instance;
    }

    private static AppDatabase create(final Context context){
        return Room.databaseBuilder(context, AppDatabase.class, "recipes").fallbackToDestructiveMigration().build();
    }
}
