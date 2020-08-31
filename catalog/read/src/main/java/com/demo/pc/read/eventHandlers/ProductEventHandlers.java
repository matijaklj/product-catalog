package com.demo.pc.read.eventHandlers;

import com.demo.pc.common.api.events.*;
import com.demo.pc.read.dtos.CategoryDto;
import com.demo.pc.read.dtos.ProductDto;
import com.demo.pc.read.repositories.CategoryRepository;
import com.demo.pc.read.repositories.ProductRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;

@ApplicationScoped
@ProcessingGroup("products")
public class ProductEventHandlers {

    private static final Logger logger = Logger.getLogger(ProductEventHandlers.class.getName());

    @Inject
    private ProductRepository productRepository;

    @Inject
    private CategoryRepository categoryRepository;

    @EventHandler
    public void handleProductCreated(ProductCreatedEvent event) {
        logger.info("Handling event ProductCreatedEvent :: " + '\'' + event.toString());
        productRepository.insertNewProduct(new ProductDto(event.getId(), event.getName(), event.getDescription()));
    }

    @EventHandler
    public void handleProductUpdated(ProductUpdatedEvent event) {
        logger.info("Handling event ProductUpdatedEvent :: " + '\'' + event.toString());

        productRepository.editProduct(new ProductDto(event.getId(), event.getName(), event.getDescription()));
    }

    @EventHandler
    public void handleStockUpdate(StockCreatedEvent event) {
        logger.info("Handling event StockCreatedEvent :: " + '\'' + event.toString());

        productRepository.updateProductStock(event.getProductId(), event.getQuantity());
    }

    @EventHandler
    public void handleStockUpdate(StockUpdatedEvent event) {
        logger.info("Handling event StockUpdatedEvent :: " + '\'' + event.toString());

        productRepository.updateProductStock(event.getProductId(), event.getQuantity());
    }

    @EventHandler
    public void handleProductCategoryAdded(ProductCategoryAddedEvent event) {
        logger.info("Handling event ProductCategoryAddedEvent :: " + '\'' + event.toString());
        CategoryDto c = categoryRepository.getCategory(event.getCategoryId());

        productRepository.addProductCategory(event.getId(), c);
    }

    @EventHandler
    public void handleProductCategoryRemoved(ProductCategoryRemovedEvent event) {
        CategoryDto c = categoryRepository.getCategory(event.getCategoryId());

        productRepository.removeProductCategory(event.getId(), c);
    }

    @EventHandler
    public void handlePriceCreated(PriceCreatedEvent event) {
        logger.info("Handling event StockUpdatedEvent :: " + '\'' + event.toString());
        productRepository.updateProductPrice(event.getProductId(), event.getValue());
    }

    @EventHandler
    public void handlePriceUpdated(PriceUpdatedEvent event) {
        productRepository.updateProductPrice(event.getProductId(), event.getValue());
    }
    
    
}
