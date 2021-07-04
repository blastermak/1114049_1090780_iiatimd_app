package com.example.a1114049_1090780_iiatimd_app;

import android.app.Application;
import androidx.lifecycle.LiveData;

import java.util.List;

public class RecipeRepository {

    private RecipeDAO recipeDAO;

    private LiveData<List<Recipe>> allRecipes;
    private LiveData<List<RecipeWithInstructions>> allRecipesWithInstructions;

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

    LiveData<List<Recipe>> getSearchRecipe(String searchString){
        return recipeDAO.getSearchRecipe(searchString);
    }

    void insertRecipe(Recipe recipe){
        AppDatabase.databaseWriterExecutor.execute(() -> {
            recipeDAO.insertRecipe(recipe);
        });
    }

    void updateRecipe(Recipe recipe){
        AppDatabase.databaseWriterExecutor.execute(() -> {
            recipeDAO.updateRecipe(recipe);
        });
    }

    LiveData<List<RecipeWithInstructions>> getRecipesWithInstructions(int recipeId){
        return recipeDAO.getRecipesWithInstructions(recipeId);
    }

    LiveData<Instruction> getInstructionById (int instructionId){
        return recipeDAO.getInstructionById(instructionId);
    }

    void insertInstruction(Instruction instruction){
        AppDatabase.databaseWriterExecutor.execute(() -> {
            recipeDAO.insertInstruction(instruction);
        });
    }

    void updateInstruction(Instruction instruction){
        AppDatabase.databaseWriterExecutor.execute(() -> {
            recipeDAO.updateInstruction(instruction);
        });
    }

    LiveData<List<RecipeWithIngredients>> getRecipesWithIngredients(int recipeId){
        return recipeDAO.getRecipesWithIngredients(recipeId);
    }

    void insertIngredient(Ingredient ingredient){
        AppDatabase.databaseWriterExecutor.execute(() -> {
            recipeDAO.insertIngredient(ingredient);
        });
    }

    void updateIngredient(Ingredient ingredient){
        AppDatabase.databaseWriterExecutor.execute(() -> {
            recipeDAO.updateIngredient(ingredient);
        });
    }


}
