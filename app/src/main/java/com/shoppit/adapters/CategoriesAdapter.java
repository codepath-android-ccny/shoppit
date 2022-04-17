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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.shoppit.R;
import com.shoppit.fragments.CategoryDetailsFragment;
import com.shoppit.models.Category;
import com.parse.ParseFile;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder>{

    Context context;
    List<Category> categories;

    public CategoriesAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.bind(category);

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardViewCategory;
        ImageView ivCategoryImage;
        TextView tvCategoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardViewCategory = itemView.findViewById(R.id.cardViewCategory);
            ivCategoryImage = itemView.findViewById(R.id.ivCategoryImage);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
        }

        public void bind(Category category) {
            // Bind the Category data to the view elements
            tvCategoryName.setText(category.getCategoryName());

            // Load the Category image
            ParseFile image = category.getImage();
            if (image != null){
                Glide.with(context).load(image.getUrl()).into(ivCategoryImage);
            }

            // Set click listener on CardView and open CategoryDetailsFragment when clicked on the CardView
            cardViewCategory.setOnClickListener(new View.OnClickListener() {
                // open CategoryDetailsFragment when clicked on the CardView
                @Override
                public void onClick(View view) {
                    Log.i("CategoryFragment", "Category " + category.getCategoryName()+ " is clicked");

                    // Cast the context to AppCompatActivity
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();

                    // Create the CategoryDetailsFragment
                    Fragment categoryDetailsFragment = new CategoryDetailsFragment();

                    // Passing Category object to the fragment
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("categoryObj", category);

                    // Set CategoryDetailsFragment Arguments
                    categoryDetailsFragment.setArguments(bundle);

                    // Create transaction and Replace whatever is in the fragment_container view with this fragment
                    // and finally Commit the transaction
                    activity.
                            getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container,categoryDetailsFragment)
                            .addToBackStack(null)
                            .commit();
                }
            });

        }
    }
}
