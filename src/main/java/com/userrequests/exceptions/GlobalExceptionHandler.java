package com.userrequests.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ErrorObject> handleException(RuntimeException ex) {
        ErrorObject errorObject = new ErrorObject();

        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnableToEditRequestException.class)
    public ResponseEntity<ErrorObject> handleUnableToEditRequestException(UnableToEditRequestException ex, WebRequest request) {
        return handleException(ex);
    }

    @ExceptionHandler(PermissionDeniedException.class)
    public ResponseEntity<ErrorObject> handlePermissionDeniedException(PermissionDeniedException ex, WebRequest request) {
        return handleException(ex);
    }
}
