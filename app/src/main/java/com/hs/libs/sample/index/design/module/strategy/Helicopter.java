package com.hs.libs.sample.index.design.module.strategy;

/**
 * Created by huangshuo on 17/10/17.
 */

public class Helicopter extends AbstractPlane{

    public Helicopter(){
        playName = "直升飞机";
        playNumber = "No.1";
        mTakeOff = new VerticalTakeOff();
        flySpeed = new SubSonicFly();
    }
}
