package com.shoppit.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.shoppit.R;
import com.shoppit.adapters.SearchItemsAdapter;
import com.shoppit.adapters.SuggestedItemsAdapter;
import com.shoppit.models.Item;

import java.util.ArrayList;
import java.util.List;

public class AddItemsToShoppingListFragment extends Fragment {
    private static final String TAG = "AddItemsToShoppingList";

    RecyclerView rvSuggestedItems;
    SuggestedItemsAdapter suggestedItemsAdapter;
    List<Item> allItems;

    // Required empty public constructor
    public AddItemsToShoppingListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_items_to_shopping_list, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set the title of the toolbar to null
        Toolbar toolbar= (Toolbar) requireActivity().findViewById(R.id.toolbar);
        assert toolbar != null;
        toolbar.setTitle("Add Items");

        // Setup any handles to view objects here
        rvSuggestedItems = view.findViewById(R.id.rvSuggestedItems);

        // Initialize the list
        allItems = new ArrayList<>();
        // Initialize the adapter
        suggestedItemsAdapter = new SuggestedItemsAdapter(getContext(), allItems);
        // Initialize the layout
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        // Decorate the RecyclerView
        // This decorator displays dividers between each item within the list
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);

        // Set the Adapter for the RecyclerView
        rvSuggestedItems.setAdapter(suggestedItemsAdapter);

        // Set the LayoutManager for the RecyclerView
        rvSuggestedItems.setLayoutManager(linearLayoutManager);
        rvSuggestedItems.addItemDecoration(itemDecoration);

        // Retrieve all suggested items
        querySuggestedItems();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        // Remove all the old menu items
        menu.clear();
        // Inflate the new menu options
        inflater.inflate(R.menu.add_items_to_shopping_list_menu, menu);
    }

    private void querySuggestedItems() {
        // Define the class we would like to query
        ParseQuery<Item> itemParseQuery = ParseQuery.getQuery(Item.class);

        // Include user key
        itemParseQuery.include(Item.KEY_ITEM_CATEGORY);
        // sort the items by name
        itemParseQuery.orderByAscending(Item.KEY_ITEM_NAME);

        // Execute the find asynchronously
        itemParseQuery.findInBackground(new FindCallback<Item>() {
            @Override
            public void done(List<Item> items, ParseException e) {
                if (e != null){
                    Log.e(TAG, "ERROR while retrieving items", e);
                    return;
                }

                // Add all categories to the list
                allItems.addAll(items);
                // Notify the adapter about the data change
                suggestedItemsAdapter.notifyDataSetChanged();
            }
        });
    }
}