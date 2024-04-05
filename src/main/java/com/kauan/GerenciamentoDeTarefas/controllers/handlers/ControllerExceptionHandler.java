package com.kauan.GerenciamentoDeTarefas.controllers.handlers;

import com.kauan.GerenciamentoDeTarefas.dtos.exceptions.CustomError;
import com.kauan.GerenciamentoDeTarefas.services.exceptions.DatabaseException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<CustomError> databaseException(DatabaseException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        CustomError customError = new CustomError(Instant.now(), status.value(), e.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(status).body(customError);
    }
}