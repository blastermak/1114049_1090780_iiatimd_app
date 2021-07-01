package com.example.a1114049_1090780_iiatimd_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class recipeDetailActivity extends AppCompatActivity {

    private TextView recipeTitleTextView;
    private TextView recipeDescriptionTextView;

    private TextInputLayout editRecipeTitleLayout;
    private TextInputLayout editRecipeDescriptionLayout;
    private TextInputEditText editRecipeTitleText;
    private TextInputEditText editRecipeDescriptionText;

    private FloatingActionButton fab;

    private RecipeViewModel recipeViewModel;
    private Recipe recipeDetail;


    private boolean editing = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        recipeTitleTextView = findViewById(R.id.recipeDetailTitle);
        recipeDescriptionTextView = findViewById(R.id.recipeDetailDescription);

        editRecipeTitleLayout = findViewById(R.id.editRecipeTitleTextLayout);
        editRecipeDescriptionLayout = findViewById(R.id.editRecipeDescriptionTextLayout);
        editRecipeTitleText = findViewById(R.id.editRecipeTitleTextEdit);
        editRecipeDescriptionText = findViewById(R.id.editRecipeDescriptionEdit);

        fab = findViewById(R.id.editRecipeButton);
        fab.setOnClickListener(this::actionButtonOnClick);

        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        Intent intent = getIntent();
        int detailRecipeid = intent.getIntExtra("RECIPE_ID", 0);

        recipeViewModel.getRecipeById(detailRecipeid).observe(this, recipe -> {
//            Log.d("recipedatail", recipe.toString());
            recipeDetail = recipe;
            recipeTitleTextView.setText(recipe.getTitle());

            recipeDescriptionTextView.setText(recipe.getDescription());

        });

    }

    public void actionButtonOnClick(View v){
        if (editing){
            recipeTitleTextView.setVisibility(View.VISIBLE);
            recipeDescriptionTextView.setVisibility(View.VISIBLE);
            editRecipeTitleLayout.setVisibility(View.GONE);
            editRecipeDescriptionLayout.setVisibility(View.GONE);

            recipeDetail.setTitle(editRecipeTitleText.getText().toString());
            recipeDetail.setDescription(editRecipeDescriptionText.getText().toString());

            recipeViewModel.update(recipeDetail);

            fab.setImageResource(R.drawable.ic_baseline_edit_24);
            editing = false;
        } else {
            recipeTitleTextView.setVisibility(View.GONE);
            recipeDescriptionTextView.setVisibility(View.GONE);
            editRecipeTitleLayout.setVisibility(View.VISIBLE);
            editRecipeDescriptionLayout.setVisibility(View.VISIBLE);

            editRecipeTitleText.setText(recipeDetail.getTitle());
            editRecipeDescriptionText.setText(recipeDetail.getDescription());


            fab.setImageResource(R.drawable.ic_baseline_check_24);
            editing = true;
        }
    }
}