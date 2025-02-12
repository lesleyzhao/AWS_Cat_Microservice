package com.lesleyzh.cat_demo.sqs;

public interface MessageProcessor<T> {
    void processMessage(T message);
}
