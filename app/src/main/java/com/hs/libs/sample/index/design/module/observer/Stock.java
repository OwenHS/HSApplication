package com.hs.libs.sample.index.design.module.observer;

/**
 * 股票类
 * Created by huangshuo on 17/10/23.
 */

public class Stock extends Subject {


    @Override
    public void notifyObservers(String action) {
        for(Observer observer : observers){
            observer.update(action);
        }
    }
}
