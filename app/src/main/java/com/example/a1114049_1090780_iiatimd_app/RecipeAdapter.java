package com.example.a1114049_1090780_iiatimd_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>  {

    private ArrayList<Recipe> recipes;
    private recipeItemClickListener recipeOnClickListener;

    public interface recipeItemClickListener {
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(recipeItemClickListener listener){
        recipeOnClickListener = listener;
    }

    public RecipeAdapter(ArrayList<Recipe> recipes){
        this.recipes = recipes;
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView summaryTextView;
        public CardView recipeCardView;
        private recipeItemClickListener myItemClickListener;

        public RecipeViewHolder(View v, recipeItemClickListener listener){
            super(v);
            titleTextView = v.findViewById(R.id.recipeCardName);
            summaryTextView = v.findViewById(R.id.recipeCardSummary);
            recipeCardView = v.findViewById(R.id.recipeCard);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(v, position);
                        }
                    }
                }
            });
        }

    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_card, parent, false);
        RecipeViewHolder recipeViewHolder = new RecipeViewHolder(v, recipeOnClickListener);

        return recipeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.RecipeViewHolder holder, int position){
        holder.titleTextView.setText(recipes.get(position).getTitle());
        holder.summaryTextView.setText(recipes.get(position).getDescription_short());
    }

    @Override
    public int getItemCount(){
        return recipes.size();
    }


}
