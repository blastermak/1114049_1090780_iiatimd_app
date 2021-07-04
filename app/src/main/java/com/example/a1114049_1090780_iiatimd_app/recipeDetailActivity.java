package com.example.a1114049_1090780_iiatimd_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

// Activity voor het weergeven van receptendetails

public class recipeDetailActivity extends AppCompatActivity {

    private TextView recipeTitleTextView;
    private TextView recipeDescriptionTextView;

    private TextInputLayout editRecipeTitleLayout;
    private TextInputLayout editRecipeDescriptionLayout;
    private TextInputEditText editRecipeTitleText;
    private TextInputEditText editRecipeDescriptionText;

    private FloatingActionButton fab;
    private Button ingredientsButton;
    private Button instructionsButton;

    private RecipeViewModel recipeViewModel;
    private Recipe recipeDetail;


    private boolean editing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        recipeTitleTextView = findViewById(R.id.recipeDetailTitle);
        recipeDescriptionTextView = findViewById(R.id.recipeDetailDescription);

        editRecipeTitleLayout = findViewById(R.id.editInstructionTextLayout);
        editRecipeDescriptionLayout = findViewById(R.id.editRecipeDescriptionTextLayout);
        editRecipeTitleText = findViewById(R.id.editRecipeTitleTextEdit);
        editRecipeDescriptionText = findViewById(R.id.editRecipeDescriptionEdit);

        fab = findViewById(R.id.editRecipeButton);
        fab.setOnClickListener(this::actionButtonOnClick);

        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        Intent receivingIntent = getIntent();
        int detailRecipeid = receivingIntent.getIntExtra("RECIPE_ID", 0);

        recipeViewModel.getRecipeById(detailRecipeid).observe(this, recipe -> {
            recipeDetail = recipe;
            recipeTitleTextView.setText(recipe.getTitle());
            recipeDescriptionTextView.setText(recipe.getDescription());
        });

        ingredientsButton = findViewById(R.id.goToIngredientsButton);
        ingredientsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent sendingIntent = new Intent(recipeDetailActivity.this, recipeIngredientsDetail.class);
                sendingIntent.putExtra("RECIPE_ID", detailRecipeid);
                startActivity(sendingIntent);
            }
        });

        instructionsButton = findViewById(R.id.goToInstructionsButton);
        instructionsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent sendingIntent = new Intent(recipeDetailActivity.this, recipeInstructionsDetail.class);
                sendingIntent.putExtra("RECIPE_ID", detailRecipeid);
                startActivity(sendingIntent);
            }
        });

    }

    public void actionButtonOnClick(View v){

        // Zorgt ervoor dat de relevante velden verdwijnen en verschijnen wanneer er bewerkt wordt
        if (editing){
            recipeTitleTextView.setVisibility(View.VISIBLE);
            recipeDescriptionTextView.setVisibility(View.VISIBLE);
            editRecipeTitleLayout.setVisibility(View.GONE);
            editRecipeDescriptionLayout.setVisibility(View.GONE);

            ingredientsButton.setVisibility(View.VISIBLE);
            instructionsButton.setVisibility(View.VISIBLE);

            recipeDetail.setTitle(editRecipeTitleText.getText().toString());
            recipeDetail.setDescription(editRecipeDescriptionText.getText().toString());

            recipeViewModel.updateRecipe(recipeDetail);

            fab.setImageResource(R.drawable.ic_baseline_edit_24);
            editing = false;
        } else {
            recipeTitleTextView.setVisibility(View.GONE);
            recipeDescriptionTextView.setVisibility(View.GONE);
            editRecipeTitleLayout.setVisibility(View.VISIBLE);
            editRecipeDescriptionLayout.setVisibility(View.VISIBLE);

            ingredientsButton.setVisibility(View.GONE);
            instructionsButton.setVisibility(View.GONE);

            editRecipeTitleText.setText(recipeDetail.getTitle());
            editRecipeDescriptionText.setText(recipeDetail.getDescription());


            fab.setImageResource(R.drawable.ic_baseline_check_24);
            editing = true;
        }
    }
}