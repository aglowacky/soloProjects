package com.example.Test4.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class AppointmentDto {
    private Long id;
    private DoctorDto doctorDto;
    private PatientDto patientDto;
    private LocalDateTime dateTime;
    private int timeInMinutes;
}

