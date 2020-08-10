package com.demo.pc.common.api.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.io.Serializable;
import java.util.Date;

public class RemoveProductPriceCmd implements Serializable {

    @TargetAggregateIdentifier
    private String id;
    private String productId;

    public RemoveProductPriceCmd(String id, String productId) {
        this.id = id;
        this.productId = productId;
    }

    public RemoveProductPriceCmd() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
