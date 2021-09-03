package com.bookgallery.s3management.model.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import services.BookService;
import services.beans.BookBean;

import java.net.URL;
import java.time.Instant;

public class S3BookDownloadService {

    public static S3BookDownloadService getInstance() {
        return new S3BookDownloadService();
    }

    public String getPresignedBookDownloadURL(Long bookId) {

        try {

            BookBean book = BookService.getInstance().getBookById(bookId);
            String bucketName = "pdf-bucket-atypon-intern";
            String objectKey = book.fileName;

            AmazonS3 s3Client = AmazonS3ClientBuilder.standard().build();

            // Set the presigned URL to expire after one hour.
            java.util.Date expiration = new java.util.Date();
            long expTimeMillis = Instant.now().toEpochMilli();
            expTimeMillis += 1000 * 60 * 60;
            expiration.setTime(expTimeMillis);

            // Generate the presigned URL.
            System.out.println("Generating pre-signed URL.");
            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucketName, objectKey)
                            .withMethod(HttpMethod.GET)
                            .withExpiration(expiration);
            URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

            System.out.println("Pre-Signed Download URL: " + url.toString());

            return url.toString();
        } catch (AmazonServiceException e) {
            // The call was transmitted successfully, but Amazon S3 couldn't process
            // it, so it returned an error response.
            e.printStackTrace();
        } catch (SdkClientException e) {
            // Amazon S3 couldn't be contacted for a response, or the client
            // couldn't parse the response from Amazon S3.
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        return "https://www.w3schools.com/images/myw3schoolsimage.jpg";
    }
}
