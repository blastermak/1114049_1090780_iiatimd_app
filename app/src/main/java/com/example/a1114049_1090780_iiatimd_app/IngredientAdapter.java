package com.example.a1114049_1090780_iiatimd_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>{

    private List<Ingredient> ingredients;

    public IngredientAdapter(List<Ingredient> ingredients){
        this.ingredients = ingredients;
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {

        public TextView ingredientNameTextView;
        public TextView ingredientAmountTextView;

        public IngredientViewHolder(View v){
            super(v);

            ingredientNameTextView = v.findViewById(R.id.instructionDescription);
            ingredientAmountTextView = v.findViewById(R.id.ingredientAmount);
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

    }

    @Override
    public int getItemCount(){
        return ingredients.size();
    }
}
