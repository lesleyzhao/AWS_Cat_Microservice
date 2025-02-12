package com.lesleyzh.cat_demo.kafka.observers;

public interface Subject<T> {
    void registerObserver(Observer<T> observer);
    void removeObserver(Observer<T> observer);
    void notifyObservers(T obj);
}
