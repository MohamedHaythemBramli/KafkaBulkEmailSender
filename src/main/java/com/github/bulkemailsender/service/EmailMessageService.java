package com.github.bulkemailsender.service;

import com.github.bulkemailsender.dto.EmailMessageDto;
import com.github.bulkemailsender.entites.EmailMessageEntity;
import com.github.bulkemailsender.entites.EmailMessageStatus;
import com.github.bulkemailsender.mapper.EmailMessageMapper;
import com.github.bulkemailsender.repositories.EmailMessageRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class EmailMessageService {
    private final EmailMessageRepository repository;
    private final EmailMessageMapper mapper;
    private final KafkaTemplate<Long, Long> kafkaTemplate;
    private final EmailMessageSenderService emailMessageSenderService;

    public void create(EmailMessageDto emailMessageDto) throws MessagingException {
        EmailMessageEntity entity = mapper.to(emailMessageDto);
        entity.setStatus(EmailMessageStatus.PENDING);

        // Save the entity with PENDING status
        repository.save(entity);

        try {
            // Attempt to send the email
            emailMessageSenderService.sendEmail(emailMessageDto);

            // If email sent successfully, update status to SENT
            entity.setStatus(EmailMessageStatus.SENT);
        } catch (MessagingException e) {
            // If email sending fails, set status to FAILED and rethrow the exception
            entity.setStatus(EmailMessageStatus.FAILED);
            throw e;
        } finally {
            // Update the entity status in the database
            repository.save(entity);
        }

        // Optionally send Kafka message
        kafkaTemplate.send("emailMessageTopic", entity.getId());
    }

}
