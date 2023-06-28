package com.example.Test4.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InvalidAppointmentTimeException extends RuntimeException {

    private final String person;

    public InvalidAppointmentTimeException(String person) {
        this.person = person;
    }

    public String getMessage() {
        return "Cannot make a new appointment due to unavailability of the " + person + ".";
    }
}
