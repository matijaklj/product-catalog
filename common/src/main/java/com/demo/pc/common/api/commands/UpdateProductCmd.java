package com.demo.pc.common.api.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.io.Serializable;

public class UpdateProductCmd implements Serializable {

    @TargetAggregateIdentifier
    private String id;
    private String name;
    private String description;

    public UpdateProductCmd(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public UpdateProductCmd() {}

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

    @Override
    public String toString() {
        return "UpdateProductCmd{" +
                "id='" + id + '}';
    }
}
