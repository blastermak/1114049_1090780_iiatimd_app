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

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private Recipe[] recipes;
    private MyItemClickListener recipeOnClickListener;


    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }
    public RecipeAdapter(Recipe[] recipes){
        this.recipes = recipes;
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView titleTextView;
        public TextView summaryTextView;
        public CardView recipeCardView;
        private MyItemClickListener myItemClickListener;

        public RecipeViewHolder(View v, MyItemClickListener listener){
            super(v);
            titleTextView = v.findViewById(R.id.recipeCardName);
            summaryTextView = v.findViewById(R.id.recipeCardSummary);
            recipeCardView = v.findViewById(R.id.recipeCard);
            this.myItemClickListener = listener;
            v.setOnClickListener(this);

        }

        @Override
        public void onClick(View v){
            if (myItemClickListener != null){
                myItemClickListener.onItemClick(v, getAdapterPosition());
            }
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
        holder.titleTextView.setText(recipes[position].getName());
        holder.summaryTextView.setText(recipes[position].getSummary());
    }

    @Override
    public int getItemCount(){
        return recipes.length;
    }

    public void setOnItemClickListener(MyItemClickListener listener){
        this.recipeOnClickListener = listener;
    }

}
