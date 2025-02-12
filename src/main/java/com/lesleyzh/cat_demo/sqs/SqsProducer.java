package com.lesleyzh.cat_demo.sqs;

import com.lesleyzh.cat_demo.dto.QueueMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;


@Service
public class SqsProducer implements QueueProducer<QueueMessageDto> {
    private final Logger logger = LoggerFactory.getLogger(SqsProducer.class);
    private final SqsClient sqsClient;
    private final String queueName;
    private final String queueUrl;

    public SqsProducer(SqsClient sqsClient,
                       @Qualifier("demo-job-queue") String queueName) {
        this.sqsClient = sqsClient;
        this.queueName = queueName;
        this.queueUrl = getQueueUrl();
    }

    private String getQueueUrl() {
        GetQueueUrlRequest getQueueUrlRequest = GetQueueUrlRequest.builder()
                .queueName(queueName)
                .build();
        return sqsClient.getQueueUrl(getQueueUrlRequest).queueUrl();
    }

    @Override
    public void send(QueueMessageDto message) {
        logger.info("Sending message to queue: {}", message.getMessage());
        sqsClient.sendMessage(builder -> builder.queueUrl(queueUrl).messageBody(message.getMessage().toString()));
    }
}
