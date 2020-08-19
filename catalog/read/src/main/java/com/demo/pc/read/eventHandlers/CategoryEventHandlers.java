package com.demo.pc.read.eventHandlers;

import com.demo.pc.common.api.events.CategoryCreatedEvent;
import com.demo.pc.read.dtos.CategoryDto;
import com.demo.pc.read.repositories.CategoryRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.ConcurrentMap;

@ApplicationScoped
@ProcessingGroup("categories")
public class CategoryEventHandlers {

    @Inject
    private CategoryRepository categoryRepository;

    @EventHandler
    public void handleCategoryCreated(CategoryCreatedEvent evt) {
        categoryRepository.insertNewCategory(new CategoryDto(evt.getId(), evt.getName()));
    }
}
