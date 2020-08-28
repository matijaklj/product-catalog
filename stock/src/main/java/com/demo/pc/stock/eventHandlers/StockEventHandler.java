package com.demo.pc.stock.eventHandlers;

import com.demo.pc.common.api.commands.CreateProductStockCmd;
import com.demo.pc.common.api.events.ProductCreatedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

@ApplicationScoped
public class StockEventHandler {

    private static final Logger logger = Logger.getLogger(StockEventHandler.class.getName());

    @Inject
    private CommandGateway commandGateway;

    @EventHandler
    public void handle(ProductCreatedEvent event) throws InterruptedException, ExecutionException {
        String stockId = event.getId() + "_stock";

        logger.info("Product created id: " + event.getId() + " - creating stock id: " + stockId + ".");
        CompletableFuture<String> futureResult = commandGateway.send(new CreateProductStockCmd(stockId, event.getId(), 0));

        futureResult.get();
    }
}
