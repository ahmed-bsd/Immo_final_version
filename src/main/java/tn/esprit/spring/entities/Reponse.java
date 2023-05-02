package tn.esprit.spring.entities;

import tn.esprit.spring.serviceInterface.Response;

public class Reponse implements Response {
    private String message;

    public Reponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
