package com.example.a1114049_1090780_iiatimd_app;

import androidx.appcompat.app.AppCompatActivity;

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
                String URL = "http://iiatimd.jimmak.nl/api/recipes";
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("title", String.valueOf(recipeTitleText.getText()));
                jsonBody.put("description_short", String.valueOf(recipeShortDescriptionText.getText()));
                jsonBody.put("description", String.valueOf(recipeDescriptionText.getText()));
                jsonBody.put("prep_time_min", String.valueOf(recipePrepTimeMin.getText()));
                final String requestBody = jsonBody.toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("VOLLEY", response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.toString());
                    }
                }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                            return null;
                        }
                    }

                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null) {
                            responseString = String.valueOf(response.statusCode);
                            Log.d("volley", String.valueOf(response.data));
                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));

                    }
                };

                requestQueue.add(stringRequest);
                finish();
                Toast.makeText(this.getApplicationContext(), "Recept succesvol toegevoegd!", Toast.LENGTH_SHORT).show();


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}