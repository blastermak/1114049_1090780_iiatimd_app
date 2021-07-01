package com.example.a1114049_1090780_iiatimd_app;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {
    private RecipeRepository recipeRepository;

//    private final LiveData<Recipe> recipeById;
    private final LiveData<List<Recipe>> allRecipes;


    public RecipeViewModel (Application app){
        super(app);
        recipeRepository = new RecipeRepository(app);
        allRecipes = recipeRepository.getAllRecipes();
    }

    LiveData<Recipe> getRecipeById(int recipeId) {
        return recipeRepository.getRecipeById(recipeId);
    }

    LiveData<List<Recipe>> getAllRecipes() { return allRecipes; }

    public void insert(Recipe recipe){ recipeRepository.insert(recipe); }

    public void update(Recipe recipe){ recipeRepository.update(recipe); }



}
