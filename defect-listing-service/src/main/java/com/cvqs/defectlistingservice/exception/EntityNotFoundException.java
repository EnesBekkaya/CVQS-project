package com.cvqs.defectlistingservice.exception;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String param) {
        super(param);
    }
}
