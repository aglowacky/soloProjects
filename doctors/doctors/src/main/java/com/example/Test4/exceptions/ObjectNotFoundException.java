package com.example.Test4.exceptions;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@AllArgsConstructor
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends RuntimeException {

    private final String typeOfObject;
    private final Long idOfObject;


    @Override
    public String getMessage() {
        return "Could not find " + typeOfObject + " with Id : " + idOfObject;
    }
}
