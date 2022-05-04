package com.shoppit.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.parceler.Parcel;

@ParseClassName("ShoppingList")
@Parcel(analyze={ShoppingList.class})
public class ShoppingList extends ParseObject {

    public static final String KEY_SHOPPING_LIST_NAME = "shoppingListName";
    public static final String KEY_USER = "user";

    // empty constructor needed by the Parceler library
    public ShoppingList(){
    }

    public String getShoppingListName(){
        return getString(KEY_SHOPPING_LIST_NAME);
    }

    public void setShoppingListName(String shoppingListName){
        put(KEY_SHOPPING_LIST_NAME, shoppingListName);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user){
        put(KEY_USER, user);
    }

}
