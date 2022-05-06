package com.shoppit.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.SaveCallback;
import com.shoppit.R;
import com.shoppit.fragments.CategoryFragment;
import com.shoppit.fragments.ShoppingListDialogFragment;
import com.shoppit.fragments.HomeFragment;
import com.shoppit.fragments.ProfileFragment;
import com.shoppit.fragments.SearchFragment;
import com.shoppit.fragments.ShopFragment;
import com.shoppit.models.Item;
import com.shoppit.models.ShoppingList;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ShoppingListDialogFragment.OnInputListener {

    private static final String TAG = "MainActivity";
    
    final FragmentManager fragmentManager = getSupportFragmentManager();

    // variables
    String shoppingListName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        toolbar.setTitle("ShoppIt");

        // Find the elements
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // set click listener on bottom navigation
        bottomNavigationView.setOnItemSelectedListener(navItemSelectedListener);

        // Set default selection, in this case home item
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        // get items
        queryItem();
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // When clicked on Add menu item
        if (item.getItemId() == R.id.action_add){
            return super.onOptionsItemSelected(item);
        }

        if (item.getItemId() == R.id.action_create_list) {

            ShoppingListDialogFragment shoppingListDialog = ShoppingListDialogFragment.newInstance("Add a Shopping List");
            shoppingListDialog.show(fragmentManager, "dialog_create_shopping_list");
        }

        return true;
    }

    // Define click listener on bottom navigation
    private BottomNavigationView.OnItemSelectedListener navItemSelectedListener = new BottomNavigationView.OnItemSelectedListener(){
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.nav_home:
                default:
                    fragment = new HomeFragment();
                    break;
                case R.id.nav_shop:
                    fragment = new ShopFragment();
                    break;
                case R.id.nav_category:
                    fragment = new CategoryFragment();
                    break;
                case R.id.nav_search:
                    fragment = new SearchFragment();
                    break;
                case R.id.nav_profile:
                    fragment = new ProfileFragment();
                    break;
            }
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
            return true;
        }
    };

    private void queryItem() {
        // Define the class we would like to query
        ParseQuery<Item> itemParseQuery = ParseQuery.getQuery(Item.class);

        // Include user key
        itemParseQuery.include(Item.KEY_ITEM_CATEGORY);
        // Execute the find asynchronously
        itemParseQuery.findInBackground(new FindCallback<Item>() {
            @Override
            public void done(List<Item> items, ParseException e) {
                if (e != null){
                    Log.e("HomeActivity", "ERROR while retrieving posts", e);
                    return;
                }

                for (Item item: items){
                    Log.i("HomeActivity", "Item: " + item.getItemName() +
                            " Category Name: " + item.getCategory().getCategoryName() +
                            " Category Id: " + item.getCategory().getObjectId());
                }
            }
        });
    }

    // Create ShoppingList object with a given name
    private void createShoppingList(String shoppingListName){
        // Create the ShoppingList object and save in the parse backend;
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setShoppingListName(shoppingListName);

        //shoppingList.saveInBackground();

        // save the post
        shoppingList.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null){
                    Log.e(TAG, "Error while saving shoppingList", e);
                }

                Log.i(TAG, "ShoppingList saved successfully!");
            }
        });
    }

    @Override
    public void sendInput(String input) {
        Log.d(TAG, "sendInput: got the input: " + input);

        shoppingListName = input;

        Log.d(TAG, "Shopping List Name: " + shoppingListName);

        // Create a shopping list object with the given name and save it into the parse db
        createShoppingList(shoppingListName);

        // Pass the input to the HomeFragment
        // Get the activity
        AppCompatActivity activity =  MainActivity.this;

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

    }

}