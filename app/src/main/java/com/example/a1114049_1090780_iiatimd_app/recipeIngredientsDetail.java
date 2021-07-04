package com.example.a1114049_1090780_iiatimd_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class recipeIngredientsDetail extends AppCompatActivity {

    private RecyclerView ingredientsListRecyclerView;
    private IngredientAdapter ingredientsListRecyclerViewAdapter;
    private RecyclerView.LayoutManager ingredientsLayoutManager;

    private RecipeViewModel recipeViewModel;

    private ArrayList<Ingredient> myIngredients = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_ingredients_detail);

        ingredientsListRecyclerView = findViewById(R.id.ingredientsRecyclerView);
        ingredientsLayoutManager = new LinearLayoutManager(this);
        ingredientsListRecyclerView.setLayoutManager(ingredientsLayoutManager);
        ingredientsListRecyclerView.hasFixedSize();

        ingredientsListRecyclerViewAdapter = new IngredientAdapter(myIngredients);
        ingredientsListRecyclerView.setAdapter(ingredientsListRecyclerViewAdapter);

        Intent receivingIntent = getIntent();
        int detailRecipeid = receivingIntent.getIntExtra("RECIPE_ID", 0);

        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        recipeViewModel.getRecipesWithIngredients(detailRecipeid).observe(this, ingredients -> {
            try {
                List<Ingredient> ingredientList = ingredients.ingredientList;
                for (int i = 0; i < ingredientList.size(); i++){
                    myIngredients.add(ingredientList.get(i));
                    ingredientsListRecyclerViewAdapter.notifyItemInserted(myIngredients.size()-1);
                }
            } catch (IndexOutOfBoundsException e){
            }

        });
    }
}