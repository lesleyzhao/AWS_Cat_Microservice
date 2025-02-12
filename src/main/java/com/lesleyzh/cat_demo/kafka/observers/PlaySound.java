package com.lesleyzh.cat_demo.kafka.observers;

import com.lesleyzh.cat_demo.dto.CatLocation;

public class PlaySound implements Observer<CatLocation> {
    @Override
    public void update(CatLocation obj) {
        if(getDistance(obj.locationX(), obj.locationY()) < 10){
            System.out.println("Play sound: " + obj);
        }
    }

    public double getDistance(double x, double y){
        return Math.sqrt(x*x + y*y);
    }
}
