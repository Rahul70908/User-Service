package com.user.exception.handler;

import com.user.exception.dto.ApiErrorResponse;
import com.user.exception.dto.Errors;
import com.user.exception.dto.Source;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var errors = ex.getBindingResult().getAllErrors();
        var errorsList = new ArrayList<Errors>(errors.size());
        errors.forEach((ObjectError error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            Source source = new Source(fieldName, message);
            Errors errorInfo = new Errors(source);
            errorsList.add(errorInfo);
        });
        var apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.setStatus(HttpStatus.BAD_REQUEST.toString());
        apiErrorResponse.setMessage("INVALID_INPUT");
        apiErrorResponse.setErrors(errorsList);
        apiErrorResponse.setReason("Request Contains Invalid Input");
        apiErrorResponse.setTimestamp(Instant.now());
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
