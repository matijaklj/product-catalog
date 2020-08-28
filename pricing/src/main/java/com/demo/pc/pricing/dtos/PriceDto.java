package com.demo.pc.pricing.dtos;

import java.io.Serializable;
import java.util.Date;

public class PriceDto implements Serializable {
    private String productId;
    private float value;
    /*
    private Date validFrom;
    private Date validTo;
     */

    public PriceDto() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
    /*
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
     */
}
