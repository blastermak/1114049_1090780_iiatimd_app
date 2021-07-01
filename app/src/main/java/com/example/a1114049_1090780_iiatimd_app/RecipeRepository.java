package com.example.a1114049_1090780_iiatimd_app;

import android.app.Application;
import androidx.lifecycle.LiveData;

import java.util.List;

public class RecipeRepository {

    private RecipeDAO recipeDAO;
    private LiveData<Recipe> recipeById;
    private LiveData<List<Recipe>> allRecipes;

    RecipeRepository(Application app){
        AppDatabase db = AppDatabase.getDatabase(app);
        recipeDAO = db.recipeDAO();
        allRecipes = recipeDAO.getRecipe();
    }

    LiveData<List<Recipe>> getAllRecipes(){
        return allRecipes;
    }
    LiveData<Recipe> getRecipeById(int recipeId){
        return recipeDAO.getRecipeById(recipeId);
    }

    void insert(Recipe recipe){
        AppDatabase.databaseWriterExecutor.execute(() -> {
            recipeDAO.insertRecipe(recipe);
        });
    }

    void update(Recipe recipe){
        AppDatabase.databaseWriterExecutor.execute(() -> {
            recipeDAO.updateRecipe(recipe);
        });
    }


}
