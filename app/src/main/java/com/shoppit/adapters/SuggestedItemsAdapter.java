package com.shoppit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;
import com.shoppit.R;
import com.shoppit.models.Item;

import java.util.ArrayList;
import java.util.List;

public class SuggestedItemsAdapter extends RecyclerView.Adapter<SuggestedItemsAdapter.ViewHolder> {

    Context context;
    List<Item> items;

    public SuggestedItemsAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    // inflate item layout from XML and return the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_suggested_item, parent, false);
        return new ViewHolder(view);
    }

    // populate the data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.bind(item);
    }

    // returns the total count of items in the list
    @Override
    public int getItemCount() {
        return items.size();
    }

    // The ViewHolder is a wrapper around a View that contains the layout for an individual item in the list.
    // The Adapter creates ViewHolder objects as needed, and also sets the data for those views
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvItemName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvItemName = itemView.findViewById(R.id.tvItemName);
        }

        public void bind(Item item) {
            // Bind the Item data to the view elements
            tvItemName.setText(item.getItemName());
        }
    }


}
