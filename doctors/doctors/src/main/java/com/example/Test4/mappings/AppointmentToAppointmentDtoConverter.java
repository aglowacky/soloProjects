package com.example.Test4.mappings;

import com.example.Test4.Dto.AppointmentDto;
import com.example.Test4.Dto.DoctorDto;
import com.example.Test4.Dto.PatientDto;
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
public class AppointmentToAppointmentDtoConverter implements Converter<Appointment, AppointmentDto> {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Override
    public AppointmentDto convert(MappingContext<Appointment, AppointmentDto> mappingContext) {
        Appointment appointment = mappingContext.getSource();
        Doctor doctor = doctorRepository.findById(appointment.getDoctor().getId()).orElseThrow(() -> new ObjectNotFoundException("Doctor", appointment.getDoctor().getId()));
        DoctorDto doctorDto = new DoctorDto(doctor.getId(), doctor.getFirstName(), doctor.getLastName(), doctor.getNip(), doctor.getSpecialization());
        Patient patient = patientRepository.findById(appointment.getPatient().getId()).orElseThrow(() -> new ObjectNotFoundException("Patient", appointment.getPatient().getId()));
        PatientDto patientDto = new PatientDto(patient.getId(), patient.getFirstName(), patient.getLastName(), patient.getPesel());
        return new AppointmentDto(appointment.getId(), doctorDto, patientDto, appointment.getDateTime(), appointment.getTimeInMinutes());
    }
}
