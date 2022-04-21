package com.shoppit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppit.R;
import com.shoppit.models.Category;
import com.shoppit.models.Item;

import java.util.ArrayList;
import java.util.List;

public class ParentRecyclerViewCategoriesAdapter extends RecyclerView.Adapter<ParentRecyclerViewCategoriesAdapter.ViewHolder> {

    Context context;
    List<Category> categories;
    List<Item> items;

    public ParentRecyclerViewCategoriesAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    public ParentRecyclerViewCategoriesAdapter(Context context, List<Category> categories, List<Item> items) {
        this.context = context;
        this.categories = categories;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_parent_recyclerview_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category currentCategory = categories.get(position);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.rvChildItems.setLayoutManager(layoutManager);
        holder.rvChildItems.setHasFixedSize(true);

        holder.tvCategoryName.setText(currentCategory.getCategoryName());
        List<Item> categoryItems = new ArrayList<>();

        // Add child rows
        for (Item item: items){
            assert items != null;
            if (currentCategory.getCategoryName().equals(item.getCategory().getCategoryName())){
                categoryItems.add(item);
            }
        }

        ChildRecyclerViewItemsAdapter childRecyclerViewItemsAdapter = new ChildRecyclerViewItemsAdapter(context, categoryItems);
        holder.rvChildItems.setAdapter(childRecyclerViewItemsAdapter);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvCategoryName;
        RecyclerView rvChildItems;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            rvChildItems = itemView.findViewById(R.id.rvChildItems);
        }
    }
}
