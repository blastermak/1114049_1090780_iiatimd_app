package com.example.a1114049_1090780_iiatimd_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InstructionAdapter extends RecyclerView.Adapter<InstructionAdapter.InstructionViewHolder>{

    private List<Instruction> instructions;

    public InstructionAdapter(List<Instruction> instructions){
        this.instructions = instructions;
    }

    public static class InstructionViewHolder extends RecyclerView.ViewHolder {

        public TextView descriptionTextView;

        public InstructionViewHolder(View v){
            super(v);

            descriptionTextView = v.findViewById(R.id.instructionDescription);
        }
    }

    @NonNull
    @Override
    public InstructionAdapter.InstructionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.instruction_card, parent, false);
        InstructionViewHolder instructionViewHolder = new InstructionViewHolder(v);

        return instructionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionAdapter.InstructionViewHolder holder, int position){

        holder.descriptionTextView.setText(instructions.get(position).getDescription());

    }

    @Override
    public int getItemCount(){
        return instructions.size();
    }
}
