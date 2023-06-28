package com.example.Test4.controller;

import com.example.Test4.Dto.AppointmentDto;
import com.example.Test4.command.AppointmentCommand;
import com.example.Test4.domain.Appointment;
import com.example.Test4.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("app")
public class AppointmentController {

    private final ModelMapper modelMapper;
    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentDto> saveAppointment(@RequestBody AppointmentCommand appointmentCommand) {
        Appointment appointment = modelMapper.map(appointmentCommand, Appointment.class);
        appointmentService.saveAppointment(appointment);
        return new ResponseEntity<>(modelMapper.map(appointment, AppointmentDto.class), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> findAllAppointments() {
        return new ResponseEntity<>(appointmentService.findAllAppointments(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> findById(@PathVariable Long id) {
        return new ResponseEntity<>(appointmentService.findAppointmentById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@PathVariable Long id, @RequestBody AppointmentCommand appointmentCommand) {
        return new ResponseEntity<>(appointmentService.updateAppointment(id, (modelMapper.map(modelMapper.map(appointmentCommand, AppointmentDto.class), Appointment.class))), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Appointment> deleteAppointment(@PathVariable Long id) {
        return new ResponseEntity<>(appointmentService.deleteAppointment(id), HttpStatus.OK);
    }
}
