package com.shoppit.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shoppit.R;
import com.shoppit.adapters.CartAdapter;
import com.shoppit.adapters.CategoriesAdapter;
import com.shoppit.models.Category;
import com.shoppit.models.Item;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private RecyclerView rvCartItem;
    private CartAdapter cartAdapter;
    public static List<Item> item_list = new ArrayList<>();

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        System.out.println(item_list.size());
        rvCartItem = view.findViewById(R.id.rvCartItem);
        cartAdapter = new CartAdapter(getContext(), item_list);
        rvCartItem.setAdapter(cartAdapter);
        rvCartItem.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }
}