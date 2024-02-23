package org.example.Exceptions;

public class BookAlreadyExistsException extends Exception{
    public BookAlreadyExistsException(String msg){
        super(msg);
    }
}