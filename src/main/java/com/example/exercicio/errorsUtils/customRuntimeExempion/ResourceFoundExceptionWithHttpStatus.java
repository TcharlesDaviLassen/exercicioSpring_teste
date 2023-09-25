package com.example.exercicio.errorsUtils.customRuntimeExempion;

import org.springframework.http.HttpStatus;

public class ResourceFoundExceptionWithHttpStatus extends RuntimeException {
    public ResourceFoundExceptionWithHttpStatus(HttpStatus httpStatus, String message) {
        super(message);
    }
}