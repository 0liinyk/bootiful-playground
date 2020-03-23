package com.oliynyk.play.model;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
public class ApiException extends RuntimeException{
    HttpStatus statusCode;
    int errorCode;
    String message;
    Map<String, Object> details;

    public ApiException(String message, HttpStatus statusCode) {
        this.message = message;
        this.statusCode = statusCode;
        this.errorCode = statusCode.value();
    }

    public ApiException(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public ApiException() {
        this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
