package com.example.a1114049_1090780_iiatimd_app;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {

    private RecyclerView recipeListRecyclerView;
    private RecyclerView.Adapter recipeListRecyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<Recipe> recipes;

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
        final Recipe[] recipes = new Recipe[20];
        recipes[0] = new Recipe(1, "Burger", "Lekkere burger");
        recipes[1] = new Recipe(2, "Pasta", "Lekkere pasta");
        recipes[2] = new Recipe(3, "Risotto", "Lekkere risotto");
        recipes[3] = new Recipe(4, "Paella", "Lekkere paella");
        recipes[4] = new Recipe(5, "Curry", "Lekkere curry");
        recipes[5] = new Recipe(6, "Hutspot", "Lekkere hutspot");
        recipes[6] = new Recipe(7, "Tomatensoep", "Lekkere tomatensoep");
        recipes[7] = new Recipe(8, "Stroopwafelijs", "Lekker stroopwafelijs");
        recipes[8] = new Recipe(9, "Luxe tosti", "Lekkere tosti");
        recipes[9] = new Recipe(10, "Knoflookstokbrood", "Lekker knoflookstokbrood");
        recipes[10] = new Recipe(11, "Burger", "Lekkere burger");
        recipes[11] = new Recipe(12, "Pasta", "Lekkere pasta");
        recipes[12] = new Recipe(13, "Risotto", "Lekkere risotto");
        recipes[13] = new Recipe(14, "Paella", "Lekkere paella");
        recipes[14] = new Recipe(15, "Curry", "Lekkere curry");
        recipes[15] = new Recipe(16, "Hutspot", "Lekkere hutspot");
        recipes[16] = new Recipe(17, "Tomatensoep", "Lekkere tomatensoep");
        recipes[17] = new Recipe(18, "Stroopwafelijs", "Lekker stroopwafelijs");
        recipes[18] = new Recipe(19, "Luxe tosti", "Lekkere tosti");
        recipes[19] = new Recipe(20, "Knoflookstokbrood", "Lekker knoflookstokbrood");

        recipeListRecyclerViewAdapter = new RecipeAdapter(recipes);
        recipeListRecyclerView.setAdapter(recipeListRecyclerViewAdapter);
    }
}