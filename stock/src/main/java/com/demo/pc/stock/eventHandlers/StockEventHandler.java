package com.demo.pc.stock.eventHandlers;

import com.demo.pc.common.api.commands.CreateProductStockCmd;
import com.demo.pc.common.api.events.ProductCreatedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@ApplicationScoped
public class StockEventHandler {

    @Inject
    private CommandGateway commandGateway;

    /*
    @EventHandler
    public void handle(ProductCreatedEvent event) throws InterruptedException, ExecutionException {
        CompletableFuture<String> futureResult = commandGateway.send(new CreateProductStockCmd(event.getId(), event.getId(), 0));

        futureResult.get();
    }
     */
}
