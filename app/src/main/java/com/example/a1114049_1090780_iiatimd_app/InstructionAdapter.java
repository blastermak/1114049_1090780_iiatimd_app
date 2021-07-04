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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

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

        public ImageButton editButton;

        public InstructionViewHolder(View v){
            super(v);

            descriptionTextView = v.findViewById(R.id.instructionDescription);
            editInstructionTextInputLayout = v.findViewById(R.id.editInstructionTextLayout);
            editInstructionDescriptionTextEdit = v.findViewById(R.id.editInstructionDescriptionTextEdit);

            editButton = v.findViewById(R.id.editInstructionButton);

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
        holder.editInstructionTextInputLayout.getEditText().setText(instructions.get(position).getDescription());

        Instruction thisInstruction = instructions.get(position);

        holder.editButton.setOnClickListener(new View.OnClickListener(){
            boolean editing = false;
            String oldDescription = thisInstruction.getDescription();
            @Override
            public void onClick(View v){
                if (editing){
                    holder.descriptionTextView.setVisibility(View.VISIBLE);
                    holder.editInstructionTextInputLayout.setVisibility(View.GONE);

                    if (holder.editInstructionDescriptionTextEdit.getText().toString() != oldDescription ){
                        holder.descriptionTextView.setText(holder.editInstructionDescriptionTextEdit.getText().toString());
                        thisInstruction.setDescription(holder.editInstructionDescriptionTextEdit.getText().toString());
                        viewModel.updateInstruction(instructions.get(position));
                        sendToServer(thisInstruction, holder.itemView);
                    }
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

    public void sendToServer(Instruction instruction, View v){
        try {
            String URL = "http://iiatimd.jimmak.nl/api/instructions/" + instruction.getUuid();
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("step_number", instruction.getStep_number());
            jsonBody.put("description", instruction.getDescription());

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Snackbar snackbar = Snackbar.make(v, response.getString("message"), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            });

            VolleySingleton.getInstance(viewModel.getApplication()).addToRequestQueue(jsonObjectRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
