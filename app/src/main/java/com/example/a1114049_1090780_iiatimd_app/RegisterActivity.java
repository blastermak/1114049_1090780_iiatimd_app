package com.example.a1114049_1090780_iiatimd_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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

public class RegisterActivity extends AppCompatActivity {

    boolean authenticating;
    String database_url = "http://10.0.2.2:8000/api/register";
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        queue = Volley.newRequestQueue(getApplicationContext());

        EditText userFullName = findViewById(R.id.inputFullName);
        EditText userEmail = findViewById(R.id.inputRegisterEmail);
        EditText userPassword = findViewById(R.id.inputRegisterPassword);
        EditText userConfirmPassword = findViewById(R.id.inputRegisterConfirmPassword);

        Button backToAccountButton = findViewById(R.id.backToAccountButton);
        Button registerButton = findViewById(R.id.registerButton);

        backToAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAccountView();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(userFullName.getText().toString(), userEmail.getText().toString(), userPassword.getText().toString(), userConfirmPassword.getText().toString());
            }
        });

    }

    private void register(String username, String email, String password, String confirmPassword) {
        boolean formIsValid = false;

        if (!username.isEmpty() && !email.isEmpty() && email.contains("@") && !password.isEmpty() && !confirmPassword.isEmpty() && password.equals(confirmPassword)) {
            formIsValid = true;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(getApplicationContext(), "Wachtwoorden komen niet overeen", Toast.LENGTH_SHORT).show();
        }

        if (formIsValid) {
            try {
                authenticating = true;
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("name", username);
                jsonBody.put("email", email);
                jsonBody.put("password", password);

                final JSONObject[] jsonResponse = {new JSONObject()};

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, database_url, jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("VolleyResponse", String.valueOf(response));
                        try {
                            jsonResponse[0] = response.getJSONObject("data");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getApplicationContext(), "Aanmaken gelukt", Toast.LENGTH_SHORT).show();
                        openAccountView();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VolleyError", error.toString());
                    }
                });

                queue.add(jsonObjectRequest);
                authenticating = false;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void openAccountView() {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        intent.putExtra("fragmentToLoad", "AccountFragment");
        startActivity(intent);
    }
}