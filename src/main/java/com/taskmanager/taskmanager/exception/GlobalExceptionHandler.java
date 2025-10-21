package com.taskmanager.taskmanager.exception;

import com.taskmanager.taskmanager.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IncorrectDataException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(IncorrectDataException ex) {
        ErrorResponse exception = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                ex.getMessage());
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }


}
