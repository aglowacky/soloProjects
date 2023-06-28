package com.example.Test4;

import com.example.Test4.command.AppointmentCommand;
import com.example.Test4.domain.Appointment;
import com.example.Test4.domain.Doctor;
import com.example.Test4.domain.Patient;
import com.example.Test4.exceptions.InvalidAppointmentTimeException;
import com.example.Test4.exceptions.ObjectNotFoundException;
import com.example.Test4.repository.AppointmentRepository;
import com.example.Test4.repository.DoctorRepository;
import com.example.Test4.repository.PatientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest
@ActiveProfiles("test")
public class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;


    @Test
    public void shouldAddAppointment() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        Doctor doctor = new Doctor("andrzej", "peciak", "10", "skoki");
        Doctor savedDoctor = doctorRepository.saveAndFlush(doctor);
        Patient patient = new Patient("maria", "kowalska", "12");
        Patient savedPatient = patientRepository.saveAndFlush(patient);
        AppointmentCommand appointmentCommand = new AppointmentCommand(savedDoctor.getId(), savedPatient.getId(), LocalDateTime.parse("2022-12-31-15-53-16", formatter), 20);
        this.mockMvc.perform(post("/app")
                        .content(objectMapper.writeValueAsString(appointmentCommand))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        Appointment savedAppointment = appointmentRepository.findById(1L).orElseThrow(() -> new ObjectNotFoundException("Appointment", 1L));
        assertEquals("maria", savedAppointment.getPatient().getFirstName());
        assertEquals("andrzej", savedAppointment.getDoctor().getFirstName());
    }

    @Test
    public void doctorNotFound() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        Patient patient = new Patient("maria", "kowalska", "420");
        Patient savedPatient = patientRepository.saveAndFlush(patient);
        AppointmentCommand appointmentCommand = new AppointmentCommand(1L, savedPatient.getId(), LocalDateTime.parse("2022-12-31-15-53-16", formatter), 20);
        this.mockMvc.perform(post("/app")
                        .content(objectMapper.writeValueAsString(appointmentCommand))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MappingException));
    }

    @Test
    public void patientNotFound() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        Doctor doctor = new Doctor("andrzej", "peciak", "22", "skoki");
        Doctor savedDoctor = doctorRepository.saveAndFlush(doctor);
        AppointmentCommand appointmentCommand = new AppointmentCommand(savedDoctor.getId(), 1L, LocalDateTime.parse("2022-12-30-15-53-16", formatter), 20);
        this.mockMvc.perform(post("/app")
                        .content(objectMapper.writeValueAsString(appointmentCommand))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MappingException));
    }
//    @Test
//    public void multiThreadingFail() throws Exception{
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
//        Doctor doctor = new Doctor("andrzej", "peciak", "10", "skoki");
//        Doctor savedDoctor = doctorRepository.saveAndFlush(doctor);
//        Patient patient = new Patient("maria", "kowalska", "12");
//        Patient savedPatient = patientRepository.saveAndFlush(patient);
//        AppointmentCommand appointmentCommand = new AppointmentCommand(savedDoctor.getId(), savedPatient.getId(), LocalDateTime.now(), 20);
//        RequestThread thread1 = new RequestThread(appointmentCommand, mockMvc, objectMapper);
//        RequestThread thread2 = new RequestThread(appointmentCommand, mockMvc, objectMapper);
//        thread1.run();
//        thread2.run();
//        List<Appointment> all = appointmentRepository.findAll();
//        System.out.println(all.size());
//    }

    @Test
    public void exceptionWhenInvalidAppointmentTime() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        Doctor doctor = new Doctor("andrzej", "peciak", "73", "skoki");
        Doctor savedDoctor = doctorRepository.saveAndFlush(doctor);
        Patient patient = new Patient("maria", "kowalska", "500");
        Patient savedPatient = patientRepository.saveAndFlush(patient);
        Appointment appointment = new Appointment(savedDoctor, savedPatient, LocalDateTime.parse("2023-12-31-15-53-16", formatter), 20);
        appointmentRepository.saveAndFlush(appointment);
        AppointmentCommand appointmentCommand = new AppointmentCommand(savedDoctor.getId(), savedPatient.getId(), LocalDateTime.parse("2023-12-31-16-00-00", formatter), 20);
        this.mockMvc.perform(post("/app")
                        .content(objectMapper.writeValueAsString(appointmentCommand))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidAppointmentTimeException));
    }


}
