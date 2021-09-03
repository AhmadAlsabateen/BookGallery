package com.gallery.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gallery.query.Input;
import com.gallery.query.Query;
import databaseConfig.DatabaseConnection;
import com.gallery.entities.Category;

import java.util.Arrays;
import java.util.List;

public class CategoryDao {
    private CategoryDao(){}
    public static CategoryDao getInstance(){
        return new CategoryDao();
    }



    public void create(Input input){
        Query query= new Query();
        query.input=input;
        query.Table="categories";
        query.type="create";
        DatabaseConnection.getInstance().executeQuery(query);
    }



    public Category getByName(String name)  {

        Query query = new Query();
        query.type = "retrieve";
        query.Table = "categories";
        query.filter.fields.put("name", name);
        ObjectMapper objectMapper = new ObjectMapper();
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        String result = databaseConnection.executeQuery(query);
        List<Category> category = null;
        try {
            category = Arrays.asList(objectMapper.readValue(result, Category[].class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (category.size()>0)
        return category.get(0);
        return null;
    }



    public Category getById(long id) {
        Query query = new Query();
        query.type = "retrieve";
        query.Table = "categories";
        query.filter.fields.put("id", id);
        ObjectMapper objectMapper = new ObjectMapper();
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        String result = databaseConnection.executeQuery(query);
        List<Category> category = null;
        try {
            category = Arrays.asList(objectMapper.readValue(result, Category[].class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (category.size()>0)
            return category.get(0);
         return null;
    }
}
