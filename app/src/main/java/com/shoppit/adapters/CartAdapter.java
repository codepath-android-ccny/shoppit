package com.shoppit.adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
import com.shoppit.models.CartItem;
import com.shoppit.models.Category;
import com.shoppit.models.Item;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{

    Context context;
    List<CartItem> items;
    private double totalCost = 0;

    //changed List<Item> to List<CartItem>
    public CartAdapter(Context context, List<CartItem> items) {
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
        CartItem item = items.get(position);
        holder.bind(item);
        System.out.println(totalCost);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvCartItem;
        ImageView ivCartImage;
        TextView tvCartPrice;
        TextView tvCartQuantity;
        ImageButton ibCartRemove;
        ImageButton ibCartAdd;
        ImageButton ibCartDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCartItem = itemView.findViewById(R.id.tvCartItem);
            ivCartImage = itemView.findViewById(R.id.ivCartImage);
            tvCartPrice = itemView.findViewById(R.id.tvCartPrice);
            tvCartQuantity = itemView.findViewById(R.id.tvCartQuantity);
            ibCartAdd = itemView.findViewById(R.id.ibCartAdd);
            ibCartRemove = itemView.findViewById(R.id.ibCartRemove);
            ibCartDelete = itemView.findViewById(R.id.ibCartDelete);
        }

        public void bind(CartItem item) {
            //calculatoe total cost
            totalCost += item.getQuantity()*item.getItem().getUnitPrice();
            // Bind the Category data to the view elements
            tvCartItem.setText(item.getItem().getItemName());
            tvCartPrice.setText(item.getItem().getUnitPrice().toString());
            tvCartQuantity.setText(String.valueOf(item.getQuantity()));
            // Load the Category image
            ParseFile image = item.getItem().getImage();
            if (image != null){
                Glide.with(context).load(image.getUrl()).into(ivCartImage);
            }

            ibCartAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    items.get(getBindingAdapterPosition()).addQuantity(1);
                    //update totalCost
                    totalCost += items.get(getBindingAdapterPosition()).getItem().getUnitPrice();
                    //update text
                    tvCartQuantity.setText(String.valueOf(items.get(getBindingAdapterPosition()).getQuantity()));
                }
            });

            ibCartRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(items.get(getBindingAdapterPosition()).getQuantity() > 0){
                        items.get(getBindingAdapterPosition()).removeQuantity(1);
                        totalCost -= items.get(getBindingAdapterPosition()).getItem().getUnitPrice();
                        //update text
                        tvCartQuantity.setText(String.valueOf(items.get(getBindingAdapterPosition()).getQuantity()));
                    }
                }
            });

            ibCartDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    items.remove(item);
                    notifyDataSetChanged();
                }
            });
        }
    }
}