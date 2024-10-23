package com.github.bulkemailsender.controller;

import com.github.bulkemailsender.dto.BulkEmailMessageDto;
import com.github.bulkemailsender.service.BulkEmailMessageService;
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
@RequestMapping("/api/v1/bulk-email-messages")
public class BulkEmailMessageController {

    private final BulkEmailMessageService emailMessageService;

    @PostMapping
    public void create(@RequestBody BulkEmailMessageDto emailMessageDto) throws MessagingException {
        emailMessageService.create(emailMessageDto);
    }

}
