package com.demo.pc.common.api.events;

public class PriceRemovedEvent {

    private String id;

    public PriceRemovedEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
