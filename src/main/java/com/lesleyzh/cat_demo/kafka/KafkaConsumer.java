package com.lesleyzh.cat_demo.kafka;

import com.lesleyzh.cat_demo.dto.CatLocation;
import com.lesleyzh.cat_demo.kafka.observers.CatLocationTrackingSubject;
import com.lesleyzh.cat_demo.kafka.observers.OtherDownStreamAction;
import com.lesleyzh.cat_demo.kafka.observers.PlaySound;
import com.lesleyzh.cat_demo.kafka.observers.SaveToMongoDbObserver;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
    private CatLocationTrackingSubject catLocationTrackingSubject;

    public KafkaConsumer(CatLocationTrackingSubject catLocationTrackingSubject){
        this.catLocationTrackingSubject = catLocationTrackingSubject;
        this.catLocationTrackingSubject.registerObserver(new OtherDownStreamAction());
        this.catLocationTrackingSubject.registerObserver(new PlaySound());
        this.catLocationTrackingSubject.registerObserver(new SaveToMongoDbObserver());
    }

    @KafkaListener(topics = "cat-location-topic", groupId = "group_id")
    public void consume(CatLocation catLocation){
        catLocationTrackingSubject.notifyObservers(catLocation);
    }

}
