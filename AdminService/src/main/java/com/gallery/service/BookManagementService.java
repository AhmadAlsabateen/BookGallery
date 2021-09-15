package com.gallery.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.util.IOUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.gallery.dao.AuthorDao;
import com.gallery.dao.BookDao;
import com.gallery.dao.CategoryDao;
import com.gallery.entities.Author;
import com.gallery.entities.Category;
import com.gallery.query.Filter;
import com.gallery.query.Input;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class BookManagementService {

    private BookManagementService() {
    }

    public static BookManagementService getInstance() {
        return new BookManagementService();
    }

    public void updateBook(long id,String name, String category, String author, InputStream pdf,long pdfSize) throws JsonProcessingException {
        BookDao bookDao = BookDao.getInstance();
        Filter filter= new Filter();
        filter.fields.put("id",id);
        Input input= new Input();
        if (!category.equals("")){
        Long categoryID=getCategoryId(category);
        input.fields.put("categeryId",categoryID);
        }
        if (!author.equals("")){
        Long authorId=getAuthorId(author);
        input.fields.put("authorId",authorId);
        }

        if (!name.equals("")){
        input.fields.put("name",name);
        }

        if (pdfSize>100){
            System.out.println(pdfSize);
            String key = getFileKey(id);
            uploadtoS3(pdf,key);
        }
        bookDao.update(filter,input);

    }

    private String getFileKey(long id) throws JsonProcessingException {
        BookDao bookDao= BookDao.getInstance();
        Filter filter= new Filter();
        filter.fields.put("id",id);
        String key= bookDao.getByFilter(filter).get(0).fileName;
        return key;
    }

    public void deleteBook(long id){
        BookDao bookDao = BookDao.getInstance();
        Filter filter= new Filter();
        filter.fields.put("id",id);
        bookDao.delete(filter);
    }

    public void saveBook(String name, String category, String author, InputStream pdf) {
        String fileName = category + " - " + author + " - " + name+".pdf";
        uploadtoS3(pdf, fileName);
        Long categoryId = getCategoryId(category);
        Long authorId = getAuthorId(author);
        BookDao bookDao = BookDao.getInstance();
        Input input = new Input();
        input.fields.put("name", name);
        input.fields.put("fileName", fileName);
        input.fields.put("authorId", authorId);
        input.fields.put("categeryId", categoryId);
        bookDao.create(input);
    }


    private void uploadtoS3(InputStream pdf, String fileName) {
        String bucketName = "pdf-bucket-atypon-intern";

        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard().build();

            // Set the pre-signed URL to expire after one hour.
            java.util.Date expiration = new java.util.Date();
            long expTimeMillis = expiration.getTime();
            expTimeMillis += 1000 * 60 * 60;
            expiration.setTime(expTimeMillis);

            // Generate the pre-signed URL.
            System.out.println("Generating pre-signed URL.");
            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName)
                    .withMethod(HttpMethod.PUT)
                    .withExpiration(expiration);
            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

            // Create the connection and use it to upload the new object using the pre-signed URL.
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("PUT");
            OutputStream out = connection.getOutputStream();
            out.write(IOUtils.toByteArray(pdf));

            out.close();

            // Check the HTTP response code. To complete the upload and make the object available,
            // you must interact with the connection object in some way.
            connection.getResponseCode();
            System.out.println("HTTP response code: " + connection.getResponseCode());

        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Long getAuthorId(String authorName) {
        AuthorDao authorDao = AuthorDao.getInstance();
        Author author = authorDao.getByName(authorName);
        if (author != null) {
            return author.id;
        } else {
            Input input = new Input();
            input.fields.put("name", authorName);
            authorDao.create(input);
            author = authorDao.getByName(authorName);
            return author.id;
        }

    }

    private Long getCategoryId(String CategoryName) {
        CategoryDao categoryDao = CategoryDao.getInstance();
        Category category = categoryDao.getByName(CategoryName);
        if (category != null) {
            return category.id;
        } else {
            Input input = new Input();
            input.fields.put("name", CategoryName);
            categoryDao.create(input);
            category = categoryDao.getByName(CategoryName);
            return category.id;
        }

    }

}
