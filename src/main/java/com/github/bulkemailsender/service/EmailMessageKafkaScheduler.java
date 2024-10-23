package com.github.bulkemailsender.service;

import com.github.bulkemailsender.dto.EmailMessageDto;
import com.github.bulkemailsender.entites.EmailMessageEntity;
import com.github.bulkemailsender.entites.EmailMessageStatus;
import com.github.bulkemailsender.mapper.EmailMessageMapper;
import com.github.bulkemailsender.repositories.EmailMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailMessageKafkaScheduler {
    private final EmailMessageRepository repository;
    private final EmailMessageSenderService emailMessageSenderService;
    private final EmailMessageMapper mapper;
    private final ExecutorService executorService = Executors.newFixedThreadPool(20);
    private final Semaphore semaphoreLow = new Semaphore(30);
    private final Semaphore semaphoreMiddle = new Semaphore(100);
    private final Semaphore semaphoreHigh = new Semaphore(3000);

    // low      3X
    // middle   10X
    // high     30X

    @KafkaListener(topics = "emailMessageTopicLow", groupId = "bulk-email-consumers")
    public void emailMessageTopicLow(Long messageId) throws InterruptedException {
        semaphoreLow.acquire();
        sendEmailMessage(messageId, semaphoreLow);
    }

    @KafkaListener(topics = "emailMessageTopicMedium", groupId = "bulk-email-consumers")
    public void emailMessageTopicMedium(Long messageId) throws InterruptedException {
        semaphoreMiddle.acquire();
        sendEmailMessage(messageId, semaphoreMiddle);
    }

    @KafkaListener(topics = "emailMessageTopicHigh", groupId = "bulk-email-consumers")
    public void emailMessageTopicHigh(Long messageId) throws InterruptedException {
        semaphoreHigh.acquire();
        sendEmailMessage(messageId, semaphoreHigh);
    }

    private void sendEmailMessage(Long messageId, Semaphore semaphore) throws InterruptedException {
        EmailMessageEntity entity = repository.findById(messageId).orElseThrow();
        EmailMessageDto dto = mapper.from(entity);
        executorService.submit(() -> { // potential problem, if task is rejected, we need to either make sure, it is not rejected or we need to release semmaphore
            try {
                emailMessageSenderService.sendEmail(dto);
                entity.setStatus(EmailMessageStatus.SENT);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                entity.setStatus(EmailMessageStatus.FAILED);
            } finally {
                repository.save(entity);
                semaphore.release();
            }
        });
    }
}
