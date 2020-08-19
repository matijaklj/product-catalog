package com.demo.pc.write.entities;

import com.demo.pc.common.api.commands.AddProductCategoryCmd;
import com.demo.pc.common.api.commands.CreateCategoryCmd;
import com.demo.pc.common.api.commands.CreateProductCmd;
import com.demo.pc.common.api.events.CategoryCreatedEvent;
import com.demo.pc.common.api.events.ProductCategoryAddedEvent;
import com.demo.pc.common.api.events.ProductCreatedEvent;
import com.kumuluz.ee.kumuluzee.axon.stereotype.Aggregate;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.config.Configuration;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.modelling.command.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.*;
import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;
import static org.axonframework.modelling.command.AggregateLifecycle.createNew;

@Entity
@Aggregate
@ApplicationScoped
public class Product implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(
            MethodHandles.lookup().lookupClass());

    @Id
    @AggregateIdentifier
    private String id;
    private String name;
    private String description;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "Product_Category",
            joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")}
    )
    private Set<Category> categories = new HashSet<>();

    public Product() {}

    @CommandHandler
    public Product(CreateProductCmd cmd) {
        logger.info("Creating prodcut ID= " + cmd.getId());
        this.id = cmd.getId();
        this.name = cmd.getName();
        this.description = cmd.getDescription();

        apply(new ProductCreatedEvent(cmd.getId(),
                cmd.getName(),
                cmd.getDescription()));
    }

    @CommandHandler
    public void addCategory(AddProductCategoryCmd cmd) {
        this.categories.add(new Category(cmd.getCategoryId()));

        apply(new ProductCategoryAddedEvent(cmd.getId(), cmd.getCategoryId()));
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}