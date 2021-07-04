package com.example.a1114049_1090780_iiatimd_app;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;


// Koppeling van ingredienten met recepten

public class RecipeWithIngredients {
    @Embedded public Recipe recipe;
    @Relation(
            parentColumn = "uuid",
            entityColumn = "recipe_id"
    )
    public List<Ingredient> ingredientList;
}
