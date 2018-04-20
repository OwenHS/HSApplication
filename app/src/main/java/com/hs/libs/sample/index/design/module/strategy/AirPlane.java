package com.hs.libs.sample.index.design.module.strategy;

/**
 * Created by huangshuo on 17/10/17.
 */

public class AirPlane extends AbstractPlane {
    public AirPlane(){
        playName = "客机";
        playNumber = "No.2";
        mTakeOff = new LongDistanceTakeOff();
        flySpeed = new SubSonicFly();
    }
}
