package com.demo.pc.write.entities;

import com.demo.pc.common.api.commands.CreateCategoryCmd;
import com.demo.pc.common.api.events.CategoryCreatedEvent;
import com.kumuluz.ee.kumuluzee.axon.stereotype.Aggregate;
import org.axonframework.commandhandling.CommandHandler;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;
import static org.axonframework.modelling.command.AggregateLifecycle.createNew;

@Entity
@ApplicationScoped
@Aggregate
public class Category {

    @Id
    private String id;
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "Product_Category",
            joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")}
    )
    private Set<Product> products = new HashSet<>();


    @CommandHandler
    public Category(CreateCategoryCmd cmd) {

        this.id = cmd.getId();
        this.name = cmd.getName();

        apply(new CategoryCreatedEvent(this.id, this.name));
    }

    public Category(String id) {
        this.id = id;
    }

    public Category() {}

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

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
