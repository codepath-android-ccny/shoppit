package com.shoppit.adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;
import com.shoppit.R;
import com.shoppit.fragments.CategoryDetailsFragment;
import com.shoppit.models.Category;
import com.shoppit.models.Item;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{

    Context context;
    List<Item> items;

    public CartAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.bind(item);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvCartItem;
        ImageView ivCartImage;
        TextView tvCartPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCartItem = itemView.findViewById(R.id.tvCartItem);
            ivCartImage = itemView.findViewById(R.id.ivCartImage);
            tvCartPrice = itemView.findViewById(R.id.tvCartPrice);
        }

        public void bind(Item item) {
            // Bind the Category data to the view elements
            tvCartItem.setText(item.getItemName());
            tvCartPrice.setText(item.getUnitPrice().toString());
            // Load the Category image
            ParseFile image = item.getImage();
            if (image != null){
                Glide.with(context).load(image.getUrl()).into(ivCartImage);
            }
        }
    }
}