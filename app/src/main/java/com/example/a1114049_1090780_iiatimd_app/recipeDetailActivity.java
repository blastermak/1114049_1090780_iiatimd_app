package com.example.a1114049_1090780_iiatimd_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class recipeDetailActivity extends AppCompatActivity {

    private TextView recipeTitle;
    private TextView recipeDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        recipeTitle = findViewById(R.id.recipeDetailTitle);
        recipeDescription = findViewById(R.id.recipeDetailDescription);

        Intent intent = getIntent();
        recipeTitle.setText(intent.getStringExtra("RECIPE_TITLE"));
        recipeDescription.setText(intent.getStringExtra("RECIPE_DESCRIPTION"));


    }
}