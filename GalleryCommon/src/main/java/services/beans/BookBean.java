package services.beans;

import com.gallery.entities.Author;
import com.gallery.entities.Category;

public class BookBean {
    public long id;
    public String name;
    public String description;
    public String fileName;
    public Author author;
    public Category category;

    public BookBean() {
    }

    public BookBean(long id, String name, String description, String fileName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.fileName = fileName;
    }

    public BookBean(long id) {
        this.id = id;
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
