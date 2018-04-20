package com.hs.libs.sample.index.design.module.mediator;

/**
 * Created by huangshuo on 17/10/30.
 */

public class TextPane extends Pane {

    public TextPane(Window window) {
        super(window);
    }

    @Override
    public void sendMessage() {
        window.operation(this);
    }

    @Override
    public void update() {

    }
}
