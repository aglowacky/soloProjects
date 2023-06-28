package com.example.Test4.service;

import com.example.Test4.domain.Patient;
import com.example.Test4.exceptions.InvalidDataException;
import com.example.Test4.exceptions.ObjectNotFoundException;
import com.example.Test4.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;


    private void validatePesel(String pesel) {
        if (patientRepository.existsByPesel(pesel)) {
            throw new InvalidDataException("pesel");
        }
        for (char c : pesel.toCharArray()) {
            if (!(Character.isDigit(c))) {
                throw new InvalidDataException("pesel");
            }
        }
        //        if(pesel.length()!=11){
        //            throw new InvalidDataException("pesel"); zakomentowane bo mi sie nie chce pisac 11 cyfrowych peseli
        //        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = InvalidDataException.class)
    public Patient savePatient(Patient patient) {
        validatePesel(patient.getPesel());
        return patientRepository.save(patient);
    }

    public List<Patient> findAllPatients() {
        return patientRepository.findAll();
    }

    public Patient findPatientById(Long patientId) {
        return patientRepository.findById(patientId).orElseThrow(() -> new ObjectNotFoundException("Patient", patientId));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = ObjectNotFoundException.class)
    public Patient deletePatient(Long patientId) {
        Patient p = patientRepository.findById(patientId).orElseThrow(() -> new ObjectNotFoundException("Patient", patientId));
        patientRepository.deleteById(patientId);
        return p;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = InvalidDataException.class)
    public Patient updatePatient(Long patientId, Patient patient) {
        Patient patientToUpdate = patientRepository.findById(patientId).orElseThrow(() -> new ObjectNotFoundException("Patient", patientId));
        validatePesel(patient.getPesel());
        patientToUpdate.setFirstName(patient.getFirstName());
        patientToUpdate.setLastName(patient.getLastName());
        patientToUpdate.setPesel(patient.getPesel());
        patientToUpdate.getAppointments().clear();
        return patientRepository.save(patientToUpdate);
    }
}
