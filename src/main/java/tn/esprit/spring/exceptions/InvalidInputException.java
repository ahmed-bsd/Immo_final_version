package tn.esprit.spring.exceptions;

import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

public class InvalidInputException extends RuntimeException {

    private final List<FieldError> errors;

    public InvalidInputException(List<FieldError> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors.stream()
                .map(error -> String.format("%s: %s", error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
    }
}
