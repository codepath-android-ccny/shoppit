package com.shoppit.adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.shoppit.R;
import com.shoppit.fragments.ShoppingListDetailsFragment;
import com.shoppit.fragments.HomeFragment;
import com.shoppit.models.ShoppingList;

import java.lang.reflect.Method;
import java.util.List;

public class ShoppingListsAdapter extends RecyclerView.Adapter<ShoppingListsAdapter.ViewHolder>{

    Context context;
    List<ShoppingList> shoppingLists;

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param shoppingLists containing the data to populate views to be used
     * by RecyclerView.
     */
    public ShoppingListsAdapter(Context context, List<ShoppingList> shoppingLists) {
        this.context = context;
        this.shoppingLists = shoppingLists;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(context).inflate(R.layout.row_shopping_list, parent, false);

        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get element from your dataset at this position
        ShoppingList shoppingList = shoppingLists.get(position);

        // Replace the contents of the view with that element
        holder.bind(shoppingList);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return shoppingLists.size();
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
        // Widgets
        TextView tvShoppingList;
        TextView tvShoppingListOptions;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize the widgets
            tvShoppingList = itemView.findViewById(R.id.tvShoppingList);
            tvShoppingListOptions = itemView.findViewById(R.id.tvShoppingListOptions);
        }

        public void bind(ShoppingList shoppingList) {
            tvShoppingList.setText(shoppingList.getShoppingListName());

            // Set click listener on shopping list options
            tvShoppingListOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Attach the popup menu with the view
                    PopupMenu popupMenu = new PopupMenu(context, view);

                    // Use reflection to show the icons in the menu.
                    try {
                        Method method = popupMenu.getMenu().getClass().getDeclaredMethod("setOptionalIconsVisible", boolean.class);
                        method.setAccessible(true);
                        method.invoke(popupMenu.getMenu(), true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // Inflate the menu
                    popupMenu.getMenuInflater().inflate(R.menu.menu_shopping_list, popupMenu.getMenu());

                    // Set click listener on each item of the pop up menu
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()){
                                case R.id.action_rename:
                                    Log.i("ShoppingListsAdapter", "Clicked on Rename");
                                    return true;
                                case R.id.action_delete:
                                    Log.i("ShoppingListsAdapter", "Clicked on Delete");
                                    shoppingList.deleteInBackground();

                                    // Cast the context to AppCompatActivity
                                    AppCompatActivity activity = (AppCompatActivity) view.getContext();

                                    // Create the HomeFragment
                                    Fragment homeFragment = new HomeFragment();
                                    // Create transaction and Replace whatever is in the fragment_container view with this fragment
                                    // and finally Commit the transaction
                                    activity.
                                            getSupportFragmentManager()
                                            .beginTransaction()
                                            .replace(R.id.fragment_container,homeFragment)
                                            .addToBackStack(null)
                                            .commit();

                                    return true;
                                default:
                                    return false;
                            }
                        }
                    });
                    popupMenu.show();
                }
            });

            // Set click listener to tvShoppingList
            tvShoppingList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Cast the context to AppCompatActivity
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();

                    // Create the CategoryDetailsFragment
                    Fragment ShoppingListDetailsFragment = new ShoppingListDetailsFragment();

                    // Passing Category object to the fragment
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("shoppingListObj", shoppingList);

                    // Set CategoryDetailsFragment Arguments
                    ShoppingListDetailsFragment.setArguments(bundle);

                    // Create transaction and Replace whatever is in the fragment_container view with this fragment
                    // and finally Commit the transaction
                    activity.
                            getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container,ShoppingListDetailsFragment)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
    }

}
