package com.example.Test4.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class PatientCommand {

    private String firstName;
    private String lastName;
    private String pesel;
}
