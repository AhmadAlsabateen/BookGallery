package com.gallery.entities;

public class Book {

    public long id;
    public String name;
    public String description;
    public String fileName;
    public long authorId;
    public long categeryId;


    public Book() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public long getCategeryId() {
        return categeryId;
    }

    public void setCategeryId(long categeryId) {
        this.categeryId = categeryId;
    }


}
