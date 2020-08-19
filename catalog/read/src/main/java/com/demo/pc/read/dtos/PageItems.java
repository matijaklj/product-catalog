package com.demo.pc.read.dtos;

import java.util.List;

public class PageItems<T> {
    private List<T> items;
    private int count;
    private int totalItems;

    public PageItems(List<T> items, int count, int totalItems) {
        this.items = items;
        this.count = count;
        this.totalItems = totalItems;
    }

    public PageItems() {}

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }
}
