package com.gallery.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gallery.entities.Book;
import com.gallery.query.Filter;
import com.gallery.query.Input;
import com.gallery.query.Query;
import databaseConfig.DatabaseConnection;

import java.util.Arrays;
import java.util.List;

public class BookDao {


    public static BookDao getInstance() {

        return new BookDao();
    }


    public List<Book> getByFilter(Filter filter) throws JsonProcessingException {
        Query query = new Query();
        query.type = "retrieve";
        query.Table = "books";
        query.filter = filter;
        ObjectMapper objectMapper = new ObjectMapper();
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        String result = databaseConnection.executeQuery(query);
        return Arrays.asList(objectMapper.readValue(result, Book[].class));
    }

    public void update(Filter filter, Input input) {
        Query query = new Query();
        query.type = "update";
        query.Table = "books";
        query.filter = filter;
        query.input = input;
        ObjectMapper objectMapper = new ObjectMapper();
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        databaseConnection.executeQuery(query);
    }


    public void delete(Filter filter) {
        Query query = new Query();
        query.type = "delete";
        query.Table = "books";
        query.filter = filter;
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        databaseConnection.executeQuery(query);
    }

    public void create(Input input) {
        Query query = new Query();
        query.input = input;
        query.Table = "books";
        query.type = "create";
        DatabaseConnection.getInstance().executeQuery(query);

    }

}


