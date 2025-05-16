package com.product.ecom.exception;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(String message){
        super("Product :: "+message+" Not found !! Please check ");
    }
}
