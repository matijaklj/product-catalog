package com.demo.pc.write.entities;

import com.demo.pc.common.api.commands.AddProductCategoryCmd;
import com.demo.pc.common.api.commands.CreateProductCmd;
import com.demo.pc.common.api.commands.UpdateProductCmd;
import com.demo.pc.common.api.events.ProductCategoryAddedEvent;
import com.demo.pc.common.api.events.ProductCreatedEvent;
import com.demo.pc.common.api.events.ProductUpdatedEvent;
import com.demo.pc.common.api.exceptions.CategoryAlreadyAddedException;
import com.kumuluz.ee.kumuluzee.axon.stereotype.Aggregate;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.Set;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

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

    //@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    /*@JoinTable(
            name = "Product_Category",
            joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")}
    )
    private Set<Category> categories = new HashSet<>();
     */

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    public Product() {}

    @CommandHandler
    public Product(CreateProductCmd cmd) {
        logger.info("Creating product ID= " + cmd.getId() + ".");
        this.id = cmd.getId();
        this.name = cmd.getName();
        this.description = cmd.getDescription();

        apply(new ProductCreatedEvent(cmd.getId(),
                cmd.getName(),
                cmd.getDescription()));
    }

    @CommandHandler
    public void editProduct(UpdateProductCmd cmd) {
        logger.info("Updating product ID= " + cmd.getId() + ".");
        this.name = cmd.getName();
        this.description = cmd.getDescription();

        apply(new ProductUpdatedEvent(cmd.getId(),
                cmd.getName(),
                cmd.getDescription()));
    }

    @CommandHandler
    public void addCategory(AddProductCategoryCmd cmd) throws CategoryAlreadyAddedException {
        logger.info("Adding category id: " + cmd.getCategoryId() + " to Product id: " + cmd.getId() + ".");
        this.category = new Category(cmd.getCategoryId());

        apply(new ProductCategoryAddedEvent(cmd.getId(), cmd.getCategoryId()));
        /*if (categories.stream().noneMatch(c -> c.getId().equals(cmd.getCategoryId()))) {
            this.categories.add(new Category(cmd.getCategoryId()));

            apply(new ProductCategoryAddedEvent(cmd.getId(), cmd.getCategoryId()));
        } else {
            throw new CategoryAlreadyAddedException();
        }*/

    }

    public Category getCategories() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '}';
    }
}