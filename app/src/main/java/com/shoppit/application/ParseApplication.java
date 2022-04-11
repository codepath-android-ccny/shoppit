package com.shoppit.application;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;
import com.shoppit.models.Category;
import com.shoppit.models.Item;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Register the parse model
        ParseObject.registerSubclass(Item.class);
        ParseObject.registerSubclass(Category.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("nJBq3qwnaHFyhaHYUYKJbDjXukwQYyEy2H13Dy7U")
                .clientKey("5K6hwMzO9ijdSX9gxK2aXyKVQPLxiwWoll4fb6cz")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}