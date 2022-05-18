package com.shoppit.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
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
import com.parse.ParseUser;
import com.shoppit.R;
import com.shoppit.adapters.ShoppingListDetailsParentAdapter;
import com.shoppit.models.Category;
import com.shoppit.models.Item;
import com.shoppit.models.ShoppingList;
import com.shoppit.models.ShoppingListDetail;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListDetailsFragment extends Fragment {

   public static final String TAG = "ShoppingListDetailsFrag";

    ShoppingList shoppingList;
    RecyclerView rvParentShoppingListDetails;
    ShoppingListDetailsParentAdapter listDetailsParentAdapter;
    List<ShoppingListDetail> shoppingListDetails;
    List<Item> allItems;
    List<Category> allCategories;

    // Required empty public constructor
    public ShoppingListDetailsFragment() {

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
        return inflater.inflate(R.layout.fragment_shopping_list_details, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Receiving the data to the fragment
        if (getArguments() != null) {
            shoppingList = getArguments().getParcelable("shoppingListObj");
            Log.i(TAG, "ShoppingList id: " + shoppingList.getObjectId());
        }

        // Initialize the widgets
        rvParentShoppingListDetails = view.findViewById(R.id.rvParentShoppingListDetails);

        // Change the toolbar title dynamically
        Toolbar toolbar= Toolbar.class.cast(getActivity().findViewById(R.id.toolbar));
        assert toolbar != null;
        toolbar.setTitle(shoppingList.getShoppingListName());

        // set toolbar navigation icon: back arrow
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getActivity().onBackPressed();
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        // Initialize the lists
        allItems = new ArrayList<>();
        allCategories = new ArrayList<>();

        // Initialize the adapter
        listDetailsParentAdapter = new ShoppingListDetailsParentAdapter(getContext(), allItems, allCategories);

        // Initialize the layout
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        // Set the adapter and the linear layout manager to the RecyclerView
        rvParentShoppingListDetails.setAdapter(listDetailsParentAdapter);
        rvParentShoppingListDetails.setLayoutManager(linearLayoutManager);

        // Retrieve all the items for a particular list
        getShoppingListDetails(shoppingList);
    }

    // get all the items of the given ShoppingList
    public void getShoppingListDetails(ShoppingList shoppingList){
        List<Item> itemList = new ArrayList<>();
        List<Category> categoryList = new ArrayList<>();

        // Get the query
        ParseQuery<ShoppingListDetail> parseQuery = ParseQuery.getQuery(ShoppingListDetail.class);

        // Include Objects in the query
        parseQuery.include(ShoppingListDetail.KEY_USER);
        parseQuery.include(ShoppingListDetail.KEY_ITEM);
        parseQuery.include(ShoppingListDetail.KEY_CATEGORY);
        parseQuery.include(Item.KEY_ITEM_CATEGORY);

        // Retrieve ShoppingListDetail made by only the current user
        parseQuery.whereEqualTo(ShoppingListDetail.KEY_USER, ParseUser.getCurrentUser());
        parseQuery.whereEqualTo(ShoppingListDetail.KEY_SHOPPING_LIST, shoppingList);

        // Execute the find asynchronously
        parseQuery.findInBackground(new FindCallback<ShoppingListDetail>() {
            @Override
            public void done(List<ShoppingListDetail> shoppingListDetails, ParseException e) {
                if (e != null){
                    Log.e(TAG, "ERROR while retrieving ShoppingListDetail", e);
                    return;
                }

                for (ShoppingListDetail listDetail: shoppingListDetails){
                    Item item = listDetail.getItem();
                    Category category = listDetail.getCategory();

                    itemList.add(item);
                    if (!categoryList.contains(category)){
                        categoryList.add(category);
                    }

                    Log.i(TAG,  " Category Name: " + listDetail.getCategory().getCategoryName() + " Category Id: " +listDetail.getCategory().getObjectId());
                }

                // Add all the items to the shopping list
                allItems.addAll(itemList);

                // Add all the categories of the items
                allCategories.addAll(categoryList);

                // Notify the adapter about the data change
                listDetailsParentAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear(); // Remove all the old menu items
        inflater.inflate(R.menu.menu_shopping_list_details, menu);
    }
}