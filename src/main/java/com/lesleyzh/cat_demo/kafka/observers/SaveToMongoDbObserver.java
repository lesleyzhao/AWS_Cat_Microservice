package com.lesleyzh.cat_demo.kafka.observers;

import com.lesleyzh.cat_demo.dto.CatLocation;

public class SaveToMongoDbObserver implements Observer<CatLocation> {
    @Override
    public void update(CatLocation obj) {
        System.out.println("Save to MongoDB: " + obj);
        //connect to mongo db and save the cat location
    }

}
