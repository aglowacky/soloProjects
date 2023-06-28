package com.example.Test4.controller;

import com.example.Test4.Dto.ExceptionDto;
import com.example.Test4.exceptions.InvalidAppointmentTimeException;
import com.example.Test4.exceptions.InvalidDataException;
import com.example.Test4.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> handleMissingObjectException(ObjectNotFoundException e) {
        ExceptionDto exceptionDTO = new ExceptionDto(e.getMessage());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleInvalidDataException(InvalidDataException e) {
        ExceptionDto exceptionDTO = new ExceptionDto(e.getMessage());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleInvalidAppointmentTimeException(InvalidAppointmentTimeException e) {
        ExceptionDto exceptionDTO = new ExceptionDto(e.getMessage());
        return new ResponseEntity<>(exceptionDTO, HttpStatus.CONFLICT);
    }
}
