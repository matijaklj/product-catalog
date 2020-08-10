package com.demo.pc.common.api.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.io.Serializable;

public class RemoveCategoryCmd implements Serializable {

    @TargetAggregateIdentifier
    private String id;

    public RemoveCategoryCmd(String id) {
        this.id = id;
    }

    public RemoveCategoryCmd() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
