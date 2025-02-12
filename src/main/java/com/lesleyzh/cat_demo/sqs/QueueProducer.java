package com.lesleyzh.cat_demo.sqs;

import org.immutables.value.Value;

//@Value.Immutable
public interface QueueProducer<T> {
    void send(T message);
//    long timestamp();
}
