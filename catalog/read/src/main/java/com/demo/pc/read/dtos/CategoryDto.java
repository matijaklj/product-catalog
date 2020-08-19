package com.demo.pc.read.dtos;

import java.io.Serializable;

public class CategoryDto implements Serializable {
    private String id;
    private String name;

    public CategoryDto(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDto() {}

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
}
