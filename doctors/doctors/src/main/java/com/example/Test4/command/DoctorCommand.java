package com.example.Test4.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class DoctorCommand {

    private String firstName;
    private String lastName;
    private String nip;
    private String specialization;
}
