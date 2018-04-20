package com.hs.libs.sample;

/**
 * Created by huangshuo on 17/9/27.
 */

public class ClassA2 extends AbstractA {
    public ClassA2() {
        test = new ClassTest();
        test.str = "I'm ClassA2";
    }

    public String show() {
        return test.str;
    }
}
