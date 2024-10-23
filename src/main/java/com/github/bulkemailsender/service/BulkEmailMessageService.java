package com.github.bulkemailsender.service;

import com.github.bulkemailsender.dto.BulkEmailMessageDto;
import com.github.bulkemailsender.dto.EmailMessageDto;
import com.github.bulkemailsender.entites.BulkEmailMessageEntity;
import com.github.bulkemailsender.mapper.BulkEmailMessageMapper;
import com.github.bulkemailsender.repositories.BulkEmailMessageRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class BulkEmailMessageService {
    private final BulkEmailMessageRepository repository;
    private final BulkEmailMessageMapper mapper;
    private final EmailMessageService emailMessageService;

    public void create(BulkEmailMessageDto bulkEmailMessageDto) throws MessagingException {
        BulkEmailMessageEntity entity = mapper.to(bulkEmailMessageDto);

        repository.save(entity);

        for (String to : bulkEmailMessageDto.getTo()) {
            EmailMessageDto emailMessageDto = new EmailMessageDto();
            emailMessageDto.setFrom(bulkEmailMessageDto.getFrom());
            emailMessageDto.setTo(to);
            emailMessageDto.setSubject(bulkEmailMessageDto.getSubject());
            emailMessageDto.setBody(bulkEmailMessageDto.getBody());
            emailMessageDto.setPriority(bulkEmailMessageDto.getPriority());

            emailMessageService.create(emailMessageDto);
        }
    }
}
