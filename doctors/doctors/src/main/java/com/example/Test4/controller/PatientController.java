package com.example.Test4.controller;

import com.example.Test4.command.PatientCommand;
import com.example.Test4.domain.Patient;
import com.example.Test4.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("patient")
public class PatientController {

    private final ModelMapper modelMapper;
    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<Patient> savePatient(@RequestBody PatientCommand patientCommand) {
        return new ResponseEntity<>(patientService.savePatient(modelMapper.map(patientCommand, Patient.class)), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Patient>> findAll() {
        return new ResponseEntity<>(patientService.findAllPatients(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Patient> findById(@PathVariable Long id) {
        return new ResponseEntity<>(patientService.findPatientById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody PatientCommand patientCommand) {
        return new ResponseEntity<>(patientService.updatePatient(id, modelMapper.map(patientCommand, Patient.class)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Patient> deletePatient(@PathVariable Long id) {
        return new ResponseEntity<>(patientService.deletePatient(id), HttpStatus.OK);
    }
}
