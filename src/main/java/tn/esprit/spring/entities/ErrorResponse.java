package tn.esprit.spring.entities;

import tn.esprit.spring.serviceInterface.Response;

public class ErrorResponse implements Response {
    private final String error;

    public ErrorResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

}
