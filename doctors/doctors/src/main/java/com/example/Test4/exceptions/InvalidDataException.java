package com.example.Test4.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidDataException extends RuntimeException {

    private final String typeOfData;

    public InvalidDataException(String typeOfData) {
        this.typeOfData = typeOfData;
    }

    public String getMessage() {
        return typeOfData + " must be unique and consist of only numbers!";
    }
}
