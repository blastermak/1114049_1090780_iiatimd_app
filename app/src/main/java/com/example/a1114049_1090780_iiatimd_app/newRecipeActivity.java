package com.example.a1114049_1090780_iiatimd_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

public class newRecipeActivity extends AppCompatActivity {

    private TextInputEditText recipeTitleText;
    private TextInputEditText recipeShortDescriptionText;
    private TextInputEditText recipeDescriptionText;
    private TextInputEditText recipePrepTimeMin;

    private Button recipeSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);

        recipeTitleText = findViewById(R.id.editRecipeTitleEditText);
        recipeShortDescriptionText = findViewById(R.id.newRecipeShortDescriptionEditText);
        recipeDescriptionText = findViewById(R.id.newRecipeDescriptionEditText);
        recipePrepTimeMin = findViewById(R.id.newRecipePrepTimeMinEditText);

        recipeSubmitButton = findViewById(R.id.submitNewRecipeButton);

    }

    public void newRecipeSubmitHandler(View view){

    }
}