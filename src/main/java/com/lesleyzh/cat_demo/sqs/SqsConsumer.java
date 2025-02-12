package com.lesleyzh.cat_demo.sqs;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//都是文件写好的代码，没有需要原创的，唯一需要自己写的就是messageProcessor
//messageProcessor就是你具体要干的任务
public class SqsConsumer implements QueueConsumer{
    private final Logger logger = LoggerFactory.getLogger(SqsConsumer.class);
    private final SqsClient sqsClient;
    private final String queueUrl;
    private final ExecutorService executorService;
    private volatile boolean running = true;
    private final MessageProcessor<Message> messageProcessor;


    public SqsConsumer(SqsClient sqsClient, String queueUrl, MessageProcessor<Message> messageProcessor) {
        this.sqsClient = sqsClient;
        this.queueUrl = queueUrl;
        this.messageProcessor = messageProcessor;
        this.executorService = Executors.newFixedThreadPool(8);
    }

    @PostConstruct
    public void start() {
        executorService.submit(this::pollMessages);
    }

    @Override
    public void pollMessages() {
        while (running && !Thread.currentThread().isInterrupted()) {
            try {
                ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                        .queueUrl(queueUrl)
                        .maxNumberOfMessages(10)
                        .waitTimeSeconds(20)
                        .build();

                List<Message> messages =
                        sqsClient.receiveMessage(receiveMessageRequest).messages();

                for (Message message : messages) {
                    try {
                        logger.info("Received message: {}", message.body());

                        // Process the message
                        messageProcessor.processMessage(message);

                        // Delete the message after processing
                        deleteMessage(message);
                    } catch (Exception e) {
                        logger.error("Failed to process message: {}", message.body(), e);
                    }
                }
            } catch (Exception e) {
                logger.error("Error while polling messages", e);
            }
        }
    }

    public void deleteMessage(Message message) {
        DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                .queueUrl(queueUrl)
                .receiptHandle(message.receiptHandle())
                .build();
        sqsClient.deleteMessage(deleteMessageRequest);
    }

    public void destroy() {
        stop();
    }

    public void stop() {
        running = false;
        executorService.shutdownNow();
        try {
            if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
                logger.warn("ExecutorService did not terminate in the specified time.");
                List<Runnable> droppedTasks = executorService.shutdownNow();
                logger.warn(
                        "ExecutorService was abruptly shut down. {} tasks will not be executed.", droppedTasks.size());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("ExecutorService termination interrupted", e);
        }
    }


}
