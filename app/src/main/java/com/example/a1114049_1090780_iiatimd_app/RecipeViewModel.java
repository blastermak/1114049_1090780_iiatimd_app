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

    LiveData<List<Recipe>> getSearchRecipes(String searchString) {
        return recipeRepository.getSearchRecipe(searchString);
    }

    LiveData<Instruction> getInstructionById(int instructionId){
        return recipeRepository.getInstructionById(instructionId);
    }

    public void insertRecipe(Recipe recipe){ recipeRepository.insertRecipe(recipe); }

    public void updateRecipe(Recipe recipe){ recipeRepository.updateRecipe(recipe); }

    LiveData<List<RecipeWithInstructions>> getRecipesWithInstructions(int recipeId){
        return recipeRepository.getRecipesWithInstructions(recipeId);
    }

    public void insertInstruction(Instruction instruction){
        recipeRepository.insertInstruction(instruction);
    }

    public void updateInstruction(Instruction instruction){
        recipeRepository.updateInstruction(instruction);
    }

    LiveData<RecipeWithIngredients> getRecipesWithIngredients(int recipeId){
        return recipeRepository.getRecipesWithIngredients(recipeId);
    }

    public void insertIngredient(Ingredient ingredient){
        recipeRepository.insertIngredient(ingredient);
    }




}
