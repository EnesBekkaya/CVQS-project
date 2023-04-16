package com.cvqs.defectsaveservice.exception;

public class FailedSaveException extends RuntimeException{
    public FailedSaveException(String param) {
        super(param);
    }
}
