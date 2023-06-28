package com.example.Test4;

import com.example.Test4.command.PatientCommand;
import com.example.Test4.domain.Patient;
import com.example.Test4.exceptions.InvalidDataException;
import com.example.Test4.exceptions.ObjectNotFoundException;
import com.example.Test4.repository.PatientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PatientRepository patientRepository;


    @Test
    public void shouldAddPatientTest() throws Exception {
        PatientCommand patientCommand = new PatientCommand("andrzej", "peciak", "22");
        this.mockMvc.perform(post("/patient")
                        .content(objectMapper.writeValueAsString(patientCommand))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("andrzej"))
                .andExpect(jsonPath("$.lastName").value("peciak"))
                .andExpect(jsonPath("$.pesel").value("22"));
    }

    @Test
    public void shouldUpdatePatient() throws Exception {
        Patient patient = new Patient("andrzej", "peciak", "69");
        Patient savedPatient = patientRepository.saveAndFlush(patient);
        PatientCommand patientCommand = new PatientCommand("maria", "kowalska", "57");
        this.mockMvc.perform(put("/patient/{patientId}", savedPatient.getId())
                        .content(objectMapper.writeValueAsString(patientCommand))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("maria"));
        Patient updatedPatient = patientRepository.findById(savedPatient.getId()).orElseThrow(() -> new ObjectNotFoundException("Patient", savedPatient.getId()));
        assertEquals(updatedPatient.getLastName(), "kowalska");
    }

    @Test
    public void exceptionWhenPatientNotFound() throws Exception {
        this.mockMvc.perform(get("/patient/{id}", 27)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ObjectNotFoundException));
    }

    @Test
    public void exceptionWhenInvalidPesel() throws Exception {
        PatientCommand patientCommand = new PatientCommand("andrzej", "peciak", "dupa");

        this.mockMvc.perform(post("/patient")
                        .content(objectMapper.writeValueAsString(patientCommand))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidDataException));
    }

    @Test
    public void exceptionWhenDuplicatePesel() throws Exception {
        Patient patient = new Patient("andrzej", "peciak", "1234");
        patientRepository.saveAndFlush(patient);
        PatientCommand patientCommand = new PatientCommand("jan", "koza", "1234");
        this.mockMvc.perform(post("/patient")
                        .content(objectMapper.writeValueAsString(patientCommand))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidDataException));
    }


}
