package com.shoppit.models;

public class CartItem {
    private Item item;
    private int quantity;

    public CartItem(){
        this.item = new Item();
        this.quantity = 0;
    }

    public CartItem(Item item, int quantity){
        this.quantity = quantity;
        this.item = item;
    }

    public Item getItem(){
        return this.item;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public void addQuantity(int addQuantity){
        this.quantity += addQuantity;
    }

    public void removeQuantity(int removeQuantity){
        if(this.quantity - removeQuantity < 0){
            this.quantity = 0;
        }
        else{
            this.quantity -= removeQuantity;
        }
    }
}
