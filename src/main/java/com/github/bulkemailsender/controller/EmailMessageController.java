package com.github.bulkemailsender.controller;

import com.github.bulkemailsender.dto.EmailMessageDto;
import com.github.bulkemailsender.service.EmailMessageService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RequiredArgsConstructor
@Service
@RestController
@RequestMapping("/api/v1/email-messages")
public class EmailMessageController {

    private final EmailMessageService emailMessageService;

    @PostMapping
    public void create(@RequestBody EmailMessageDto emailMessageDto) throws MessagingException {
        emailMessageService.create(emailMessageDto);
    }

}
