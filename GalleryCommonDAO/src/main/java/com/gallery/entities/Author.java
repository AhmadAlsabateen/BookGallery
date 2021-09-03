package com.gallery.entities;

public class Author  {
    public long id;
    public String name;

    public Author(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Author() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
