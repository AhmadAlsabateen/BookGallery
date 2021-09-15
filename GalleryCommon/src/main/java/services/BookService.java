package services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gallery.dao.AuthorDao;
import com.gallery.dao.BookDao;
import com.gallery.dao.CategoryDao;
import com.gallery.entities.Book;
import com.gallery.exception.InvalidBookIdException;
import com.gallery.query.Filter;
import services.beans.BookBean;
import services.exception.BookNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class BookService {

    private BookService() {
    }

    public static BookService getInstance() {
        return new BookService();
    }


    public BookBean getBookById(Long id) throws JsonProcessingException, BookNotFoundException {
        if (id == null) {
            throw new InvalidBookIdException("Invalid BookId : " + id);
        }

        Filter filter = new Filter();
        filter.fields.put("id", id);

        List<Book> books = BookDao.getInstance().getByFilter(filter);

        if (books.isEmpty()) {
            throw new BookNotFoundException("Book with Id " + id + " not found");
        }

        return buildBook(books.get(0));

    }

    public ArrayList<BookBean> getSearchResult(String name, String author, String category) throws JsonProcessingException {

        Filter filter = new Filter();

        if (author != "") {
            if (AuthorDao.getInstance().getByName(author)!= null){
                Long authorID = AuthorDao.getInstance().getByName(author).getId();
                filter.fields.put("authorId", authorID);
            }
        }

        if (category != "") {
            if (CategoryDao.getInstance().getByName(category) != null) {
                Long categoryID = CategoryDao.getInstance().getByName(category).getId();
                filter.fields.put("categeryId", categoryID);
            }
        }

        if (name != "") {
            filter.fields.put("name", name);
        }
        ArrayList<BookBean> result = new ArrayList<>();
        if (filter.fields.containsKey("categeryId")||filter.fields.containsKey("authorId")||filter.fields.containsKey("name")){
        List<Book> books = BookDao.getInstance().getByFilter(filter);
            for (Book dbBook : books) {
                result.add(buildBook(dbBook));
            }
        }

        return result;

    }


    private BookBean buildBook(Book dbBook) {
        BookBean book = new BookBean(dbBook.id, dbBook.name, dbBook.description, dbBook.fileName);
        book.setAuthor(AuthorDao.getInstance().getByID(dbBook.authorId));
        book.setCategory(CategoryDao.getInstance().getById(dbBook.categeryId));
        return book;
    }

}
