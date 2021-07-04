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

// Activity om de ingrediënten van een recept weer te geven

public class recipeInstructionsDetail extends AppCompatActivity {

    private RecyclerView instructionsListRecyclerView;
    private InstructionAdapter instructionsListRecyclerViewAdapter;
    private RecyclerView.LayoutManager instructionsLayoutManager;

    private RecipeViewModel recipeViewModel;

    private ArrayList<Instruction> myInstructions = new ArrayList<>();
    private List<Instruction> instructionList;

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_instructions_detail);

        instructionsListRecyclerView = findViewById(R.id.instructionsRecyclerView);
        instructionsLayoutManager = new LinearLayoutManager(this);
        instructionsListRecyclerView.setLayoutManager(instructionsLayoutManager);
        instructionsListRecyclerView.hasFixedSize();

        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        Intent receivingIntent = getIntent();
        int detailRecipeid = receivingIntent.getIntExtra("RECIPE_ID", 0);

        // Livedata observer om de instructies op te halen
        recipeViewModel.getRecipesWithInstructions(detailRecipeid).observe( this, instructions -> {
            myInstructions.clear();
            try {

                for (int i = 0; i < instructions.size(); i++) {
                    instructionList = instructions.get(i).instructionList;
                    for (int j = 0; j < instructionList.size(); j ++){
                        myInstructions.add(instructionList.get(j));
                    }

                }
                instructionsListRecyclerViewAdapter.notifyDataSetChanged();
            } catch (IndexOutOfBoundsException e){

            }

        });

        instructionsListRecyclerViewAdapter = new InstructionAdapter(recipeViewModel, myInstructions);
        instructionsListRecyclerView.setAdapter(instructionsListRecyclerViewAdapter);

        // Logica om naar de activity voor het toevoegen van een instructie te gaan
        fab = findViewById(R.id.newInstructionButton);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent sendingIntent = new Intent(recipeInstructionsDetail.this, newInstructionActivity.class);
                sendingIntent.putExtra("RECIPE_ID", detailRecipeid);
                startActivity(sendingIntent);
            }
        });

    }
}