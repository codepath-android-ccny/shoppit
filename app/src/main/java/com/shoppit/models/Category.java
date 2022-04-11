package com.shoppit.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.parceler.Parcel;

@ParseClassName("Category")
@Parcel(analyze={Category.class})
public class Category extends ParseObject {
    public static final String KEY_CATEGORY_NAME = "categoryName";

    // empty constructor needed by the Parceler library
    public Category(){
    }

    public String getCategoryName(){
        return getString(KEY_CATEGORY_NAME);
    }

    public void setCategoryName(String itemName){
        put(KEY_CATEGORY_NAME, itemName);
    }
}
