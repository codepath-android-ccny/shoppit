package com.shoppit.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.shoppit.R;
import com.shoppit.models.Item;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get items from the Parse backend
        queryItem();
    }

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
                    Log.e("MainActivity", "ERROR while retrieving items", e);
                    return;
                }

                for (Item item: items){
                    Log.i("MainActivity", "Item: " + item.getItemName() + " Category Name: " + item.getCategory().getCategoryName());
                }
            }
        });
    }
}