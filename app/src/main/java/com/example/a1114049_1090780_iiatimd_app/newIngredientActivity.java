package com.example.a1114049_1090780_iiatimd_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

// Activity om nieuwe ingredienten toe te voegen bij een specifiek recept

public class newIngredientActivity extends AppCompatActivity {

    private TextInputLayout newNameLayout;
    private TextInputLayout newAmountLayout;

    private Button submitIngredientButton;

    private RecipeViewModel recipeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ingredient);

        newNameLayout = findViewById(R.id.newInstructionDescriptionLayout);
        newAmountLayout = findViewById(R.id.newIngredientAmountLayout);

        Intent receivingIntent = getIntent();
        int detailRecipeid = receivingIntent.getIntExtra("RECIPE_ID", 0);

        submitIngredientButton = findViewById(R.id.submitNewIngredientButton);
        submitIngredientButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Validatie en fouthandeling voor bij de invoervelden
                boolean formValidated = false;
                if (TextUtils.isEmpty(String.valueOf(newNameLayout.getEditText().getText()))) {
                    newNameLayout.setError("Je hebt geen naam ingevuld");
                    formValidated = false;
                } else {
                    formValidated = true;
                }
                if (TextUtils.isEmpty(String.valueOf(newAmountLayout.getEditText().getText()))) {
                    newAmountLayout.setError("Je hebt geen hoeveelheid ingevuld");
                    formValidated = false;
                } else {
                    formValidated = true;
                }
                if (formValidated) {
                    // Request om de ingevulde data naar de database te sturen
                    try {
                        String URL = "http://iiatimd.jimmak.nl/api/recipes/" + detailRecipeid + "/ingredients";
                        JSONObject jsonBody = new JSONObject();
                        jsonBody.put("name", String.valueOf(newNameLayout.getEditText().getText()));
                        jsonBody.put("amount", String.valueOf(newAmountLayout.getEditText().getText()));

                        final JSONObject[] jsonResponse = {new JSONObject()};

                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.i("VOLLEY", String.valueOf(response));
                                try {
                                    jsonResponse[0] = response.getJSONObject("data");
                                    recipeViewModel.insertIngredient(
                                            new Ingredient(
                                                    jsonResponse[0].getInt("id"),
                                                    jsonResponse[0].getInt("recipe_id"),
                                                    jsonResponse[0].getString("name"),
                                                    jsonResponse[0].getString("amount")
                                            ));
                                    finish();
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

                        VolleySingleton.getInstance(v.getContext()).addToRequestQueue(jsonObjectRequest);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

    }

}