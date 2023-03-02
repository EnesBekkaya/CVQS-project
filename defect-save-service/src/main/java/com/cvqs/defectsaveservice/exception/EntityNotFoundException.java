package com.cvqs.defectsaveservice.exception;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String param) {
        super(param);
    }
}
