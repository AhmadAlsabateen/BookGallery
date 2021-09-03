package com.gallery.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import services.BookService;
import services.beans.BookBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/userHome")
public class BooksServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        if (isSearchRequest(request)) {
            String name = request.getParameter("Name");
            String author = request.getParameter("Author");
            String category = request.getParameter("Category");
            BookService bookService = BookService.getInstance();
            try {
                ArrayList<BookBean> books = bookService.getSearchResult(name, author, category);
                request.setAttribute("books", books);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        try {
            request.getRequestDispatcher("/WEB-INF/views/Home.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }

    }

    private boolean isSearchRequest(HttpServletRequest request) {
        return ((request.getParameterMap().containsKey("Name") && request.getParameterMap().containsKey("Author") &&
                request.getParameterMap().containsKey("Category")) &&
                (request.getParameter("Name") != "" || request.getParameter("Author") != "" ||
                        request.getParameter("Category") != ""));
    }
}