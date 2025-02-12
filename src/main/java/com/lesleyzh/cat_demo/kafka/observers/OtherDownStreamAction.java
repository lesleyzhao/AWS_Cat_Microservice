package com.lesleyzh.cat_demo.kafka.observers;

import com.lesleyzh.cat_demo.dto.CatLocation;

public class OtherDownStreamAction implements Observer<CatLocation> {
    @Override
    public void update(CatLocation obj) {
        System.out.println("Other down stream action: " + obj);
        //connect to other down stream service and do something
    }
}
