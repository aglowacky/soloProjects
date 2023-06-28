package com.example.Test4.mappings;

import com.example.Test4.command.DoctorCommand;
import com.example.Test4.domain.Doctor;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

@Service
public class DoctorCommandToDoctorConverter implements Converter<DoctorCommand, Doctor> {

    @Override
    public Doctor convert(MappingContext<DoctorCommand, Doctor> mappingContext) {
        DoctorCommand doctorCommand = mappingContext.getSource();
        return new Doctor(doctorCommand.getFirstName(), doctorCommand.getLastName(), doctorCommand.getNip(), doctorCommand.getSpecialization());
    }
}
