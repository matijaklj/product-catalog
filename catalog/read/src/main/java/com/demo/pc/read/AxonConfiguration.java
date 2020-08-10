package com.demo.pc.read;

import com.demo.pc.read.command.CategoryDto;
import com.demo.pc.read.command.ProductDto;
import org.axonframework.config.Configurer;
import org.axonframework.config.DefaultConfigurer;
import org.mapdb.DB;
import org.mapdb.DBMaker;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import java.util.concurrent.ConcurrentMap;

@ApplicationScoped
public class AxonConfiguration {

    @Produces
    public ConcurrentMap<String, ProductDto> productDBMap() {
        DB querySideDB = DBMaker.memoryDB().make();
        return (ConcurrentMap<String, ProductDto>) querySideDB.hashMap("productDBMap").createOrOpen();
    }

    @Produces
    public ConcurrentMap<String, CategoryDto> categoryDBMap() {
        DB querySideDB = DBMaker.memoryDB().make();
        return (ConcurrentMap<String, CategoryDto>) querySideDB.hashMap("categoryDBMap").createOrOpen();
    }


    @Produces
    @ApplicationScoped
    public Configurer configurer() {
        Configurer configurer = DefaultConfigurer.defaultConfiguration();

        return configurer;
    }
}
