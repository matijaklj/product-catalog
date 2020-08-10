package com.demo.pc.stock.command;

import com.demo.pc.common.api.commands.AddProductStockCmd;
import com.demo.pc.common.api.commands.CreateProductStockCmd;
import com.demo.pc.common.api.commands.RemoveProductStockCmd;
import com.demo.pc.common.api.events.StockCreatedEvent;
import com.demo.pc.common.api.events.StockUpdatedEvent;
import com.demo.pc.stock.exceptions.InsufficientStockException;
import com.kumuluz.ee.kumuluzee.axon.stereotype.Aggregate;
import org.axonframework.commandhandling.CommandHandler;
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

        this.id = cmd.getId();
        this.productId = cmd.getProductId();
        this.quantity = cmd.getQuantity();

        // todo
        apply(new StockCreatedEvent(this.id, this.productId, this.quantity));
    }

    @CommandHandler
    public void addStock(AddProductStockCmd cmd) {
        this.quantity = this.quantity + cmd.getQuantity();

        apply(new StockUpdatedEvent(this.id, this.productId, this.quantity));
    }

    @CommandHandler
    public void removeFromStock(RemoveProductStockCmd cmd) throws InsufficientStockException {
        if (cmd.getQuantity() > this.quantity)
            throw new InsufficientStockException(cmd.getId(), cmd.getProductId());

        this.quantity = this.quantity - cmd.getQuantity();

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
