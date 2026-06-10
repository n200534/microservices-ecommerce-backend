package com.akshay.productservice.exception;

public class ProductNotFoundException
        extends RuntimeException {

    public ProductNotFoundException(
            String message
    ) {
        super(message);
    }
}