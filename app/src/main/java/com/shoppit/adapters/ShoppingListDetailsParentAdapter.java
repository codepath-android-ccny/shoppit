package com.shoppit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.parse.ParseException;
import com.shoppit.R;
import com.shoppit.models.Category;
import com.shoppit.models.Item;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListDetailsParentAdapter extends RecyclerView.Adapter<ShoppingListDetailsParentAdapter.ViewHolder>{

    Context context;
    List<Item> items;
    List<Category> categories;

    public ShoppingListDetailsParentAdapter(Context context, List<Item> items, List<Category> categories) {
        this.context = context;
        this.items = items;
        this.categories = categories;
    }

    // inflate item layout from XML and return the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_shopping_list_details_parent_item, parent, false);
        return new ViewHolder(view);
    }

    // populate the data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category currentCategory = categories.get(position);

        // Get the name of current category
        String categoryName = currentCategory.getCategoryName();


        // Initialize the layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        // Decorate the RecyclerView
        // This decorator displays dividers between each item within the list
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL);

        holder.rvContainerChildListItems.setLayoutManager(layoutManager);
        holder.rvContainerChildListItems.addItemDecoration(itemDecoration);
        holder.rvContainerChildListItems.setHasFixedSize(true);


        holder.tvCategoryName.setText(categoryName);
        List<Item> categoryItems = new ArrayList<>();

        // Add child rows
        for (Item item: items){
            assert items != null;
            try {
                String name = item.getCategory().fetchIfNeeded().getString("categoryName");

                if (categoryName.equals(name)){
                    categoryItems.add(item);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        ShoppingListDetailsChildAdapter shoppingListDetailsChildAdapter = new ShoppingListDetailsChildAdapter(context, categoryItems);
        holder.rvContainerChildListItems.setAdapter(shoppingListDetailsChildAdapter);

    }

    // returns the total count of items in the list
    @Override
    public int getItemCount() {
        return categories.size();
    }

    // The ViewHolder is a wrapper around a View that contains the layout for an individual item in the list.
    // The Adapter creates ViewHolder objects as needed, and also sets the data for those views
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvCategoryName;
        RecyclerView rvContainerChildListItems;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            rvContainerChildListItems = itemView.findViewById(R.id.rvContainerChildListItems);
        }
    }
}
