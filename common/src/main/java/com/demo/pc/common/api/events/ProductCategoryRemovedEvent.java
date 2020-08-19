package com.demo.pc.common.api.events;

public class ProductCategoryRemovedEvent {

    private String id;
    private String categoryId;

    public ProductCategoryRemovedEvent(String id, String categoryId) {
        this.id = id;
        this.categoryId = categoryId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
