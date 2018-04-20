package com.hs.libs.sample.index.design.module.strategy;

import android.util.Log;

/**
 * Created by huangshuo on 17/10/17.
 */

public class VerticalTakeOff extends AbstartTakeOff {

    @Override
    public void takeOff() {
        Log.d("owen","我是垂直起飞");
    }
}
