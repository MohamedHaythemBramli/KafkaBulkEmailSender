package com.github.bulkemailsender.mapper;

import com.github.bulkemailsender.dto.EmailMessageDto;
import com.github.bulkemailsender.entites.EmailMessageEntity;
import org.mapstruct.Mapper;


@Mapper
public interface EmailMessageMapper {

    EmailMessageDto from(EmailMessageEntity entity);

    EmailMessageEntity to(EmailMessageDto entity);

}
