package com.demo.pc.stock.exceptions;

public class InsufficientStockException extends Exception {

    private String id;
    private String productId;

    public InsufficientStockException(String id, String productId) {
        this.id = id;
        this.productId = productId;
    }

    @Override
    public String getMessage() {
        return "InsufficientStockException :: " + this.id +
                " :: Insufficient Stock quantity for product id: " + this.productId;
    }
}
