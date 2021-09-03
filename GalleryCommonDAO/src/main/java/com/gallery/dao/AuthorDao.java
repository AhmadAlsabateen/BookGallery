package com.gallery.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gallery.query.Filter;
import com.gallery.query.Input;
import com.gallery.query.Query;
import databaseConfig.DatabaseConnection;

import com.gallery.entities.Author;

import java.util.Arrays;
import java.util.List;

public class AuthorDao {

    private AuthorDao(){ }

    public static AuthorDao getInstance(){
        return new AuthorDao();
    }

    public void create(Input input){
        Query query= new Query();
        query.input=input;
        query.Table="authors";
        query.type="create";
        DatabaseConnection.getInstance().executeQuery(query);
    }



    public Author getByName(String name)  {

            Query query = new Query();
            query.type = "retrieve";
            query.Table = "authors";
            query.filter.fields.put("name", name);
            ObjectMapper objectMapper = new ObjectMapper();
            DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
            String result = databaseConnection.executeQuery(query);
            List<Author> author = null;
        try {
            author = Arrays.asList(objectMapper.readValue(result, Author[].class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (author.size()>0)
        return author.get(0);
        return null;
    }

    public Author getByID(long id)  {
        Query query = new Query();
        query.type = "retrieve";
        query.Table = "authors";
        query.filter.fields.put("id", id);
        ObjectMapper objectMapper = new ObjectMapper();
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        String result = databaseConnection.executeQuery(query);
        List<Author> author = null;
        try {
            author = Arrays.asList(objectMapper.readValue(result, Author[].class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return author.get(0);
    }
}
