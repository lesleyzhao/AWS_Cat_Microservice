package com.lesleyzh.cat_demo.kafka;


public interface StreamProducerService<T> {
    void produceMessage(T message);

}
