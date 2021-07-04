package com.example.a1114049_1090780_iiatimd_app;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>{

    private RecipeViewModel viewModel;
    private List<Ingredient> ingredients;

    public IngredientAdapter(RecipeViewModel viewModel, List<Ingredient> ingredients){
        this.viewModel = viewModel;
        this.ingredients = ingredients;
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {

        public TextView ingredientNameTextView;
        public TextView ingredientAmountTextView;

        public TextInputLayout nameInputLayout;
        public TextInputLayout amountInputLayout;

        public ImageButton editButton;

        public IngredientViewHolder(View v){
            super(v);

            ingredientNameTextView = v.findViewById(R.id.instructionDescription);
            ingredientAmountTextView = v.findViewById(R.id.ingredientAmount);

            nameInputLayout = v.findViewById(R.id.ingredientNameInputLayout);
            amountInputLayout = v.findViewById(R.id.ingredientAmountInputLayout);

            editButton = v.findViewById(R.id.editIngredientsButton);

        }
    }

    @NonNull
    @Override
    public IngredientAdapter.IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_card, parent, false);
        IngredientViewHolder ingredientViewHolder = new IngredientViewHolder(v);

        return ingredientViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.IngredientViewHolder holder, int position){

        holder.ingredientNameTextView.setText(ingredients.get(position).getName());
        holder.ingredientAmountTextView.setText(ingredients.get(position).getAmount());

        holder.nameInputLayout.getEditText().setText(ingredients.get(position).getName());
        holder.amountInputLayout.getEditText().setText(ingredients.get(position).getAmount());

        Ingredient thisIngredient = ingredients.get(position);

        holder.editButton.setOnClickListener(new View.OnClickListener(){
            boolean editing = false;
            String oldName = thisIngredient.getName();
            String oldAmount = thisIngredient.getAmount();
            @Override
            public void onClick(View v){
                if (editing){
                    holder.ingredientNameTextView.setVisibility(View.VISIBLE);
                    holder.ingredientAmountTextView.setVisibility(View.VISIBLE);

                    holder.nameInputLayout.setVisibility(View.GONE);
                    holder.amountInputLayout.setVisibility(View.GONE);

                    if (holder.nameInputLayout.getEditText().getText().toString() != oldName &&
                            holder.amountInputLayout.getEditText().getText().toString() == oldAmount ){
                        thisIngredient.setName(holder.nameInputLayout.getEditText().getText().toString());
                        viewModel.updateIngredient(thisIngredient);
                        sendToServer(thisIngredient, holder.itemView);
                    } else if (holder.nameInputLayout.getEditText().getText().toString() == oldName &&
                            holder.amountInputLayout.getEditText().getText().toString() != oldAmount ){
                        thisIngredient.setAmount(holder.amountInputLayout.getEditText().getText().toString());
                        viewModel.updateIngredient(thisIngredient);
                        sendToServer(thisIngredient, holder.itemView);
                    } else if (holder.nameInputLayout.getEditText().getText().toString() != oldName &&
                            holder.amountInputLayout.getEditText().getText().toString() != oldAmount ){
                        thisIngredient.setName(holder.nameInputLayout.getEditText().getText().toString());
                        thisIngredient.setAmount(holder.amountInputLayout.getEditText().getText().toString());
                        viewModel.updateIngredient(thisIngredient);
                        sendToServer(thisIngredient, holder.itemView);
                    }
                    editing = false;
                } else {
                    holder.ingredientNameTextView.setVisibility(View.GONE);
                    holder.ingredientAmountTextView.setVisibility(View.GONE);

                    holder.nameInputLayout.setVisibility(View.VISIBLE);
                    holder.amountInputLayout.setVisibility(View.VISIBLE);

                    editing = true;
                }
            }
        });

    }

    @Override
    public int getItemCount(){
        return ingredients.size();
    }

    public void sendToServer(Ingredient ingredient, View v){
        try {
            String URL = "http://iiatimd.jimmak.nl/api/ingredients/" + ingredient.getUuid();
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("name", ingredient.getName());
            jsonBody.put("amount", ingredient.getAmount());

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
