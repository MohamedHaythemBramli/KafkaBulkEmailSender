package com.github.bulkemailsender.mapper;

import com.github.bulkemailsender.dto.BulkEmailMessageDto;
import com.github.bulkemailsender.entites.BulkEmailMessageEntity;
import org.mapstruct.Mapper;

@Mapper
public interface BulkEmailMessageMapper {

    BulkEmailMessageDto from(BulkEmailMessageEntity entity);

    BulkEmailMessageEntity to(BulkEmailMessageDto entity);

}