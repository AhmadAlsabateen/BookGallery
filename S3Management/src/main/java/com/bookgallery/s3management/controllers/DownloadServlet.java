package com.bookgallery.s3management.controllers;

import com.bookgallery.s3management.model.service.S3BookDownloadService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/download")
public class DownloadServlet extends HttpServlet {


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        S3BookDownloadService downloadService = S3BookDownloadService.getInstance();

        Long bookId = Long.valueOf(request.getParameter("bookId"));
        response.setContentType(MediaType.TEXT_PLAIN);

        try {
            PrintWriter printWriter = response.getWriter();
            printWriter.print(downloadService.getPresignedBookDownloadURL(bookId));
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
