package com.gallery.controllers;

import com.amazonaws.util.IOUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.gallery.service.BookManagementService;
import services.BookService;
import services.beans.BookBean;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

@WebServlet(urlPatterns = "/adminHome")
@MultipartConfig
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

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        BookManagementService bookManagementService = BookManagementService.getInstance();

        try {

            String namePart = IOUtils.toString(request.getPart("Name").getInputStream());
            String categoryPart = IOUtils.toString(request.getPart("Category").getInputStream());
            String authorPart = IOUtils.toString(request.getPart("Author").getInputStream());
            InputStream filePart = request.getPart("File").getInputStream();
            bookManagementService.saveBook(namePart, categoryPart, authorPart, filePart);
            response.sendRedirect("/Admin/adminHome");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) {


        try {
            if (isUpdateRequest(request)) {
                BookManagementService bookManagementService=BookManagementService.getInstance();
                String namePart = IOUtils.toString(request.getPart("Name").getInputStream());
                String categoryPart = IOUtils.toString(request.getPart("Category").getInputStream());
                String authorPart = IOUtils.toString(request.getPart("Author").getInputStream());
                Long bookId = Long.parseLong(IOUtils.toString(request.getPart("bookId").getInputStream()));
                InputStream filePart = request.getPart("File").getInputStream();
                long size=request.getPart("File").getSize();
                bookManagementService.updateBook(bookId, namePart, categoryPart, authorPart, filePart,size);
            }
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) {


    }


    private boolean isSearchRequest(HttpServletRequest request) {
        return ((request.getParameterMap().containsKey("Name") && request.getParameterMap().containsKey("Author") &&
                request.getParameterMap().containsKey("Category")) &&
                (request.getParameter("Name") != "" || request.getParameter("Author") != "" ||
                        request.getParameter("Category") != ""));
    }
    private boolean isUpdateRequest(HttpServletRequest request) throws ServletException, IOException {
        return ((!request.getPart("Name").getInputStream().equals("") ||
                 !request.getPart("Author").getInputStream().equals("") ||
                 !request.getPart("Category").getInputStream().equals("")||
                 !request.getPart("id").getInputStream().equals("")||
                  request.getPart("File").getSize()!=0));
    }


}
