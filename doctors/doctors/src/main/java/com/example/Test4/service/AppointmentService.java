package com.example.Test4.service;

import com.example.Test4.domain.Appointment;
import com.example.Test4.domain.Doctor;
import com.example.Test4.domain.Patient;
import com.example.Test4.exceptions.InvalidAppointmentTimeException;
import com.example.Test4.exceptions.ObjectNotFoundException;
import com.example.Test4.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;


    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {InvalidAppointmentTimeException.class, ObjectNotFoundException.class})
    public Appointment saveAppointment(Appointment appointment) {
        validateAppointment(appointment);
        return appointmentRepository.save(appointment);
    }

    public Appointment findAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId).orElseThrow(() -> new ObjectNotFoundException("Appointment", appointmentId));
    }

    public List<Appointment> findAllAppointments() {
        return appointmentRepository.findAll();
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {InvalidAppointmentTimeException.class, ObjectNotFoundException.class})
    public Appointment deleteAppointment(Long appointmentId) {
        Appointment appointment = findAppointmentById(appointmentId);
        appointmentRepository.deleteById(appointmentId);
        return appointment;
    }


    public LocalDateTime getAppointmentEndTime(Appointment appointment) {
        return appointment.getDateTime().plusMinutes(appointment.getTimeInMinutes());
    }


    public boolean collisionCheck(Appointment a, Appointment b) {
        boolean notColliding = b.getDateTime().isBefore(a.getDateTime()) && getAppointmentEndTime(b).isBefore(a.getDateTime());
        if (b.getDateTime().isAfter(getAppointmentEndTime(a))) {
            notColliding = true;
        }
        return !notColliding;
    }

    private void validateAppointment(Appointment appointment) {
        Doctor doctor = appointment.getDoctor();
        Patient patient = appointment.getPatient();
        for (Appointment a : doctor.getAppointments()) {
            if (collisionCheck(a, appointment)) {
                throw new InvalidAppointmentTimeException("Doctor");
            }
        }
        for (Appointment a : patient.getAppointments()) {
            if (collisionCheck(a, appointment)) {
                throw new InvalidAppointmentTimeException("Patient");
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {InvalidAppointmentTimeException.class, ObjectNotFoundException.class})
    public Appointment updateAppointment(Long appointmentId, Appointment appointment) {
        Appointment appointmentToUpdate = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ObjectNotFoundException("Appointment", appointmentId));
        appointmentToUpdate.setDateTime(appointment.getDateTime());
        appointmentToUpdate.setDoctor(appointment.getDoctor());
        appointmentToUpdate.setPatient(appointment.getPatient());
        appointmentToUpdate.setTimeInMinutes(appointment.getTimeInMinutes());
        validateAppointment(appointmentToUpdate);
        return appointmentRepository.save(appointmentToUpdate);

    }
}
