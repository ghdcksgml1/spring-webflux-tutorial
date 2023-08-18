package com.wintersoft.webfluxch2.cartitem;

import com.wintersoft.webfluxch2.item.Item;

public class CartItem {

    private Item item;
    private int quantity;

    public CartItem(Item item) {
        this.item = item;
        this.quantity = 1;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increment() {
        this.quantity++;
    }
}
