package com.demo.pc.write.dtos;

import java.io.Serializable;

// todo remove
public class ProductDto implements Serializable {

    private String name;
    private String description;

    public ProductDto() {
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
}
