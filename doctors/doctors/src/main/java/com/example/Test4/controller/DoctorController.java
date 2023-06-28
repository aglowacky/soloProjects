package com.example.Test4.controller;

import com.example.Test4.command.DoctorCommand;
import com.example.Test4.domain.Doctor;
import com.example.Test4.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("doctor")
public class DoctorController {

    private final ModelMapper modelMapper;
    private final DoctorService doctorService;


    @PostMapping
    public ResponseEntity<Doctor> saveDoctor(@RequestBody DoctorCommand doctorCommand) {
        return new ResponseEntity<>(doctorService.saveDoctor((modelMapper.map(doctorCommand, Doctor.class))), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Doctor>> findAll() {
        return new ResponseEntity<>(doctorService.findAllDoctors(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> findById(@PathVariable Long id) {
        return new ResponseEntity<>(doctorService.findDoctorById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Long id, @RequestBody DoctorCommand doctorCommand) {
        return new ResponseEntity<>(doctorService.updateDoctor(id, modelMapper.map(doctorCommand, Doctor.class)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Doctor> deleteDoctor(@PathVariable Long id) {
        return new ResponseEntity<>((doctorService.deleteDoctor(id)), HttpStatus.OK);
    }
}
