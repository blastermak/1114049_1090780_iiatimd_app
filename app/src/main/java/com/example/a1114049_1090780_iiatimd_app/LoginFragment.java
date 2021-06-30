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

public class LoginFragment extends Fragment {

    final String database_url = "http://10.0.2.2:8000/api/login";
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

        EditText userEmail = view.findViewById(R.id.inputEmail);
        EditText userPassword = view.findViewById(R.id.inputPassword);

        Button backToAccountButton = view.findViewById(R.id.backToAccountButton);
        Button registerButton = view.findViewById(R.id.registerButton);

        backToAccountButton.setOnClickListener(this::toAccountFragment);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(userEmail.getText().toString(), userPassword.getText().toString());

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
    public void login(String username, String password) {
        Log.d("buttonClick", "Login button clicked");
        boolean formIsValid = false;

        if (!username.isEmpty() && !password.isEmpty()) {
            formIsValid = true;
        }

        if (formIsValid) {
            try {
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("username", username);
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
                        Log.d("geenVolley", error.getMessage());
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
                Toast.makeText(getActivity().getApplicationContext(), "Succesvol ingelogd", Toast.LENGTH_SHORT).show();
            } catch (JSONException e){
                Toast.makeText(getActivity().getApplicationContext(), "Verkeerde informatie ingevuld", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void logout() {
    }
}
