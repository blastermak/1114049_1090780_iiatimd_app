package com.example.a1114049_1090780_iiatimd_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;

public class newRecipeActivity extends AppCompatActivity {

    private TextInputEditText recipeTitleText;
    private TextInputEditText recipeShortDescriptionText;
    private TextInputEditText recipeDescriptionText;
    private TextInputEditText recipePrepTimeMin;

    private int titleCharacterAmount = 20;
    private int shortDescriptionCharacterAmount = 50;
    private int descriptionCharacterAmount = 500;
    private int prepTimeMinCharacterAmount = 3;

    private Button recipeSubmitButton;

    private boolean formValidated = false;

    private AppDatabase db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);

        recipeTitleText = findViewById(R.id.editRecipeTitleEditText);

        recipeShortDescriptionText = findViewById(R.id.newRecipeShortDescriptionEditText);
        recipeDescriptionText = findViewById(R.id.newRecipeDescriptionEditText);
        recipePrepTimeMin = findViewById(R.id.newRecipePrepTimeMinEditText);

        recipeSubmitButton = findViewById(R.id.submitNewRecipeButton);

        recipeSubmitButton.setOnClickListener(this::newRecipeSubmitHandler);

        db = AppDatabase.getInstance(newRecipeActivity.this);

    }

    public void newRecipeSubmitHandler(View view){
        if (TextUtils.isEmpty(String.valueOf(recipeTitleText.getText()))) {
            recipeTitleText.setError("Je hebt geen titel ingevuld");
            formValidated = false;
        } else {
            formValidated = true;
        }
        if (TextUtils.isEmpty(String.valueOf(recipeShortDescriptionText.getText()))) {
            recipeShortDescriptionText.setError("Je hebt geen korte beschrijving ingevuld");
            formValidated = false;
        } else {
            formValidated = true;
        }
        if (TextUtils.isEmpty(String.valueOf(recipeDescriptionText.getText()))) {
            recipeDescriptionText.setError("Je hebt geen beschrijving ingevuld");
            formValidated = false;
        } else {
            formValidated = true;
        }
        if (TextUtils.isEmpty(String.valueOf(recipePrepTimeMin.getText()))) {
            recipePrepTimeMin.setError("Je hebt geen bereidingstijd ingevuld");
            formValidated = false;
        } else {
            formValidated = true;
        }

        if (formValidated) {
            try {
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                String URL = "http://iiatimd.jimmak.nl/api/recipe";
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("title", String.valueOf(recipeTitleText.getText()));
                jsonBody.put("description_short", String.valueOf(recipeShortDescriptionText.getText()));
                jsonBody.put("description", String.valueOf(recipeDescriptionText.getText()));
                jsonBody.put("prep_time_min", String.valueOf(recipePrepTimeMin.getText()));

                final JSONObject[] jsonResponse = {new JSONObject()};

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URL, jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("VOLLEY", String.valueOf(response));
                        try {
                            jsonResponse[0] = response.getJSONObject("data");
                            db.recipeDAO().InsertRecipe(
                                    new Recipe(
                                            jsonResponse[0].getInt("id"),
                                            jsonResponse[0].getString("title"),
                                            jsonResponse[0].getString("description_short"),
                                            jsonResponse[0].getString("description"),
                                            jsonResponse[0].getInt("prep_time_min")
                                    ));
                            Intent intent = new Intent(newRecipeActivity.this, recipeDetailActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("RECIPE_ID", jsonResponse[0].getInt("id"));
                            intent.putExtra("RECIPE_TITLE", jsonResponse[0].getString("title"));
                            intent.putExtra("RECIPE_DESCRIPTION_SHORT", jsonResponse[0].getString("description_short"));
                            intent.putExtra("RECIPE_DESCRIPTION", jsonResponse[0].getString("description"));
                            intent.putExtra("RECIPE_PREP_TIME_MIN", jsonResponse[0].getInt("prep_time_min"));
                            // Finish is needed so that we don't see the new recipe screen when pressing back
                            finish();
                            startActivity(intent);
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

                VolleySingleton.getInstance(view.getContext()).addToRequestQueue(jsonObjectRequest);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}