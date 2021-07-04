package com.example.a1114049_1090780_iiatimd_app;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

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

        Log.d("ingredienstAdatpter", thisIngredient.getName());

        holder.editButton.setOnClickListener(new View.OnClickListener(){
            boolean editing = false;
            @Override
            public void onClick(View v){
                if (editing){
                    holder.ingredientNameTextView.setVisibility(View.VISIBLE);
                    holder.ingredientAmountTextView.setVisibility(View.VISIBLE);

                    holder.nameInputLayout.setVisibility(View.GONE);
                    holder.amountInputLayout.setVisibility(View.GONE);

                    thisIngredient.setName(holder.nameInputLayout.getEditText().getText().toString());
                    thisIngredient.setAmount(holder.amountInputLayout.getEditText().getText().toString());

                    viewModel.updateIngredient(thisIngredient);
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
}
