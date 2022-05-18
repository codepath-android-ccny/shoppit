package com.shoppit.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.parceler.Parcel;

@ParseClassName("ShoppingListDetail")
@Parcel(analyze={ShoppingListDetail.class, Item.class, Category.class, ShoppingList.class})
public class ShoppingListDetail extends ParseObject {
    public static final String KEY_SHOPPING_LIST = "shoppingList";
    public static final String KEY_ITEM = "item";
    public static final String KEY_USER = "user";
    public static final String KEY_CATEGORY = "category";

    public ShoppingList getShoppingList(){
        return (ShoppingList) getParseObject(KEY_SHOPPING_LIST);
    }

    public Item getItem() {
        return (Item) getParseObject(KEY_ITEM);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public Category getCategory() {
        return (Category) getParseObject(KEY_CATEGORY);
    }
}
