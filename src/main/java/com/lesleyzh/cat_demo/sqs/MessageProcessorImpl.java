package com.lesleyzh.cat_demo.sqs;

import com.lesleyzh.cat_demo.dto.QueueMessageDto;
import com.lesleyzh.cat_demo.s3.S3Service;
import software.amazon.awssdk.services.sqs.model.Message;

public class MessageProcessorImpl implements MessageProcessor<Message> {
    private final S3Service s3Service;

    public MessageProcessorImpl(S3Service s3Service) {

        this.s3Service = s3Service;
    }

    //consume message唯一需要改动的代码就是这，通过改代码来改成不同的服务
    @Override
    public void processMessage(Message message) {
        // TODO: Implement message processing logic
    }
}
