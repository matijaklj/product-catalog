package com.demo.pc.common.api.events;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class CategoryCreatedEvent {

    private String id;
    private String name;

    public CategoryCreatedEvent(String id, String name) {
        this.id = id;
        this.name = name;
    }

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
