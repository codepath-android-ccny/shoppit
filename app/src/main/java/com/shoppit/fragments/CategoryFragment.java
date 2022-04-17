package com.shoppit.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shoppit.R;
import com.shoppit.adapters.CategoriesAdapter;
import com.shoppit.models.Category;
import com.shoppit.utils.SpacesItemDecoration;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment{
    public static final String TAG = "CategoryFragment";

    RecyclerView rvCategories;
    CategoriesAdapter categoriesAdapter;
    List<Category> allCategories;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Setup any handles to view objects here
        rvCategories = view.findViewById(R.id.rvCategories);

        // Initialize the list
        allCategories = new ArrayList<>();
        // Initialize the adapter
        categoriesAdapter = new CategoriesAdapter(getContext(), allCategories);
        // Initialize the layout
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

        // Decorator which adds spacing around the tiles in a Grid layout RecyclerView
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);

        // Set the adapter and the linear layout manager to the RecyclerView
        rvCategories.setAdapter(categoriesAdapter);
        rvCategories.setLayoutManager(gridLayoutManager);
        rvCategories.addItemDecoration(decoration); // Not working properly

        // Get all the categories
        getAllCategories();
    }

    // Retrieve all the categories from the Parse backend
    private void getAllCategories() {
        // Define the class we would like to query
        ParseQuery<Category> categoryParseQuery = ParseQuery.getQuery(Category.class);

        // Include user key
        categoryParseQuery.include(Category.KEY_CATEGORY_NAME);
        // Execute the find asynchronously
        categoryParseQuery.findInBackground(new FindCallback<Category>() {
            @Override
            public void done(List<Category> categories, ParseException e) {
                if (e != null){
                    Log.e(TAG, "ERROR while retrieving categories", e);
                    return;
                }

                for (Category category: categories){
                    Log.i(TAG,  " Category Name: " + category.getCategoryName() + " Category Id: " + category.getObjectId());
                }

                // Add all categories to the list
                allCategories.addAll(categories);
                // Notify the adapter about the data change
                categoriesAdapter.notifyDataSetChanged();
            }
        });
    }

}