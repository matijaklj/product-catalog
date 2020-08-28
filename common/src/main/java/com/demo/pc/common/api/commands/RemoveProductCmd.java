package com.demo.pc.common.api.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.io.Serializable;

public class RemoveProductCmd implements Serializable {

    @TargetAggregateIdentifier
    private String id;

    public RemoveProductCmd(String id) {
        this.id = id;
    }

    public RemoveProductCmd() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "StockUpdatedEvent{" +
                "id='" + id + '\'' +
                '}';
    }
}
