package tn.esprit.spring.exceptions;

public class BadWordException extends RuntimeException {
    public BadWordException(String message) {
        super(message);
    }
}
