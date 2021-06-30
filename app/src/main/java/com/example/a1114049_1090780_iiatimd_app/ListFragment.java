package com.example.a1114049_1090780_iiatimd_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment  {

    private RecyclerView recipeListRecyclerView;
    private RecipeAdapter recipeListRecyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<Recipe> myRecipes = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstances){
        super.onViewCreated(view, savedInstances);
        recipeListRecyclerView = view.findViewById(R.id.recipeListRecyclerView);
        layoutManager = new LinearLayoutManager(this.getContext());
        recipeListRecyclerView.setLayoutManager(layoutManager);
        recipeListRecyclerView.hasFixedSize();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://iiatimd.jimmak.nl/api/recipes/",null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("gelukt", response.toString());
                    JSONArray recipeData = response.getJSONArray("data");
                    Log.d("recipejson", recipeData.toString());
                    for (int i = 0; i < recipeData.length(); i++){
                        myRecipes.add(new Recipe (
                                recipeData.getJSONObject(i).getInt("id"),
                                recipeData.getJSONObject(i).getString("title"),
                                recipeData.getJSONObject(i).getString("description_short"),
                                recipeData.getJSONObject(i).getString("description"),
                                recipeData.getJSONObject(i).getInt("prep_time_min")
                                )
                        );
                    }
                    recipeListRecyclerViewAdapter.notifyDataSetChanged();
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

        VolleySingleton.getInstance(this.getActivity().getApplicationContext()).addToRequestQueue(jsonObjectRequest);

        recipeListRecyclerViewAdapter = new RecipeAdapter(myRecipes);
        recipeListRecyclerView.setAdapter(recipeListRecyclerViewAdapter);

        recipeListRecyclerViewAdapter.setOnItemClickListener(new RecipeAdapter.recipeItemClickListener(){
            @Override
            public void onItemClick(View v, int position){
                Log.d("clicklistener", "clicked + " + String.valueOf(position));
                Intent intent = new Intent (v.getContext(), recipeDetailActivity.class);
                intent.putExtra("RECIPE_TITLE", myRecipes.get(position).getTitle());
                intent.putExtra("RECIPE_DESCRIPTION", myRecipes.get(position).getDescription());
                startActivity(intent);
            }
        });
    }
}