package com.taskmanager.taskmanager.exception;

import lombok.Getter;

public class IncorrectDataException extends RuntimeException {
    public IncorrectDataException(String message) {
        super(message);
    }
}
