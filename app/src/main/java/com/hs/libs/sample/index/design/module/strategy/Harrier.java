package com.hs.libs.sample.index.design.module.strategy;

/**
 * Created by huangshuo on 17/10/17.
 */

public class Harrier extends AbstractPlane {

    public Harrier() {
        playName = "鹞式战斗机";
        playNumber = "No.4";
        mTakeOff = new VerticalTakeOff();
        flySpeed = new SuperSonicFly();
    }
}
