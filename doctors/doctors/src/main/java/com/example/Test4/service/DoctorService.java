package com.example.Test4.service;

import com.example.Test4.domain.Doctor;
import com.example.Test4.exceptions.InvalidDataException;
import com.example.Test4.exceptions.ObjectNotFoundException;
import com.example.Test4.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = InvalidDataException.class)
    public Doctor saveDoctor(Doctor doctor) {
        validateNip(doctor.getNip());
        return doctorRepository.save(doctor);
    }

    public List<Doctor> findAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor findDoctorById(Long doctorId) {
        return doctorRepository.findById(doctorId).orElseThrow(() -> new ObjectNotFoundException("Doctor", doctorId));
    }

    public Doctor deleteDoctor(Long doctorId) {
        Doctor d = findDoctorById(doctorId);
        doctorRepository.deleteById(doctorId);
        return d;
    }


    private void validateNip(String nip) {
        if (doctorRepository.existsByNip(nip)) {
            throw new InvalidDataException("nip");
        }
        for (char c : nip.toCharArray()) {
            if (!(Character.isDigit(c))) {
                throw new InvalidDataException("nip");
            }
        }
    }

    @Transactional(rollbackFor = {InvalidDataException.class, ObjectNotFoundException.class}, propagation = Propagation.REQUIRES_NEW)
    public Doctor updateDoctor(Long doctor_id, Doctor doctor) {
        Doctor doctorToUpdate = doctorRepository.findById(doctor_id).orElseThrow(() -> new ObjectNotFoundException("Doctor", doctor_id));
        validateNip(doctor.getNip());
        doctorToUpdate.setFirstName(doctor.getFirstName());
        doctorToUpdate.setLastName(doctor.getLastName());
        doctorToUpdate.setNip(doctor.getNip());
        doctorToUpdate.setSpecialization(doctor.getSpecialization());
        doctorToUpdate.getAppointments().clear();
        return doctorRepository.save(doctorToUpdate);
    }
}
