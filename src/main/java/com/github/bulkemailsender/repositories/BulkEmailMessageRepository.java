package com.github.bulkemailsender.repositories;

import com.github.bulkemailsender.entites.BulkEmailMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BulkEmailMessageRepository extends JpaRepository<BulkEmailMessageEntity, Long> {
}
