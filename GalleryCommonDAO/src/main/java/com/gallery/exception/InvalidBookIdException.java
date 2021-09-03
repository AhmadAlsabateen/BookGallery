package com.gallery.exception;

public class InvalidBookIdException extends RuntimeException{


    public InvalidBookIdException(String message){
        super(message);
    }
}
