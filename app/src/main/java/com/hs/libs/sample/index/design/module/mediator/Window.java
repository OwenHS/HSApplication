package com.hs.libs.sample.index.design.module.mediator;

import java.util.ArrayList;

/**
 * 窗体类
 * Created by huangshuo on 17/10/30.
 */

public abstract class Window {

    public ArrayList<Pane> lists = new ArrayList<>();

    public void register(Pane pane) {
        lists.add(pane);
    }

    public abstract void operation(Pane pane);
}
