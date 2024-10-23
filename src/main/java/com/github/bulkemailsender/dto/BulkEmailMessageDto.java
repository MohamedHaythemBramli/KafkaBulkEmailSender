package com.github.bulkemailsender.dto;

import com.github.bulkemailsender.entites.EmailMessagePriority;
import lombok.Data;

import java.util.List;

@Data
public class BulkEmailMessageDto {
    private String from;
    private List<String> to;
    private String subject;
    private String body;
    private EmailMessagePriority priority;
}
