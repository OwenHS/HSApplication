package com.hs.libs.sample.index.design.module.observer;

/**
 * 观察者类
 * Created by huangshuo on 17/10/23.
 */

public abstract class Observer {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void update(String action);
}
