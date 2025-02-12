package com.lesleyzh.cat_demo.kafka.observers;

public interface Observer<T> {
    void update(T obj);
}
