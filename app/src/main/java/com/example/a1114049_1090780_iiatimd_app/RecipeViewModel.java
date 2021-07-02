package com.example.a1114049_1090780_iiatimd_app;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {
    private RecipeRepository recipeRepository;

//    private final LiveData<Recipe> recipeById;
    private final LiveData<List<Recipe>> allRecipes;

//    private final LiveData<List<RecipeWithInstructions>> allRecipesWithInstructions;


    public RecipeViewModel (Application app){
        super(app);
        recipeRepository = new RecipeRepository(app);
        allRecipes = recipeRepository.getAllRecipes();
//        allRecipesWithInstructions = recipeRepository.getRecipesWithInstructions();
    }

    LiveData<Recipe> getRecipeById(int recipeId) {
        return recipeRepository.getRecipeById(recipeId);
    }

    LiveData<List<Recipe>> getAllRecipes() { return allRecipes; }

    public void insertRecipe(Recipe recipe){ recipeRepository.insertRecipe(recipe); }

    public void updateRecipe(Recipe recipe){ recipeRepository.updateRecipe(recipe); }

    LiveData<RecipeWithInstructions> getRecipesWithInstructions(int recipeId){
        return recipeRepository.getRecipesWithInstructions(recipeId);
    }

    public void insertInstruction(Instruction instruction){
        recipeRepository.insertInstruction(instruction);
    }




}