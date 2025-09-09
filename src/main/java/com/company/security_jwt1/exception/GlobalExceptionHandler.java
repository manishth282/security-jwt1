package com.company.security_jwt1.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> methodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String,Object> response = new HashMap<>();
        response.put("message","Field validation error.");
        Map<String, String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> Objects.requireNonNull(fieldError.getDefaultMessage()),
                        (existing, replacement) -> existing
                ));
        response.put("errors",errors);
        return ResponseEntity.badRequest().body(response);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String,String>> dataIntegrityViolationException(DataIntegrityViolationException ex){
        log.warn("DataIntegrityViolationException::: {}",ex.getMessage());
        if(ex.getMessage().contains("students_email_key"))
            return new ResponseEntity<>(Map.of("error", "Given Email ID is already exists."), HttpStatus.BAD_REQUEST);
        else if(ex.getMessage().contains("students_roll_no_key"))
            return new ResponseEntity<>(Map.of("error", "Given Roll Number is already exists."), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(StudentNotExist.class)
    public ResponseEntity<Map<String,String>> studentNotExist(StudentNotExist ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message",ex.getMessage()));
    }
}
