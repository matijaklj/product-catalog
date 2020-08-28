package com.demo.pc.read.repositories;

import com.demo.pc.read.dtos.CategoryDto;
import com.demo.pc.read.dtos.PageItems;
import com.demo.pc.read.dtos.ProductDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.util.*;

import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.MongoClients;

@ApplicationScoped
public class ProductRepository {

    private MongoClient mongoClient;
    private MongoCollection<Document> collection;
    private ObjectMapper objectMapper;

    public ProductRepository() {
        Optional<String> connectionString = ConfigurationUtil.getInstance().get("kumuluzee.mongodb.connection-string");
        this.mongoClient = connectionString.map(MongoClients::create).orElseGet(MongoClients::create);

        MongoDatabase database = mongoClient.getDatabase("product-catalog");
        this.collection = database.getCollection("products");
        this.objectMapper = new ObjectMapper();

        collection.createIndex(new Document("id", 1), new IndexOptions().unique(true));
    }

    public void insertNewProduct(ProductDto p) {
        try {
            // get ProductDto object as a json string
            String jsonStr = objectMapper.writeValueAsString(p);

            Document d = Document.parse(jsonStr);

            collection.insertOne(d);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ProductDto
    getProduct(String id) {
        return toDto(collection.find(eq("id", id)).projection(new Document("_id", false)).first(), objectMapper);
    }

    public PageItems<ProductDto> getProducts(int skip, int take, String search) {

        List<ProductDto> productDtos = new ArrayList<>();
        int count = 0;
        if (search != null) {
            Document query = new Document("name", new Document("$eq", search));
            count = (int)collection.countDocuments(query);

            collection.find(eq("name", search))
                    .skip(skip).limit(take).projection(new Document("_id", false))
                    .forEach(d -> productDtos.add(toDto(d, objectMapper)));
        } else {
            count = (int)collection.countDocuments();

            collection.find()
                    .skip(skip).limit(take).projection(new Document("_id", false))
                    .forEach(d -> productDtos.add(toDto(d, objectMapper)));
        }
        return new PageItems<>(productDtos, productDtos.size(), count);
    }

    public void updateProductStock(String id, int quantity) {
        ProductDto p = getProduct(id);
        p.setStock(quantity);

        updateProduct(p);
    }

    public void addProductCategory(String id, CategoryDto c) {
        ProductDto p = getProduct(id);
        p.getCategories().add(c);

        updateProduct(p);
    }

    public void removeProductCategory(String id, CategoryDto c) {
        ProductDto p = getProduct(id);
        p.getCategories().remove(c);

        updateProduct(p);
    }

    public void updateProductPrice(String id, float price) {
        ProductDto p = getProduct(id);
        p.setPrice(price);

        updateProduct(p);
    }

    public void editProduct(ProductDto p) {
        ProductDto pOld = getProduct(p.getId());
        pOld.setDescription(p.getDescription());
        pOld.setName(p.getName());
    }

    private void updateProduct(ProductDto p) {
        try {
            String jsonStr = objectMapper.writeValueAsString(p);

            Document d = Document.parse(jsonStr);
            collection.replaceOne(eq("id", p.getId()), d); // try replaceOne
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ProductDto toDto(Document document, ObjectMapper objectMapper) {
        try {
            ProductDto p = objectMapper.readValue(document.toJson(), ProductDto.class);
            return p;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
