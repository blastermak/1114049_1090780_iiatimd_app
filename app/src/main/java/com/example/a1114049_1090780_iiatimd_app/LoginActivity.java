package com.example.a1114049_1090780_iiatimd_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
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

public class LoginActivity extends AppCompatActivity {

    boolean authenticating;
    String database_url = "http://10.0.2.2:8000/api/register";
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        queue = Volley.newRequestQueue(getApplicationContext());

        EditText userEmail = findViewById(R.id.inputLoginEmail);
        EditText userPassword = findViewById(R.id.inputLoginPassword);

        Button backToAccountButton = findViewById(R.id.backToAccountButton2);
        Button loginButton = findViewById(R.id.loginButton);

        backToAccountButton.setOnClickListener(this::toAccountFragment);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(userEmail.getText().toString(), userPassword.getText().toString());
            }
        });
    }

    private void login(String username, String password) {
        boolean formIsValid = false;

        if (!username.isEmpty() && !password.isEmpty() && username.contains("@")) {
            formIsValid = true;
        }

        if (formIsValid) {
            try {
                authenticating = true;
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("email", username);
                jsonBody.put("password", password);
                final String requestBody = jsonBody.toString();
                StringRequest stringRequest = new StringRequest(Request.Method.POST, database_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Volley", response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VolleyError", error.getMessage());
                    }
                })
                {
//                    @Override
//                    public String getBodyContentType() {
//                        return "application/json; charset=utf-8";
//                    }
//
//                    @Override
//                    public byte[] getBody() throws AuthFailureError {
//                        return requestBody.getBytes();
//                    }
//
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null) {
                            responseString = String.valueOf(response.statusCode);
                            Log.d("VolleyResponse", String.valueOf(response.data));
                            // returns some kind of token we could use?
                            Log.d("VolleyResponseHeaders", String.valueOf(response.headers));
                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }
                };

                queue.add(stringRequest);
                authenticating = false;
                Toast.makeText(getApplicationContext(), "Succesvol ingelogd", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Onjuiste logingegevens", Toast.LENGTH_SHORT).show();
        }

    }

    public void toAccountFragment(View view) {
        Fragment accountFragment = new AccountFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, accountFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}