package com.example.a1114049_1090780_iiatimd_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

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

    private RecyclerView instructionsListRecyclerView;
    private InstructionAdapter instructionsListRecyclerViewAdapter;
    private RecyclerView.LayoutManager instructionsLayoutManager;

    private RecyclerView ingredientsListRecyclerView;
    private IngredientAdapter ingredientsListRecyclerViewAdapter;
    private RecyclerView.LayoutManager ingredientsLayoutManager;

    private ArrayList<Instruction> myInstructions = new ArrayList<>();
    private ArrayList<Ingredient> myIngredients = new ArrayList<>();

    private boolean editing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        recipeTitleTextView = findViewById(R.id.recipeDetailTitle);
        recipeDescriptionTextView = findViewById(R.id.recipeDetailDescription);

        instructionsListRecyclerView = findViewById(R.id.instructionsRecyclerView);
        instructionsLayoutManager = new LinearLayoutManager(this);
        instructionsListRecyclerView.setLayoutManager(instructionsLayoutManager);
        instructionsListRecyclerView.hasFixedSize();

        instructionsListRecyclerViewAdapter = new InstructionAdapter(myInstructions);
        instructionsListRecyclerView.setAdapter(instructionsListRecyclerViewAdapter);

        ingredientsListRecyclerView = findViewById(R.id.ingredientsRecyclerView);
        ingredientsLayoutManager = new LinearLayoutManager(this);
        ingredientsListRecyclerView.setLayoutManager(ingredientsLayoutManager);
        ingredientsListRecyclerView.hasFixedSize();

        ingredientsListRecyclerViewAdapter = new IngredientAdapter(myIngredients);
        ingredientsListRecyclerView.setAdapter(ingredientsListRecyclerViewAdapter);

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

        recipeViewModel.getRecipesWithIngredients(detailRecipeid).observe(this, ingredients -> {
            try {
//                Log.d("instructions", instructions.get(0).toString());
                List<Ingredient> ingredientList = ingredients.ingredientList;
                for (int i = 0; i < ingredientList.size(); i++){
                    myIngredients.add(ingredientList.get(i));
                    ingredientsListRecyclerViewAdapter.notifyItemInserted(myIngredients.size()-1);
                }
//                List<Instruction> instructionList = instructions.get(0).instructionList;
//                recipeInstructionsTextView.setText(instructionList.get(0).getDescription());
            } catch (IndexOutOfBoundsException e){
//                recipeInstructionsTextView.setText("Geen instructies gevonden!");
            }

        });

        recipeViewModel.getRecipesWithInstructions(detailRecipeid).observe(this, instructions -> {
            try {
//                Log.d("instructions", instructions.get(0).toString());
                List<Instruction> instructionList = instructions.instructionList;
                for (int i = 0; i < instructionList.size(); i++){
                    myInstructions.add(instructionList.get(i));
                    instructionsListRecyclerViewAdapter.notifyItemInserted(myInstructions.size()-1);
                }
//                List<Instruction> instructionList = instructions.get(0).instructionList;
//                recipeInstructionsTextView.setText(instructionList.get(0).getDescription());
            } catch (IndexOutOfBoundsException e){
//                recipeInstructionsTextView.setText("Geen instructies gevonden!");
            }

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

            recipeViewModel.updateRecipe(recipeDetail);

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