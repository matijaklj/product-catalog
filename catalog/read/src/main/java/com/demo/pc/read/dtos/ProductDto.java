package com.demo.pc.read.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductDto implements Serializable {
    private String id;
    private String name;
    private String description;
    private Integer stock = null;
    private Float price = null;
    private List<CategoryDto> categories;

    public ProductDto(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categories = new ArrayList<>();
    }

    public ProductDto(String id, String name, String description, List<CategoryDto> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categories = categories;
    }

    public ProductDto() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }
}
