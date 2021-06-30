package com.example.a1114049_1090780_iiatimd_app;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class RegisterFragment extends Fragment {

    final String database_url = "http://10.0.2.2:8000/api/register";
    //    final String database_url = "https://iiatimd.jimmak.nl/api";
    RequestQueue queue;
    String errorMessage;
    private boolean authenticating = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        EditText userFullName = view.findViewById(R.id.inputFullName);
        EditText userEmail = view.findViewById(R.id.inputEmail);
        EditText userPassword = view.findViewById(R.id.inputPassword);
        EditText userConfirmPassword = view.findViewById(R.id.inputConfirmPassword);

        Button backToAccountButton = view.findViewById(R.id.backToAccountButton);
        Button registerButton = view.findViewById(R.id.registerButton);

        backToAccountButton.setOnClickListener(this::toAccountFragment);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(userFullName.getText().toString(), userEmail.getText().toString(), userPassword.getText().toString(), userConfirmPassword.getText().toString());
            }
        });

        return view;
    }

    public void toAccountFragment(View view) {
        Fragment accountFragment = new AccountFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, accountFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void register(String username, String email, String password, String confirmPassword) {
        Log.d("buttonClick", "Register button clicked");
        boolean formIsValid = false;

        if (!username.isEmpty() && !email.isEmpty() && email.contains("@") && !password.isEmpty() && !confirmPassword.isEmpty() && password.equals(confirmPassword)) {
            formIsValid = true;
        }

        if (formIsValid) {
            try {
                authenticating = true;
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("name", username);
                jsonBody.put("email", email);
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
                        Log.d("heeftNietGewerkt", error.getMessage());
                    }
                }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        return requestBody.getBytes();
                    }

                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null) {
                            responseString = String.valueOf(response.statusCode);
                            Log.d("VolleyResponse", String.valueOf(response.data));
                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }
                };

                queue.add(stringRequest);
                authenticating = false;
                Toast.makeText(getActivity().getApplicationContext(), "Succesvol geregistreerd", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                Log.d("VolleyError", String.valueOf(e));
            }

        } else {
            Toast.makeText(getActivity().getApplicationContext(), "Verkeerde informatie ingevuld", Toast.LENGTH_SHORT).show();
        }
    }
}
