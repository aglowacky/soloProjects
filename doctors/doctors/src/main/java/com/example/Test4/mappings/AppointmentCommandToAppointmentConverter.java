package com.example.Test4.mappings;

import com.example.Test4.command.AppointmentCommand;
import com.example.Test4.domain.Appointment;
import com.example.Test4.domain.Doctor;
import com.example.Test4.domain.Patient;
import com.example.Test4.exceptions.ObjectNotFoundException;
import com.example.Test4.repository.DoctorRepository;
import com.example.Test4.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentCommandToAppointmentConverter implements Converter<AppointmentCommand, Appointment> {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;


    @Override
    public Appointment convert(MappingContext<AppointmentCommand, Appointment> mappingContext) {
        AppointmentCommand appointmentCommand = mappingContext.getSource();
        Doctor doctor = doctorRepository.findById(appointmentCommand.getDoctorId()).orElseThrow(() -> new ObjectNotFoundException("Doctor", appointmentCommand.getDoctorId()));
        Patient patient = patientRepository.findById(appointmentCommand.getPatientId()).orElseThrow(() -> new ObjectNotFoundException("Patient", appointmentCommand.getPatientId()));
        return new Appointment(doctor, patient, appointmentCommand.getDateTime(), appointmentCommand.getTimeInMinutes());
    }
}
