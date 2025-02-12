package com.lesleyzh.cat_demo.kafka.observers;

import com.lesleyzh.cat_demo.dto.CatLocation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class CatLocationTrackingSubject implements Subject<CatLocation> {
    private final List<Observer<CatLocation>> observers = new ArrayList<>();

    @Override
    public void registerObserver(Observer<CatLocation> observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer<CatLocation> observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(CatLocation obj) {
        for (Observer<CatLocation> observer : observers) {
            observer.update(obj);
        }
    }
}
