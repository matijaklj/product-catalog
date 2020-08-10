package com.demo.pc.read.eventHandlers;

import com.demo.pc.common.api.events.ProductCategoryAddedEvent;
import com.demo.pc.common.api.events.ProductCreatedEvent;
import com.demo.pc.read.command.CategoryDto;
import com.demo.pc.read.command.ProductDto;
import org.axonframework.eventhandling.EventHandler;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

@ApplicationScoped
public class ProductEventHandlers {

    @Inject
    ConcurrentMap<String, ProductDto> productDBMap;

    @Inject
    ConcurrentMap<String, CategoryDto> categoryDBMap;

    @EventHandler
    public void handleProductCreated(ProductCreatedEvent evt) {
        productDBMap.putIfAbsent(evt.getId(), new ProductDto(evt.getId(), evt.getName(), evt.getDescription()));
    }

    @EventHandler
    public void handleProductCategoryAdded(ProductCategoryAddedEvent evt) {
        ProductDto p = productDBMap.get(evt.getId());
        CategoryDto c = categoryDBMap.get(evt.getCategoryId());

        List<CategoryDto> categoryDtoList = p.getCategories();
        categoryDtoList.add(c);

        p.setCategories(categoryDtoList);
    }
}
