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
import com.shoppit.R;
import com.shoppit.models.Item;
import com.parse.ParseFile;

import java.util.List;

public class ChildRecyclerViewItemsAdapter extends RecyclerView.Adapter<ChildRecyclerViewItemsAdapter.ViewHolder>{

    Context context;
    List<Item> items;

    public ChildRecyclerViewItemsAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_child_recyclerview_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item currentItem = items.get(position);
        holder.bind(currentItem);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivItemImage;
        TextView tvItemName;
        TextView tvItemUnitPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivItemImage = itemView.findViewById(R.id.ivItemImage);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemUnitPrice = itemView.findViewById(R.id.tvItemUnitPrice);
        }

        public void bind(Item item) {
            // Bind the Item data to the view elements
            tvItemName.setText(item.getItemName());
            tvItemUnitPrice.setText("$ " + String.valueOf(item.getUnitPrice()));

            // Load the Category image
            ParseFile image = item.getImage();
            if (image != null){
                Glide.with(context).load(image.getUrl()).into(ivItemImage);
            }
        }
    }
}
