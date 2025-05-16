package com.user.ecom.exception;

public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException(String email){
        super("User with Email :: "+email+" already Exists!");
    }
}
