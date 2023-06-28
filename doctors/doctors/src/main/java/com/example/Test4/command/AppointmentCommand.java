package com.example.Test4.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class AppointmentCommand {

    private Long doctorId;
    private Long patientId;
    private LocalDateTime dateTime;
    private int timeInMinutes;
}
