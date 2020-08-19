package com.demo.pc.read.queries;

public class FetchProductSummaryQuery {

    private final String productId;

    public FetchProductSummaryQuery(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }
}
