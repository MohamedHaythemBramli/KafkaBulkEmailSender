package com.github.bulkemailsender.dto;

import com.github.bulkemailsender.entites.EmailMessagePriority;
import lombok.Data;

@Data
public class EmailMessageDto {
    private String from;
    private String to;
    private String subject;
    private String body;
    private EmailMessagePriority priority;
}
