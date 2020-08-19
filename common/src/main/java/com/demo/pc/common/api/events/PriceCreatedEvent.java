package com.demo.pc.common.api.events;

import java.util.Date;

public class PriceCreatedEvent {

    private String id;
    private String productId;
    private float value;
    private Date validFrom;
    private Date validTo;

    public PriceCreatedEvent(String id, String productId, float value, Date validFrom, Date validTo) {
        this.id = id;
        this.productId = productId;
        this.value = value;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

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
