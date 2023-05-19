package com.neoris.turnosrotativos.exceptions.handlers;

import com.neoris.turnosrotativos.exceptions.ConflictRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


//Implementanción similar a BadRequestExceptionHandler solo que aquí seteamos el statuscode en 409.
@RestControllerAdvice
public class ConflictRequestExceptionHandler extends ResponseEntityExceptionHandler{
    @ExceptionHandler(ConflictRequestException.class)
    protected ResponseEntity<Object> handleConflictRequestException(ConflictRequestException ex) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("timestamp", new Date());
        responseBody.put("status", HttpStatus.CONFLICT.value());
        responseBody.put("message", ex.getMessage());
        return new ResponseEntity<>(responseBody, HttpStatus.CONFLICT);
    }
}
