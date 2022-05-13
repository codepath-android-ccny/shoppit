package com.shoppit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;
import com.shoppit.R;
import com.shoppit.fragments.CartFragment;
import com.shoppit.models.Item;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProfileHistoryAdapter extends RecyclerView.Adapter<ProfileHistoryAdapter.ViewHolder>{

    Context context;
    List<Item> items;


    public ProfileHistoryAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    // inflate item layout from XML and return the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_profile_info, parent, false);
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
        private TextView tvHistoryName;
        private TextView tvPurchaseQuantity;
        private TextView tvPurchaseDate;
        private ImageView ivHistoryItem;



        //solomon -- set onClickListener for image
        //              when click, adds Item to cart


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHistoryName = itemView.findViewById(R.id.tvHistoryName);
            tvPurchaseQuantity = itemView.findViewById(R.id.tvHistoryQuantity);
            tvPurchaseDate = itemView.findViewById(R.id.tvPurchaseDate);
            ivHistoryItem = itemView.findViewById(R.id.ivHistoryItem);

        }

        //change item to historyitem
        public void bind(Item item) {
            tvHistoryName.setText(item.getItemName());
            Date date = item.getCreatedAt();
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String reportDate = df.format(date);
            tvPurchaseDate.setText(reportDate);

            ParseFile image = item.getImage();
            if (image != null){
                Glide.with(context).load(image.getUrl()).into(ivHistoryItem);
            }
        }
    }
}