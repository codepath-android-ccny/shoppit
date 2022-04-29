package com.shoppit.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shoppit.R;
import com.shoppit.models.ShoppingList;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    TextView tvText;
    ImageView ivListIcon;
    RecyclerView rvShoppingLists;
    List<ShoppingList> shoppingLists;

    // Required empty public constructor
    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set the toolbar title
        Toolbar toolbar= Toolbar.class.cast(getActivity().findViewById(R.id.toolbar));
        toolbar.setTitle("ShoppIt");

        tvText = view.findViewById(R.id.tvText);
        ivListIcon = view.findViewById(R.id.ivListIcon);
        rvShoppingLists = view.findViewById(R.id.rvShoppingLists);

        // Initialize list
        shoppingLists = new ArrayList<>();

        // Initialize recycler view

        showMessageOnEmptyRecyclerView();
    }

    // Show a message to the user if the RecyclerView is empty
    private void showMessageOnEmptyRecyclerView(){
        if (shoppingLists.isEmpty()){
            rvShoppingLists.setVisibility(View.GONE);
            ivListIcon.setVisibility(View.VISIBLE);
            tvText.setVisibility(View.VISIBLE);

            // tvText.setText(String.valueOf("You don't have any shopping list!"));
        }else {
            rvShoppingLists.setVisibility(View.VISIBLE);
            ivListIcon.setVisibility(View.GONE);
            tvText.setVisibility(View.GONE);
        }
    }
}