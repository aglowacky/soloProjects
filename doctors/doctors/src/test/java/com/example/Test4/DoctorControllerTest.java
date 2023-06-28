package com.example.Test4;

import com.example.Test4.command.DoctorCommand;
import com.example.Test4.domain.Doctor;
import com.example.Test4.exceptions.InvalidDataException;
import com.example.Test4.exceptions.ObjectNotFoundException;
import com.example.Test4.repository.DoctorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private DoctorRepository doctorRepository;


    @Test
    public void shouldAddDoctorTest() throws Exception {
        DoctorCommand doctorCommand = new DoctorCommand("andrzej", "peciak", "22", "skoki");
        this.mockMvc.perform(post("/doctor")
                        .content(objectMapper.writeValueAsString(doctorCommand))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("andrzej"))
                .andExpect(jsonPath("$.lastName").value("peciak"))
                .andExpect(jsonPath("$.nip").value("22"))
                .andExpect(jsonPath("$.specialization").value("skoki"));
    }

    @Test
    public void shouldUpdateDoctor() throws Exception{
        Doctor doctor = new Doctor("andrzej", "peciak", "22", "skoki");
        Doctor savedDoctor = doctorRepository.saveAndFlush(doctor);
        DoctorCommand doctorCommand = new DoctorCommand("maria", "kowalska", "57", "neurologia");
        this.mockMvc.perform(put("/doctor/{doctorId}", savedDoctor.getId())
                .content(objectMapper.writeValueAsString(doctorCommand))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("maria"));
        Doctor updatedDoctor = doctorRepository.findById(savedDoctor.getId()).orElseThrow(()->new ObjectNotFoundException("Doctor", savedDoctor.getId()));
        assertEquals("maria", updatedDoctor.getFirstName());
    }

    @Test
    public void exceptionWhenDoctorNotFound() throws Exception{
        this.mockMvc.perform(get("/doctor/{doctorId}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ObjectNotFoundException));

    }
    @Test
    public void exceptionWhenInvalidNip() throws Exception {
        DoctorCommand doctorCommand = new DoctorCommand("andrzej", "peciak", "dupa", "trololo");

        this.mockMvc.perform(post("/doctor")
                        .content(objectMapper.writeValueAsString(doctorCommand))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidDataException));
    }
    @Test
    public void exceptionWhenDuplicateNip() throws Exception {
        Doctor doctor = new Doctor("andrzej", "peciak", "1234", "narciarstwo");
        doctorRepository.saveAndFlush(doctor);
        DoctorCommand doctorCommand = new DoctorCommand("jan", "koza", "1234", "gra w lola");
        this.mockMvc.perform(post("/doctor")
                        .content(objectMapper.writeValueAsString(doctorCommand))
                        .contentType(MediaType.APPLICATION_JSON)
                        )
                .andExpect(status().isUnprocessableEntity())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidDataException));
    }


}
