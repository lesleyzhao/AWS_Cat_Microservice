package com.lesleyzh.cat_demo.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableQueueMessageDto.class)
@JsonDeserialize(as = ImmutableQueueMessageDto.class)
public interface QueueMessageDto {
    String getMessage();
    long timestamp();
}
