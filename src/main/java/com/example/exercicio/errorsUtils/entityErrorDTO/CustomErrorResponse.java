package com.example.exercicio.errorsUtils.entityErrorDTO;

public class CustomErrorResponse {
    private String error;
    private String message;
    private String details;

    public CustomErrorResponse() {
    }

    public CustomErrorResponse(String error, String message, String details) {
        this.error = error;
        this.message = message;
        this.details = details;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}