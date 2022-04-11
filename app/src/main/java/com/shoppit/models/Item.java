package com.shoppit.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import org.parceler.Parcel;

@ParseClassName("Item")
@Parcel(analyze={Item.class})
public class Item extends ParseObject {
    public static final String KEY_ITEM_NAME = "itemName";
    public static final String KEY_ITEM_IMAGE = "image";
    public static final String KEY_ITEM_CATEGORY = "category";
    public static final String KEY_CREATED_AT = "createdAt";

    // empty constructor needed by the Parceler library
    public Item(){
    }

    public String getItemName(){
        return getString(KEY_ITEM_NAME);
    }

    public void setItemName(String itemName){
        put(KEY_ITEM_NAME, itemName);
    }

    public ParseFile getImage(){
        return getParseFile(KEY_ITEM_IMAGE);
    }

    public void setImage(ParseFile parseFile){
        put(KEY_ITEM_IMAGE, parseFile);
    }

    public Category getCategory(){
        return (Category) getParseObject(KEY_ITEM_CATEGORY);
    }

    public void setCategory(Category category){
        put(KEY_ITEM_CATEGORY, category);
    }
}
