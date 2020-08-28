package com.demo.pc.pricing.command;

import com.demo.pc.common.api.commands.*;
import com.demo.pc.common.api.events.PriceCreatedEvent;
import com.demo.pc.common.api.events.PriceRemovedEvent;
import com.demo.pc.common.api.events.PriceUpdatedEvent;
import com.kumuluz.ee.kumuluzee.axon.stereotype.Aggregate;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.common.Assert;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;

import javax.enterprise.context.ApplicationScoped;

import java.util.Date;
import java.util.logging.Logger;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;
import static org.axonframework.modelling.command.AggregateLifecycle.markDeleted;

@ApplicationScoped
@Aggregate
public class ProductPricing {

    private static final Logger logger = Logger.getLogger(ProductPricing.class.getName());

    @AggregateIdentifier
    private String id;
    private String productId;
    private float value;
    private Date validFrom;
    private Date validTo;

    @CommandHandler
    public ProductPricing(CreateProductPriceCmd cmd) {
        Assert.isTrue(cmd.getValue() > 0, () -> "Price value must not be negative.");

        apply(new PriceCreatedEvent(cmd.getId(), cmd.getProductId(),
                cmd.getValue(), cmd.getValidFrom(), cmd.getValidTo()));
    }

    @CommandHandler
    public void updatePrice(UpdateProductPriceCmd cmd) {
        Assert.isTrue(cmd.getValue() > 0, () -> "Price value must not be negative.");

        apply(new PriceUpdatedEvent(cmd.getId(), cmd.getProductId(),
                cmd.getValue(), cmd.getValidFrom(), cmd.getValidTo()));
    }

    @CommandHandler
    public void removePrice(RemoveProductPriceCmd cmd) {
        apply(new PriceRemovedEvent(cmd.getId()));
    }

    @EventSourcingHandler
    public void on(PriceCreatedEvent event) {
        logger.info("Created Product price id :: " + event.getId() + " price :: " + event.getValue());
        this.id = event.getId();
        this.productId = event.getProductId();
        this.value = event.getValue();
        this.validFrom = event.getValidFrom();
        this.validTo = event.getValidTo();
    }

    @EventSourcingHandler
    public void on(PriceUpdatedEvent event) {
        logger.info("Updated Product price id :: " + event.getId() + " price :: " + event.getValue());

        this.value = event.getValue();
        this.validFrom = event.getValidFrom();
        this.validTo = event.getValidTo();
    }

    @EventSourcingHandler
    public void on(PriceRemovedEvent event) {
        markDeleted();
    }

    public ProductPricing() {}

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
}
