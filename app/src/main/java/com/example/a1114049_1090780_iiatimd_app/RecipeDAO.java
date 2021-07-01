package com.example.a1114049_1090780_iiatimd_app;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;



@Dao
public interface RecipeDAO {

    @Query("SELECT * FROM recipe")
    public Flowable<List<Recipe>> getRecipe();

    @Query("SELECT * FROM recipe WHERE uuid = :id")
    public Flowable<Recipe> getRecipeById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public Completable InsertRecipe(Recipe recipe);

    @Insert()
    void insertAllRecipes(Recipe... recipes);

    @Delete
    void deleteRecipe(Recipe recipe);
}
