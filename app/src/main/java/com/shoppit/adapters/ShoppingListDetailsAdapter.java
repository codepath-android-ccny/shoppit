package com.shoppit.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppit.models.Item;

import java.util.List;

public class ShoppingListDetailsAdapter extends RecyclerView.Adapter<ShoppingListDetailsAdapter.ViewHolder>{

    Context context;
    List<Item> items;

    public ShoppingListDetailsAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    // inflate item layout from XML and return the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    // populate the data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    // returns the total count of items in the list
    @Override
    public int getItemCount() {
        return 0;
    }

    // The ViewHolder is a wrapper around a View that contains the layout for an individual item in the list.
    // The Adapter creates ViewHolder objects as needed, and also sets the data for those views
    class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
