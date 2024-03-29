package com.example.a1114049_1090780_iiatimd_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    String database_url = "http://10.0.2.2:8000/api/search/";
    RequestQueue queue;
    private RecipeViewModel recipeViewModel;

    private RecyclerView searchRecyclerView;
    private RecipeAdapter searchRecyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<Recipe> myRecipes = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        queue = Volley.newRequestQueue(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        // recyclerView and other parts are created so that they can be filled later with the content loaded from the local or Laravel DB
        searchRecyclerView = view.findViewById(R.id.searchRecyclerView);
        layoutManager = new LinearLayoutManager(this.getContext());
        searchRecyclerView.setLayoutManager(layoutManager);
        searchRecyclerViewAdapter = new RecipeAdapter(myRecipes);

        Button searchButton = view.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });
        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        // Inflate the layout for this fragment
        return view;
    }

    public void search() {
        boolean searchIsValid = false;
        SearchView searchView = getView().findViewById(R.id.searchInput);
        String searchInput = searchView.getQuery().toString();
        if (!searchInput.isEmpty()) {
            searchIsValid = true;
        }

        if (searchIsValid) {
            try {
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("search", searchInput);
                final JSONArray[] jsonResponse = {new JSONArray()};
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, database_url, jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("VolleyResponse", String.valueOf(response));
                        try {
                            jsonResponse[0] = response.getJSONArray("data");
                            searchDb(jsonResponse[0]);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("failed", "failed to connect");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VolleyError", error.getMessage());
                        searchLocal(jsonBody.toString());
                    }
                });
                queue.add(jsonObjectRequest);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void searchDb(JSONArray searchTerm) {
        // Searching Laravel DB
        myRecipes.clear();
        for (int i = 0; i < searchTerm.length(); i++) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject = searchTerm.getJSONObject(i);
                Recipe recipe = new Recipe(jsonObject.getInt("id"), jsonObject.getString("title"), jsonObject.getString("description_short"), jsonObject.getString("description"), jsonObject.getInt("prep_time_min"));
                myRecipes.add(recipe);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Adding items to recyclerView
        searchRecyclerViewAdapter.notifyItemInserted(myRecipes.size()-1);
        searchRecyclerViewAdapter = new RecipeAdapter(myRecipes);
        searchRecyclerView.setAdapter(searchRecyclerViewAdapter);
    }

    public void searchLocal(String searchTerm) {
        // strip searchTerm of unused characters
        String[] value = searchTerm.split("\"");
        String searchTerm2 = "%" + value[3] + "%";
        getRecipes(searchTerm2);
    }

    public void getRecipes(String searchTerm) {
        // searching Local DB
        myRecipes.clear();
        recipeViewModel.getSearchRecipes(searchTerm).observe(getViewLifecycleOwner(), recipes -> {
            for (int i = 0; i < recipes.size(); i++) {
                if (!myRecipes.contains(recipes.get(i))) {
                    myRecipes.add(recipes.get(i));
                    searchRecyclerViewAdapter.notifyItemInserted(recipes.size());
                }
            }
        });
        // Adding items to recycler view
        searchRecyclerViewAdapter = new RecipeAdapter(myRecipes);
        searchRecyclerView.setAdapter(searchRecyclerViewAdapter);
    }
}