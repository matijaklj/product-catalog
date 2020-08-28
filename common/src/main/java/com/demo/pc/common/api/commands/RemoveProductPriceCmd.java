package com.demo.pc.common.api.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.io.Serializable;
import java.util.Date;

public class RemoveProductPriceCmd implements Serializable {

    @TargetAggregateIdentifier
    private String id;

    public RemoveProductPriceCmd(String id) {
        this.id = id;
    }

    public RemoveProductPriceCmd() {}

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
