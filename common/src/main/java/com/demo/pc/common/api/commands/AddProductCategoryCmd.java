package com.demo.pc.common.api.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.io.Serializable;

public class AddProductCategoryCmd implements Serializable {

    @TargetAggregateIdentifier
    private String id;
    private String categoryId;

    public AddProductCategoryCmd(String id, String categoryId) {
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
