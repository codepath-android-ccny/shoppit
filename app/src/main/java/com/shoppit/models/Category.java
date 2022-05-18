package com.shoppit.models;

import androidx.annotation.Nullable;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import org.parceler.Parcel;


@ParseClassName("Category")
@Parcel(analyze={Category.class})
public class Category extends ParseObject {

    public static final String KEY_CATEGORY_NAME = "categoryName";
    public static final String KEY_CATEGORY_IMAGE = "categoryImage";

    // empty constructor needed by the Parceler library
    public Category(){
    }

    public String getCategoryName(){
        return getString(KEY_CATEGORY_NAME);
    }

    public void setCategoryName(String categoryName){
        put(KEY_CATEGORY_NAME, categoryName);
    }

    public ParseFile getImage(){
        return getParseFile(KEY_CATEGORY_IMAGE);
    }

    public void setImage(ParseFile parseFile){
        put(KEY_CATEGORY_IMAGE, parseFile);
    }

    @Override
    public String toString() {
        return "Category{} " + this.getCategoryName();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {

        Category category = (Category) obj;
        assert category != null;
        return this.getCategoryName().equals(category.getCategoryName());
    }
}
