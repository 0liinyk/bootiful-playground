package com.oliynyk.play.web.config;

import com.oliynyk.play.model.ApiException;
import com.oliynyk.play.web.api.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;


/**
 * invoked by ExceptionHandlerExceptionResolver that implements HandlerExceptionResolver
 * and will be used to intercept and process any exception raised in the MVC system and not handled by a Controller
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired(required = false)
    private Map<? extends Class<? extends Throwable>, HttpStatus> exceptionToStatusCode = Collections.emptyMap();

    @ExceptionHandler(Throwable.class)
    ResponseEntity<ApiErrorResponse> handleControllerException(HttpServletRequest req, Throwable ex) {
        log.info("Handling exception: {} for path: {}", ex.getClass(), req.getContextPath());
        HttpStatus httpStatus = exceptionToStatusCode.getOrDefault(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        ApiErrorResponse errorResponse = ApiErrorResponse
                .builder()
                .path(req.getRequestURI())
                .message(ex.getMessage())
                .code(httpStatus.value())
                .build();
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    @ExceptionHandler(ApiException.class)
    ResponseEntity<ApiErrorResponse> handleApiException(HttpServletRequest req, ApiException ex) {
        log.info("Handling API exception for path: {}", req.getContextPath());
        ApiErrorResponse errorResponse = ApiErrorResponse
                .builder()
                .path(req.getRequestURI())
                .message(ex.getMessage())
                .code(ex.getErrorCode())
                .details(ex.getDetails())
                .build();
        return new ResponseEntity<>(errorResponse, ex.getStatusCode());
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.info("Handling exception: {} for request: {}", ex.getClass(), request);
        ApiErrorResponse.ApiErrorResponseBuilder errorResponseBuilder = ApiErrorResponse
                .builder()
                .message(ex.getMessage())
                .code(status.value());
        if (request instanceof ServletWebRequest){
            HttpServletRequest httpRequest = ((ServletWebRequest) request).getRequest();
            errorResponseBuilder.path(httpRequest.getRequestURI());
        }
        return new ResponseEntity<>(errorResponseBuilder.build(), headers, status);
    }



}