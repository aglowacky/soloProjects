package com.example.Test4.Dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class PatientDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String pesel;

    public PatientDto(Long id, String firstName, String lastName, String pesel) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
    }
}
