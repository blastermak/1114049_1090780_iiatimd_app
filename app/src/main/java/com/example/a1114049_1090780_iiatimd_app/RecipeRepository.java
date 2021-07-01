package com.example.a1114049_1090780_iiatimd_app;

import android.app.Application;
import androidx.lifecycle.LiveData;

import java.util.List;

public class RecipeRepository {

    private RecipeDAO recipeDAO;
    private LiveData<List<Recipe>> allRecipes;

    RecipeRepository(Application app){
        AppDatabase db = AppDatabase.getDatabase(app);
        recipeDAO = db.recipeDAO();
        allRecipes = recipeDAO.getRecipe();
    }

    LiveData<List<Recipe>> getAllRecipes(){
        return allRecipes;
    }

    void insert(Recipe recipe){
        AppDatabase.databaseWriterExecutor.execute(() -> {
            recipeDAO.insertRecipe(recipe);
        });
    }
}
