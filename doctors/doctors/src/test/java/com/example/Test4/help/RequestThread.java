package com.example.Test4.help;

import com.example.Test4.command.AppointmentCommand;
import com.example.Test4.repository.DoctorRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RequiredArgsConstructor
public class RequestThread implements Runnable{
    private final AppointmentCommand appointmentCommand;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Override
    public void run() {
        try {
            this.mockMvc.perform(post("/app")
                    .content(objectMapper.writeValueAsString(appointmentCommand))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
