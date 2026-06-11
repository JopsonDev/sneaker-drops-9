package com.pluralsight.sneakerdrop.sneakerdrops.service;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message){
        super(message);
    }
}
