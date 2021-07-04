package com.example.a1114049_1090780_iiatimd_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;

public class LoginActivity extends AppCompatActivity {

    boolean authenticating;
    String database_url = "http://10.0.2.2:8000/api/login/";
    String database_url2 = "http://iiatimd.jimmak.nl:8000/api/login";
    RequestQueue queue;

//    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        queue = Volley.newRequestQueue(getApplicationContext());

        // creating user input fields and adding them as a variable
        EditText userEmail = findViewById(R.id.inputLoginEmail);
        EditText userPassword = findViewById(R.id.inputLoginPassword);

        Button backToAccountButton = findViewById(R.id.backToAccountButton2);
        Button loginButton = findViewById(R.id.loginButton);

        backToAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAccountView();
                Log.d("BackButton","clicked");
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(userEmail.getText().toString(), userPassword.getText().toString());
            }
        });

//        loginViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
    }

    private void login(String username, String password) {
        // validating the form
        boolean formIsValid = false;

        if (!username.isEmpty() && !password.isEmpty() && username.contains("@")) {
            formIsValid = true;
        }

        if (formIsValid) {
            try {
                // creating json body for the request
                authenticating = true;
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("email", username);
                jsonBody.put("password", password);

                final JSONObject[] jsonResponse = {new JSONObject()};

                // Sending the actual request
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, database_url, jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Volley", String.valueOf(response));
                        try {
                            jsonResponse[0] = response.getJSONObject("data");
                            String userToken = jsonResponse[0].getString("token");
                            Log.d("Token", userToken);
                            JSONObject user = jsonResponse[0].getJSONObject("user");
                            String username = user.getString("name");
                            Log.d("username", username);

                            // storing the token in the shared preferences
                            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("userToken", userToken);
                            editor.putString("username", username);
                            editor.apply();
                            Toast.makeText(getApplicationContext(), "Succesvol ingelogd", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        openAccountView();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Volley", error.toString());
                        Toast.makeText(getApplicationContext(), "Onjuiste inloggegevens", Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(jsonObjectRequest);
                authenticating = false;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Onjuiste logingegevens", Toast.LENGTH_SHORT).show();
        }
    }

    public void openAccountView() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("fragmentToLoad", "AccountFragment");
        startActivity(intent);
    }
}