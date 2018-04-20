package com.hs.libs.sample.index.design.module.strategy;

/**
 * Created by huangshuo on 17/10/17.
 */

public class Fighter extends AbstractPlane {
    public Fighter(){
        playName = "歼击机";
        playNumber = "No.3";
        mTakeOff = new LongDistanceTakeOff();
        flySpeed = new SuperSonicFly();
    }
}
