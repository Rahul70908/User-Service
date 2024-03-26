package com.user.exception.handler;

import com.user.exception.ApplicationException;
import com.user.exception.dto.ApiErrorResponse;
import com.user.exception.dto.Errors;
import com.user.exception.dto.Source;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@Component
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleUserNotFoundException(UsernameNotFoundException exception) {
        var apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.setStatus(HttpStatus.BAD_REQUEST.toString());
        apiErrorResponse.setMessage("Invalid User");
        apiErrorResponse.setErrors(List.of(Errors.builder()
                .source(Source.builder()
                        .detail(exception.getMessage()).build())
                .build()));
        apiErrorResponse.setReason("Need to Register First!!!");
        apiErrorResponse.setTimestamp(Instant.now());
        return ResponseEntity.badRequest().body(apiErrorResponse);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApiErrorResponse> handleUserNotFoundException(ApplicationException exception) {
        var apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.setStatus(HttpStatus.BAD_REQUEST.toString());
        apiErrorResponse.setMessage("Invalid User");
        apiErrorResponse.setErrors(List.of(Errors.builder()
                .source(Source.builder()
                        .detail(exception.getMessage()).build())
                .build()));
        apiErrorResponse.setReason("Need to Register First!!!");
        apiErrorResponse.setTimestamp(Instant.now());
        return ResponseEntity.badRequest().body(apiErrorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleUserNotFoundException(Exception exception) {
        var apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        apiErrorResponse.setMessage("Something Went Wrong!!");
        apiErrorResponse.setErrors(List.of(Errors.builder()
                .source(Source.builder()
                        .detail(exception.getMessage()).build())
                .build()));
        apiErrorResponse.setReason("Please Try Again");
        apiErrorResponse.setTimestamp(Instant.now());
        return ResponseEntity.internalServerError().body(apiErrorResponse);
    }
}
