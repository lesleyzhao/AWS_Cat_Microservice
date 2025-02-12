package com.lesleyzh.cat_demo.kafka;

import com.lesleyzh.cat_demo.dto.CatLocation;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {
    private final KafkaTemplate<String, CatLocation> kafkaProducer;
    public static final String CAT_LOCATION_TOPIC = "cat-location-topic";

    public ProducerService(KafkaTemplate<String, CatLocation> kafkaProducer){
        this.kafkaProducer = kafkaProducer;
    }

    public void produceMessage(CatLocation catLocation){
        kafkaProducer.send(CAT_LOCATION_TOPIC, catLocation.catId(), catLocation);
    }

}
