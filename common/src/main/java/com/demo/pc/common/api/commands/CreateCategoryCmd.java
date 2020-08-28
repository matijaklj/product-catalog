package com.demo.pc.common.api.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.io.Serializable;

public class CreateCategoryCmd implements Serializable {

    @TargetAggregateIdentifier
    private String id;
    private String name;

    public CreateCategoryCmd(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public CreateCategoryCmd() {}

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

    @Override
    public String toString() {
        return "StockUpdatedEvent{" +
                "id='" + id + '\'' +
                '}';
    }
}
