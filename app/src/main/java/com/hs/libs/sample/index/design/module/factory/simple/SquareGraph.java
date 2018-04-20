package com.hs.libs.sample.index.design.module.factory.simple;

import android.util.Log;

/**
 * Created by huangshuo on 17/10/17.
 */

public class SquareGraph extends Graph {

    @Override
    public void draw() {
        super.draw();
        Log.d("owen", "SquareGraph draw");
    }

    @Override
    public void erase() {
        super.erase();
        Log.d("owen", "SquareGraph erase");
    }
}
