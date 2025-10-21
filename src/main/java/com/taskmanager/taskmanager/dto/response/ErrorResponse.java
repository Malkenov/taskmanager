package com.taskmanager.taskmanager.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {

    private HttpStatus status;
    private String message;
    private LocalDateTime localDateTime;

    public ErrorResponse(HttpStatus status, String message){
        this.status = status;
        this.message = message;
        this.localDateTime = LocalDateTime.now();
    }
}
