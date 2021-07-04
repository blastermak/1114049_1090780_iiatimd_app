package com.example.a1114049_1090780_iiatimd_app;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class InstructionAdapter extends RecyclerView.Adapter<InstructionAdapter.InstructionViewHolder>{

    private RecipeViewModel viewModel;
    private List<Instruction> instructions;

    public InstructionAdapter(RecipeViewModel viewModel, List<Instruction> instructions){
        this.viewModel = viewModel;
        this.instructions = instructions;
    }

    public static class InstructionViewHolder extends RecyclerView.ViewHolder {

        public TextView descriptionTextView;
        public TextInputLayout editInstructionTextInputLayout;
        public TextInputEditText editInstructionDescriptionTextEdit;

        public Instruction showingInstruction;
        private RecipeViewModel recipeViewModel;

        public ImageButton editButton;
        private boolean editing = false;

        public InstructionViewHolder(View v, RecipeViewModel viewModel){
            super(v);

            descriptionTextView = v.findViewById(R.id.instructionDescription);
            editInstructionTextInputLayout = v.findViewById(R.id.editInstructionTextLayout);
            editInstructionDescriptionTextEdit = v.findViewById(R.id.editInstructionDescriptionTextEdit);

            editButton = v.findViewById(R.id.editInstructionButton);

            this.recipeViewModel = viewModel;

//            this.showingInstruction = viewModel.getInstructionById()


        }
    }

    @NonNull
    @Override
    public InstructionAdapter.InstructionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.instruction_card, parent, false);
        InstructionViewHolder instructionViewHolder = new InstructionViewHolder(v, this.viewModel);

        return instructionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionAdapter.InstructionViewHolder holder, int position){

        holder.descriptionTextView.setText(instructions.get(position).getDescription());
        holder.editInstructionTextInputLayout.getEditText().setText(instructions.get(position).getDescription());


//        holder.showingInstruction = instructions.get(position);

        Instruction thisInstruciton = instructions.get(position);


        holder.editButton.setOnClickListener(new View.OnClickListener(){
            boolean editing = false;
            @Override
            public void onClick(View v){
                if (editing){
                    holder.descriptionTextView.setVisibility(View.VISIBLE);
                    holder.editInstructionTextInputLayout.setVisibility(View.GONE);

                    holder.descriptionTextView.setText(holder.editInstructionDescriptionTextEdit.getText().toString());
                    thisInstruciton.setDescription(holder.editInstructionDescriptionTextEdit.getText().toString());

                    viewModel.updateInstruction(instructions.get(position));
                    editing = false;
                } else {
                    holder.descriptionTextView.setVisibility(View.GONE);
                    holder.editInstructionTextInputLayout.setVisibility(View.VISIBLE);

                    editing = true;
                }
            }
        });

    }

    @Override
    public int getItemCount(){
        return instructions.size();
    }
}
