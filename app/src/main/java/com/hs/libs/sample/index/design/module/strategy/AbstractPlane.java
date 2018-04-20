package com.hs.libs.sample.index.design.module.strategy;

import android.util.Log;

/**
 * 飞机抽象类
 * Created by huangshuo on 17/10/17.
 */

public abstract class AbstractPlane {
    /**
     * 飞机名称
     */
    protected String playName;
    /**
     * 飞机航班号
     */
    protected String playNumber;
    /**
     * 飞机起飞方式
     */
    protected AbstartTakeOff mTakeOff;
    /**
     * 飞机飞行速度
     */
    protected AbstractFlySpeed flySpeed;

    public void move(){
        Log.d("owen","飞机名称："+playName);
        Log.d("owen","飞机航班号："+playNumber);
        mTakeOff.takeOff();
        flySpeed.fly();
        Log.d("owen","-----------------------");
    }

}
