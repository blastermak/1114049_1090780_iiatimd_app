package com.example.a1114049_1090780_iiatimd_app;

public class insertMultipleRecipesTask implements Runnable{

    AppDatabase db;
    Recipe[] recipes;

    public insertMultipleRecipesTask(AppDatabase db, Recipe[] recipes){
        this.db = db;
        this.recipes = recipes;
    }

    @Override
    public void run(){
        db.recipeDAO().insertAllRecipes(this.recipes);
    }
}