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

public class CategoryDetailsAdapter extends RecyclerView.Adapter<CategoryDetailsAdapter.ViewHolder>{

    Context context;
    List<Item> items;
    int itemCount;

    public CategoryDetailsAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    // inflate item layout from XML and return the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_category_single_item, parent, false);
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

        ImageView ivItemImage;
        TextView tvItemName;
        TextView tvItemUnitPrice;

        ImageView ivRemoveItem;
        TextView tvItemCount;
        ImageView ivAddItem;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivItemImage = itemView.findViewById(R.id.ivItemImage);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemUnitPrice = itemView.findViewById(R.id.tvItemUnitPrice);

            ivRemoveItem = itemView.findViewById(R.id.ivRemoveItem);
            tvItemCount = itemView.findViewById(R.id.tvItemCount);
            ivAddItem = itemView.findViewById(R.id.ivAddItem);

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

            // Set click listener to Add item image view
            ivAddItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Get the item count from the text field
                    itemCount = Integer.parseInt(tvItemCount.getText().toString());

                    // Make the remove item button and item count field visible
                    ivRemoveItem.setVisibility(View.VISIBLE);
                    tvItemCount.setVisibility(View.VISIBLE);

                    // Increment the item count field
                    itemCount++;

                    // Set the item count field with the new value
                    tvItemCount.setText(String.valueOf(itemCount));
                }
            });

            // Set click listener to Remove item image view
            ivRemoveItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Get the item count from the text field
                    itemCount = Integer.parseInt(tvItemCount.getText().toString());

                    // decrement item count
                    itemCount--;

                    if (itemCount > 0){

                        ivRemoveItem.setVisibility(View.VISIBLE);
                        tvItemCount.setVisibility(View.VISIBLE);

                        tvItemCount.setText(String.valueOf(itemCount));
                    }

                    if (itemCount == 0){

                        tvItemCount.setText(String.valueOf(itemCount));

                        ivRemoveItem.setVisibility(View.GONE);
                        tvItemCount.setVisibility(View.GONE);
                    }
                }
            });
        }
    }


}
