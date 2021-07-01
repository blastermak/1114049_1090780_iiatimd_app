package com.example.a1114049_1090780_iiatimd_app;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;



@Dao
public interface RecipeDAO {

    @Query("SELECT * FROM recipe")
    LiveData<List<Recipe>> getRecipe();

    @Query("SELECT * FROM recipe WHERE uuid = :id")
    LiveData<Recipe> getRecipeById(int id);

    @Insert()
    void insertAllRecipes(Recipe... recipes);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertRecipe(Recipe recipe);

    @Update
    void updateRecipe(Recipe recipe);

    @Delete
    void deleteRecipe(Recipe recipe);
}
