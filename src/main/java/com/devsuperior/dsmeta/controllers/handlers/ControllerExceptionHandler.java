package com.devsuperior.dsmeta.controllers.handlers;

import com.devsuperior.dsmeta.errors.CustomError;
import com.devsuperior.dsmeta.exceptions.DateTimePeriodException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(DateTimePeriodException.class)
    public ResponseEntity<CustomError> handleDateTime(DateTimePeriodException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

}