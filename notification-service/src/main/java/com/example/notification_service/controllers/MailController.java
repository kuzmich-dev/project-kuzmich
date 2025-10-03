package com.example.notification_service.controllers;

import com.example.core.dto.UserEventDTO;
import com.example.notification_service.service.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
@Slf4j
public class MailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@Valid @RequestBody UserEventDTO userEventDTO) {
        try {
            emailService.sendNotification(userEventDTO);
            log.info("Письмо успешно отправлено на {}", userEventDTO.getEmail());
            return ResponseEntity.ok("Письмо отправлено");
        } catch (Exception e) {
            log.error("Ошибка при отправке письма на {}: {}", userEventDTO.getEmail(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при отправке письма");
        }
    }

}