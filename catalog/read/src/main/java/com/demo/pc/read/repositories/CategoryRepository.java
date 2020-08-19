package com.demo.pc.read.repositories;

import com.demo.pc.read.dtos.CategoryDto;
import com.demo.pc.read.dtos.PageItems;
import com.demo.pc.read.dtos.ProductDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import org.bson.Document;

import javax.enterprise.context.ApplicationScoped;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

@ApplicationScoped
public class CategoryRepository {

    private MongoClient mongoClient;
    private MongoCollection<Document> collection;
    private ObjectMapper objectMapper;

    public CategoryRepository() {
        this.mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("product-catalog");
        this.collection = database.getCollection("categories");
        this.objectMapper = new ObjectMapper();

        collection.createIndex(new Document("id", 1), new IndexOptions().unique(true));
    }

    public void insertNewCategory(CategoryDto p) {
        try {
            String jsonStr = objectMapper.writeValueAsString(p);

            Document d = Document.parse(jsonStr);

            collection.insertOne(d);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CategoryDto getCategory(String id) {
        return toDto(collection.find(eq("id", id)).projection(new Document("_id", false)).first(), objectMapper);
    }

    public List<CategoryDto> getCategories() {

        List<CategoryDto> categoryDtos = new ArrayList<>();

        collection.find().forEach(d -> categoryDtos.add(toDto(d, objectMapper)));

        return categoryDtos;
    }


    private static CategoryDto toDto(Document document, ObjectMapper objectMapper) {
        try {
            CategoryDto d = objectMapper.readValue(document.toJson(), CategoryDto.class);
            return d;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
