package com.example.a1114049_1090780_iiatimd_app;

public class insertRecipeTask implements Runnable {

    AppDatabase db;
    Recipe recipe;

    public insertRecipeTask(AppDatabase db, Recipe recipe){
        this.db = db;
        this.recipe = recipe;
    }

    @Override
    public void run(){
        db.recipeDAO().InsertRecipe(this.recipe);
    }

}
