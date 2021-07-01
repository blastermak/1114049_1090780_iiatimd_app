package com.example.a1114049_1090780_iiatimd_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.android.material.textfield.TextInputLayout;
public class recipeDetailActivity extends AppCompatActivity {

    private TextView recipeTitleTextView;
    private TextView recipeDescriptionTextView;

    private TextInputLayout editRecipeTitleLayout;
    private TextInputLayout editRecipeDescriptionLayout;

    private FloatingActionButton fab;


    private AppDatabase db;


    private boolean editing = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        recipeTitleTextView = findViewById(R.id.recipeDetailTitle);
        recipeDescriptionTextView = findViewById(R.id.recipeDetailDescription);

        editRecipeTitleLayout = findViewById(R.id.editRecipeTitleTextLayout);
        editRecipeDescriptionLayout = findViewById(R.id.editRecipeDescriptionTextLayout);

        fab = findViewById(R.id.editRecipeButton);
        fab.setOnClickListener(this::actionButtonOnClick);

        db = AppDatabase.getInstance(recipeDetailActivity.this);

        Intent intent = getIntent();
        int detailRecipeid = intent.getIntExtra("RECIPE_ID", 0);
        String detailRecipeTitle = intent.getStringExtra("RECIPE_TITLE");
        String detailRecipeDescriptionShort = intent.getStringExtra("RECIPE_DESCRIPTION_SHORT");
        String detailRecipeDescription = intent.getStringExtra("RECIPE_DESCRIPTION");
        int detailRecipePrepTimeMin = intent.getIntExtra("RECIPE_PREP_TIME_MIN", 0);

        recipeTitleTextView.setText(detailRecipeTitle);
        recipeDescriptionTextView.setText(detailRecipeDescription);

    }


    public void actionButtonOnClick(View v){
        if (editing){
            recipeTitleTextView.setVisibility(View.VISIBLE);
            recipeDescriptionTextView.setVisibility(View.VISIBLE);
            editRecipeTitleLayout.setVisibility(View.GONE);
            editRecipeDescriptionLayout.setVisibility(View.GONE);

            fab.setImageResource(R.drawable.ic_baseline_edit_24);
            editing = false;
        } else {
            recipeTitleTextView.setVisibility(View.GONE);
            recipeDescriptionTextView.setVisibility(View.GONE);
            editRecipeTitleLayout.setVisibility(View.VISIBLE);
            editRecipeDescriptionLayout.setVisibility(View.VISIBLE);

            fab.setImageResource(R.drawable.ic_baseline_check_24);
            editing = true;
        }
    }
}