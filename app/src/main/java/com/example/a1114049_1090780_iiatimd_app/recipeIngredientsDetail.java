package com.example.a1114049_1090780_iiatimd_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

// Activity om de ingrediënten van een recept weer te geven

public class recipeIngredientsDetail extends AppCompatActivity {

    private RecyclerView ingredientsListRecyclerView;
    private IngredientAdapter ingredientsListRecyclerViewAdapter;
    private RecyclerView.LayoutManager ingredientsLayoutManager;

    private RecipeViewModel recipeViewModel;

    private FloatingActionButton fab;

    private ArrayList<Ingredient> myIngredients = new ArrayList<>();
    private List<Ingredient> ingredientList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_ingredients_detail);

        ingredientsListRecyclerView = findViewById(R.id.ingredientsRecyclerView);
        ingredientsLayoutManager = new LinearLayoutManager(this);
        ingredientsListRecyclerView.setLayoutManager(ingredientsLayoutManager);
        ingredientsListRecyclerView.hasFixedSize();

        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        Intent receivingIntent = getIntent();
        int detailRecipeid = receivingIntent.getIntExtra("RECIPE_ID", 0);


        // Livedata observer om de ingredienten op te halen
        recipeViewModel.getRecipesWithIngredients(detailRecipeid).observe(this, ingredients -> {
            myIngredients.clear();
            try {
                for(int i = 0; i < ingredients.size(); i++) {
                    ingredientList = ingredients.get(i).ingredientList;
                    for (int j = 0; j < ingredientList.size(); j++) {
                        myIngredients.add(ingredientList.get(j));
                    }
                }
                ingredientsListRecyclerViewAdapter.notifyDataSetChanged();
            } catch (IndexOutOfBoundsException e){
            }

        });
        ingredientsListRecyclerViewAdapter = new IngredientAdapter(recipeViewModel, myIngredients);
        ingredientsListRecyclerView.setAdapter(ingredientsListRecyclerViewAdapter);


        // Logica om naar de activity voor het toevoegen van een ingredient te gaan
        fab = findViewById(R.id.newIngredientButton);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent sendingIntent = new Intent(recipeIngredientsDetail.this, newIngredientActivity.class);
                sendingIntent.putExtra("RECIPE_ID", detailRecipeid);
                startActivity(sendingIntent);
            }
        });
    }
}