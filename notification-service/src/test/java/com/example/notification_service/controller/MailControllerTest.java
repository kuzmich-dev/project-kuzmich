package com.example.notification_service.controller;

import com.example.core.dto.UserEventDTO;
import com.example.notification_service.controllers.MailController;
import com.example.notification_service.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MailController.class)
@Import(com.example.notification_service.config.MailConfig.class) // если MailConfig нужен
class MailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmailService emailService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldSendEmailSuccessfully() throws Exception {
        UserEventDTO dto = new UserEventDTO("test@mail.com", "CREATE");

        mockMvc.perform(post("/api/mail/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Письмо отправлено"));

        Mockito.verify(emailService, times(1)).sendNotification(any(UserEventDTO.class));
    }

    @Test
    void shouldHandleEmailServiceException() throws Exception {
        UserEventDTO dto = new UserEventDTO("fail@mail.com", "CREATE");

        Mockito.doThrow(new RuntimeException("Mail server down"))
                .when(emailService).sendNotification(any(UserEventDTO.class));

        mockMvc.perform(post("/api/mail/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Ошибка при отправке письма"));

        Mockito.verify(emailService, times(1)).sendNotification(any(UserEventDTO.class));
    }

}
