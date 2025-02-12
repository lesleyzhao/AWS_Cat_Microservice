package com.lesleyzh.cat_demo.configuration;

import com.lesleyzh.cat_demo.s3.S3Service;
import com.lesleyzh.cat_demo.sqs.MessageProcessor;
import com.lesleyzh.cat_demo.sqs.MessageProcessorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sqs.model.Message;


@Configuration
public class SqsMessageProcessorConfiguration {
    @Bean
    public MessageProcessor<Message> messageProcessor(S3Service s3Service) {
        return new MessageProcessorImpl(s3Service);
    }

}
