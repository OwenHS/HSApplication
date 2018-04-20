package com.hs.libs.sample;

import android.widget.ListView;

/**
 * Created by huangshuo on 17/9/27.
 */

public class ClassA1 extends AbstractA {

    ListView a;

    public ClassA1(){
        test = new ClassTest();
        test.str = "I'm ClassA1";
    }

    public String show(){
        return test.str;
    }
}
