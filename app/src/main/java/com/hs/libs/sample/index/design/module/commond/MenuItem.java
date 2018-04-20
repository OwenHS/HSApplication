package com.hs.libs.sample.index.design.module.commond;

import android.util.Log;

/**
 * 功能菜单类
 * Created by huangshuo on 17/10/27.
 */

public class MenuItem {

    AbstractCommond commond;
    String name;

    public MenuItem(String name, AbstractCommond commond) {
        this.commond = commond;
        this.name = name;
    }

    /**
     * 执行方法
     */
    void onClick() {
        commond.execute();
    }

    public void display() {
        Log.d("owen", "我是" + name + "功能菜单");
    }

}
