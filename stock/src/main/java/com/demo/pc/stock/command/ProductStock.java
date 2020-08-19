package com.demo.pc.stock.command;

import com.demo.pc.common.api.commands.AddProductStockCmd;
import com.demo.pc.common.api.commands.CreateProductStockCmd;
import com.demo.pc.common.api.commands.RemoveProductStockCmd;
import com.demo.pc.common.api.events.StockAddedEvent;
import com.demo.pc.common.api.events.StockCreatedEvent;
import com.demo.pc.common.api.events.StockRemovedEvent;
import com.demo.pc.common.api.events.StockUpdatedEvent;
import com.demo.pc.stock.exceptions.InsufficientStockException;
import com.kumuluz.ee.kumuluzee.axon.stereotype.Aggregate;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.common.Assert;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;

import javax.enterprise.context.ApplicationScoped;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@ApplicationScoped
@Aggregate
public class ProductStock {

    @AggregateIdentifier
    private String id;
    private String productId;
    private int quantity;

    @CommandHandler
    public ProductStock(CreateProductStockCmd cmd) {
        Assert.isTrue(cmd.getQuantity() >= 0, () -> "Quantity should not be negative!");

        apply(new StockCreatedEvent(cmd.getId(), cmd.getProductId(), cmd.getQuantity()));
    }

    @CommandHandler
    public void addStock(AddProductStockCmd cmd) {
        Assert.isTrue(cmd.getQuantity() >= 0, () -> "Added quantity should not be negative!");

        apply(new StockAddedEvent(this.id, this.productId, this.quantity));
    }

    @CommandHandler
    public void removeFromStock(RemoveProductStockCmd cmd) throws InsufficientStockException {
        Assert.isTrue(cmd.getQuantity() >= 0, () -> "Remove quantity should not be negative!");

        if (cmd.getQuantity() > this.quantity)
            throw new InsufficientStockException(cmd.getId(), cmd.getProductId());

        apply(new StockRemovedEvent(this.id, this.productId, this.quantity));
    }

    @EventSourcingHandler
    public void on(StockCreatedEvent event) {
        this.id = event.getId();
        this.productId = event.getProductId();
        this.quantity = event.getQuantity();
    }

    @EventSourcingHandler
    public void on(StockAddedEvent event) {
        this.quantity = this.quantity + event.getQuantity();

        apply(new StockUpdatedEvent(this.id, this.productId, this.quantity));
    }

    @EventSourcingHandler
    public void on(StockRemovedEvent event) {
        this.quantity = this.quantity - event.getQuantity();

        apply(new StockUpdatedEvent(this.id, this.productId, this.quantity));
    }

    public ProductStock() {}

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
