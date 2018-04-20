package com.hs.libs.sample.index.design.module.commond;

import java.util.ArrayList;

/**
 * Created by huangshuo on 17/10/27.
 */

public class Menu {

    ArrayList<MenuItem> items = new ArrayList<>();

    public void addMenuItem(MenuItem menuItem){
            items.add(menuItem);
    }

    public void display(){
        for(MenuItem item : items){
            item.display();
        }
    }

    public void onclick(int i) {
        items.get(i).onClick();
    }
}
