package com.github.bulkemailsender.entites;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class EmailMessageEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "\"from\"")
    private String from;
    @Column(name = "\"to\"")
    private String to;
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String body;

    @Enumerated(EnumType.STRING)
    private EmailMessageStatus status;

    @Enumerated(EnumType.STRING)
    private EmailMessagePriority priority;
}
