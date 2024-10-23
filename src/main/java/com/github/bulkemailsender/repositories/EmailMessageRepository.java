package com.github.bulkemailsender.repositories;

import com.github.bulkemailsender.entites.EmailMessageEntity;
import com.github.bulkemailsender.entites.EmailMessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailMessageRepository extends JpaRepository<EmailMessageEntity, Long> {
    List<EmailMessageEntity> findByStatus(EmailMessageStatus emailMessageStatus);
}
