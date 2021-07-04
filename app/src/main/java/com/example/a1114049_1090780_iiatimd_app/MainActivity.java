package com.example.a1114049_1090780_iiatimd_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    // Variable for the bottom navigation view
    private BottomNavigationView bottomNavigationView;

    private FloatingActionButton fab;

    private RecipeViewModel recipeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the bottomnavigationview
        // Setting the correct listener for it
        // and open the homefragment
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        openFragment(HomeFragment.newInstance("","" ));
        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        fab = findViewById(R.id.addRecipeActionButton);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, newRecipeActivity.class));
            }
        });

        JsonObjectRequest recipeJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://iiatimd.jimmak.nl/api/recipes/",null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray recipeData = response.getJSONArray("data");
                    for (int i = 0; i < recipeData.length(); i++){
                        Recipe recipe = new Recipe (
                                        recipeData.getJSONObject(i).getInt("id"),
                                        recipeData.getJSONObject(i).getString("title"),
                                        recipeData.getJSONObject(i).getString("description_short"),
                                        recipeData.getJSONObject(i).getString("description"),
                                        recipeData.getJSONObject(i).getInt("prep_time_min")
                                );
                        recipeViewModel.insertRecipe(recipe);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("gefaald", error.getMessage());
            }
        });

        JsonObjectRequest instructionsJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://iiatimd.jimmak.nl/api/instructions/",null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray instructionData = response.getJSONArray("data");
                    for (int i = 0; i < instructionData.length(); i++){
                        Instruction instruction = new Instruction (
                                instructionData.getJSONObject(i).getInt("id"),
                                instructionData.getJSONObject(i).getInt("recipe_id"),
                                instructionData.getJSONObject(i).getInt("step_number"),
                                instructionData.getJSONObject(i).getString("description")
                        );
                        recipeViewModel.insertInstruction(instruction);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("gefaald", error.getMessage());
            }
        });

        JsonObjectRequest ingredientsJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://iiatimd.jimmak.nl/api/ingredients/",null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray ingredientsData = response.getJSONArray("data");
                    for (int i = 0; i < ingredientsData.length(); i++){
                        Ingredient ingredient = new Ingredient (
                                ingredientsData.getJSONObject(i).getInt("id"),
                                ingredientsData.getJSONObject(i).getInt("recipe_id"),
                                ingredientsData.getJSONObject(i).getString("name"),
                                ingredientsData.getJSONObject(i).getString("amount")
                        );
                        recipeViewModel.insertIngredient(ingredient);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("gefaald", error.getMessage());
            }
        });

        VolleySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(recipeJsonObjectRequest);
        VolleySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(instructionsJsonObjectRequest);
        VolleySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(ingredientsJsonObjectRequest);

    }

    // Method for opening fragment in the correct container
    public void openFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    // Listener for the bottomnavigationview
    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
        new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.bottom_menu_page_home:
                        openFragment(HomeFragment.newInstance("",""));
                        return true;
                    case R.id.bottom_menu_page_list:
                        openFragment(ListFragment.newInstance("",""));
                        return true;
                    case R.id.bottom_menu_page_search:
                        openFragment(SearchFragment.newInstance("",""));
                        return true;
                    case R.id.bottom_menu_page_account:
                        openFragment(AccountFragment.newInstance("",""));
                        return true;
                }
                return false;
            }
        };
}