package com.example.a1114049_1090780_iiatimd_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class recipeInstructionsDetail extends AppCompatActivity {

    private RecyclerView instructionsListRecyclerView;
    private InstructionAdapter instructionsListRecyclerViewAdapter;
    private RecyclerView.LayoutManager instructionsLayoutManager;

    private TextView instructionDescriptionTextView;
    private TextInputLayout editInstructionTextInputLayout;

    private RecipeViewModel recipeViewModel;

    private FloatingActionButton fab;
    private boolean editing = false;

    private ArrayList<Instruction> myInstructions = new ArrayList<>();
    private List<Instruction> instructionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_instructions_detail);

        instructionsListRecyclerView = findViewById(R.id.instructionsRecyclerView);
        instructionsLayoutManager = new LinearLayoutManager(this);
        instructionsListRecyclerView.setLayoutManager(instructionsLayoutManager);
        instructionsListRecyclerView.hasFixedSize();

        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        instructionsListRecyclerViewAdapter = new InstructionAdapter(recipeViewModel, myInstructions);
        instructionsListRecyclerView.setAdapter(instructionsListRecyclerViewAdapter);

        Intent receivingIntent = getIntent();
        int detailRecipeid = receivingIntent.getIntExtra("RECIPE_ID", 0);

        instructionDescriptionTextView = findViewById(R.id.instructionDescription);
        editInstructionTextInputLayout = findViewById(R.id.editInstructionTextLayout);



        recipeViewModel.getRecipesWithInstructions(detailRecipeid).observe(this, instructions -> {
            try {
                instructionList = instructions.instructionList;

                for (int i = 0; i < instructionList.size(); i++){
                    if (!myInstructions.contains(instructionList.get(i))) {
                        Log.d("instructions", instructionList.get(i).toString());
                        myInstructions.add(instructionList.get(i));
                        instructionsListRecyclerViewAdapter.notifyItemInserted(myInstructions.size() - 1);
                    }
                }

            } catch (IndexOutOfBoundsException e){
//                recipeInstructionsTextView.setText("Geen instructies gevonden!");
            }

        });

    }

}