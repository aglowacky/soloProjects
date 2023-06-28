package com.example.Test4.mappings;

import com.example.Test4.command.PatientCommand;
import com.example.Test4.domain.Patient;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Service;

@Service
public class PatientCommandToPatientConverter implements Converter<PatientCommand, Patient> {

    @Override
    public Patient convert(MappingContext<PatientCommand, Patient> mappingContext) {
        PatientCommand patientCommand = mappingContext.getSource();
        return new Patient(patientCommand.getFirstName(), patientCommand.getLastName(), patientCommand.getPesel());
    }
}
