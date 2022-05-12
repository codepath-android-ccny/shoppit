package com.shoppit.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.shoppit.R;
import com.shoppit.models.Category;
import com.shoppit.models.ShoppingList;

public class ShoppingListDetailsFragment extends Fragment {

   public static final String TAG = "ShoppingListDetailsFrag";

    ShoppingList shoppingList;

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
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear(); // Remove all the old menu items
        inflater.inflate(R.menu.menu_shopping_list_details, menu);
    }
}