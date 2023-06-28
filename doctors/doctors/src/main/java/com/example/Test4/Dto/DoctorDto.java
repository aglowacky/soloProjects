package com.example.Test4.Dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class DoctorDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String nip;
    private String specialization;

    public DoctorDto(Long id, String firstName, String lastName, String nip, String specialization) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nip = nip;
        this.specialization = specialization;
    }
}
