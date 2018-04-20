package com.hs.libs.sample.index.design.module.observer;

import android.util.Log;

import java.util.ArrayList;

/**
 * 被观察者类
 * Created by huangshuo on 17/10/23.
 */

public abstract class Subject {

    ArrayList<Observer> observers = new ArrayList<>();

    public void attach(Observer obj){
        Log.d("owen",obj.getName()+"买入这只股票");
        observers.add(obj);
    }

    public void detach(Observer obj){
        Log.d("owen",obj.getName()+"卖出这只股票");
        observers.remove(obj);
    }

    //通知观察者
    public abstract void notifyObservers(String action);
}
