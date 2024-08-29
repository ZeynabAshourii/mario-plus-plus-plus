package org.example.server.model;


import java.io.Serializable;
import java.util.Arrays;

public class SpecialBag implements Serializable {
    private Item[] items = new Item[5];

    public SpecialBag(Item[] items) {
        this.items = items;
    }

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "SpecialBag{" +
                "items=" + Arrays.toString(items) +
                '}';
    }
}
