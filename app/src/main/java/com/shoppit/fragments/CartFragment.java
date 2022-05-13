package com.shoppit.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.shoppit.R;
import com.shoppit.adapters.CartAdapter;
import com.shoppit.adapters.CategoriesAdapter;
import com.shoppit.models.CartItem;
import com.shoppit.models.Category;
import com.shoppit.models.Item;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private RecyclerView rvCartItem;
    private CartAdapter cartAdapter;
//    public static List<Item> item_list = new ArrayList<>();
    public static List<CartItem> item_list = new ArrayList<>();

    // Required empty public constructor
    public CartFragment() {

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
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set toolbar title
        Toolbar toolbar= Toolbar.class.cast(getActivity().findViewById(R.id.toolbar));
        assert toolbar != null;
        toolbar.setTitle("My Shopping Cart");
        // Set toolbar navigation icon to null
        toolbar.setNavigationIcon(null);


        System.out.println(item_list.size());

        rvCartItem = view.findViewById(R.id.rvCartItem);
        cartAdapter = new CartAdapter(getContext(), item_list);
        rvCartItem.setAdapter(cartAdapter);
        rvCartItem.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        // Hide the action_add menu item
        MenuItem itemAdd = menu.findItem(R.id.action_add);
        itemAdd.setVisible(false);
    }
}