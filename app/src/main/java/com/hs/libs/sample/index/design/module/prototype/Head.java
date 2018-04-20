package com.hs.libs.sample.index.design.module.prototype;

/**
 * Created by huangshuo on 17/10/19.
 */

public class Head implements Cloneable {
    public Face face;

    public Head(Face face) {
        this.face = face;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
