package com.gallery.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gallery.entities.User;
import com.gallery.query.Filter;
import com.gallery.query.Input;
import com.gallery.query.Query;
import databaseConfig.DatabaseConnection;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDao {

    private UserDao(){}
    public static UserDao getInstance(){
        return new UserDao();
    }

    public void create(Input input){
        Query query= new Query();
        query.input=input;
        query.Table="users";
        query.type="create";
        DatabaseConnection.getInstance().executeQuery(query);
    }

    public User get(Filter filter){
        Query query= new Query();

        query.filter=filter;
        query.Table="users";
        query.type="retrieve";
        ObjectMapper objectMapper = new ObjectMapper();
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance();
        String result = databaseConnection.executeQuery(query);
        try {
            List<User> user = Arrays.asList(objectMapper.readValue(result, User[].class));
            if (user.size()==0)
                 return null;
            return user.get(0);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
