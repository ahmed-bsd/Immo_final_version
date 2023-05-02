package tn.esprit.spring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MaxSizeExceededException extends RuntimeException {
    public MaxSizeExceededException(String message) {
        super(message);
    }
}
