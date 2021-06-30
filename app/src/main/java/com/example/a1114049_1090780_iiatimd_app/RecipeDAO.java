package com.example.a1114049_1090780_iiatimd_app;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


@Dao
public interface RecipeDAO {

    @Query("SELECT * FROM recipe")
    public LiveData<List<Recipe>> getAllRecipes();

    @Insert()
    void InsertRecipe(Recipe recipe);

    @Insert()
    void insertAllRecipes(Recipe... recipes);

    @Delete
    void deleteRecipe(Recipe recipe);
}
