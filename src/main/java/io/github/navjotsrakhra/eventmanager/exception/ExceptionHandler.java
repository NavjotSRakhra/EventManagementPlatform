package io.github.navjotsrakhra.eventmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;


public class ExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler({MethodArgumentNotValidException.class, DateValidationFailedException.class})
    public List<String> handleValidationException(MethodArgumentNotValidException methodArgumentNotValidException, DateValidationFailedException dateValidationFailedException) {
        List<String> errors = new ArrayList<>();


        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach(error -> errors.add((error.getDefaultMessage())));

        return errors;
    }
}
