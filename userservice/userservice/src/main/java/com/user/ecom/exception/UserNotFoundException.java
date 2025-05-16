package com.user.ecom.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String email) {
        super("User With Email "+email+" Not found!!");
    }
}
