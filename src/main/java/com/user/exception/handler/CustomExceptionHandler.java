package com.user.exception.handler;

import com.user.exception.dto.ApiErrorResponse;
import com.user.exception.dto.Errors;
import com.user.exception.dto.Source;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers, @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
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
}
