package com.hs.libs.sample.index.design.module.mediator;

/**
 * 窗格对象基类
 * Created by huangshuo on 17/10/30.
 */

public abstract class Pane {

    public Window window;

    public Pane(Window window) {
        this.window = window;
    }

    public abstract void sendMessage();

    public abstract void update();

}
