package com.blumbit.supermercado.exception;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blumbit.supermercado.common.dto.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse<String>> handleException(Exception ex, 
    HttpServletRequest request){
        return new ResponseEntity<>(new ErrorResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, 
        ex.getMessage(), new Date().toString(), 
        request.getRequestURI()), 
        new HttpHeaders(), 
        HttpStatus.INTERNAL_SERVER_ERROR);
    };

    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse<String>> handleRuntimeException(RuntimeException ex, 
    HttpServletRequest request){
        return new ResponseEntity<>(new ErrorResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, 
        ex.getMessage(), new Date().toString(), 
        request.getRequestURI()), 
        new HttpHeaders(), 
        HttpStatus.INTERNAL_SERVER_ERROR);
    };

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse<List<String>>> handleMethodNotValidException(MethodArgumentNotValidException ex, 
    HttpServletRequest request){
        log.error("LOG TEST ERROR", ex);
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
        .map(FieldError::getDefaultMessage)
        .collect(Collectors.toList());
        return new ResponseEntity<>(new ErrorResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, 
        errors, new Date().toString(), 
        request.getRequestURI()), 
        new HttpHeaders(), 
        HttpStatus.INTERNAL_SERVER_ERROR);
    };

}
