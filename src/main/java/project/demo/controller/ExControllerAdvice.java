package project.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import project.demo.exception.BadRequestException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExControllerAdvice {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> FileUploadException(BadRequestException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}
