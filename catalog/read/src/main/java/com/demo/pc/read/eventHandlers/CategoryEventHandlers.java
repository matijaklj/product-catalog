package com.demo.pc.read.eventHandlers;

import com.demo.pc.common.api.events.CategoryCreatedEvent;
import com.demo.pc.read.command.CategoryDto;
import org.axonframework.eventhandling.EventHandler;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.ConcurrentMap;

@ApplicationScoped
public class CategoryEventHandlers {

    @Inject
    ConcurrentMap<String, CategoryDto> categoryDBMap;

    @EventHandler
    public void handleCategoryCreated(CategoryCreatedEvent evt) {
        categoryDBMap.putIfAbsent(evt.getId(), new CategoryDto(evt.getId(), evt.getName()));
    }
}
