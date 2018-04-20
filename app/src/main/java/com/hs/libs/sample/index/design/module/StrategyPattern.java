package com.hs.libs.sample.index.design.module;


import com.hs.libs.sample.index.design.module.strategy.AirPlane;
import com.hs.libs.sample.index.design.module.strategy.Fighter;
import com.hs.libs.sample.index.design.module.strategy.Harrier;
import com.hs.libs.sample.index.design.module.strategy.Helicopter;

/**
 * Created by huangshuo on 17/10/17.
 *
 * Sunny软件公司欲开发一款飞机模拟系统，该系统主要模拟不同种类飞机的飞行特征与起飞特征，
 * 需要模拟的飞机种类及其特征如表24-1所示：

 飞机种类                      起飞特征                       飞行特征
 直升机(Helicopter)     垂直起飞(VerticalTakeOff)         亚音速飞行(SubSonicFly)
 客机(AirPlane)         长距离起飞(LongDistanceTakeOff)   亚音速飞行(SubSonicFly)
 歼击机(Fighter)        长距离起飞(LongDistanceTakeOff)    超音速飞行(SuperSonicFly)
 鹞式战斗机(Harrier)     垂直起飞(VerticalTakeOff)         超音速飞行(SuperSonicFly)
 为将来能够模拟更多种类的飞机，试采用策略模式设计该飞机模拟系统。
 */

public class StrategyPattern extends AbstractPattern {

    @Override
    public String getTitle() {
        return "策略模式";
    }

    @Override
    public void testPattern() {
        AirPlane airPlane = new AirPlane();
        airPlane.move();

        Fighter fighter = new Fighter();
        fighter.move();

        Harrier harrier = new Harrier();
        harrier.move();

        Helicopter helicopter = new Helicopter();
        helicopter.move();
    }

}
