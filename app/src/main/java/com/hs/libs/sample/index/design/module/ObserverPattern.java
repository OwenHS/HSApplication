package com.hs.libs.sample.index.design.module;

import com.hs.libs.sample.index.design.module.observer.Investor;
import com.hs.libs.sample.index.design.module.observer.Observer;
import com.hs.libs.sample.index.design.module.observer.Stock;
import com.hs.libs.sample.index.design.module.observer.Subject;

/**
 * 观察者模式测试类
 *
 * Sunny软件公司欲开发一款实时在线股票软件，该软件需提供如下功能：
 * 当股票购买者所购买的某支股票价格变化幅度达到5%时，
 * 系统将自动发送通知（包括新价格）给购买该股票的所有股民。试使用观察者模式设计并实现该系统
 * Created by huangshuo on 17/10/23.
 */

public class ObserverPattern extends AbstractPattern{
    @Override
    public String getTitle() {
        return "观察者模式";
    }

    @Override
    public void testPattern() {

        Subject stock = new Stock();

        Observer p1 = new Investor();
        p1.setName("owen");

        Observer p2 = new Investor();
        p2.setName("huang");

        stock.attach(p1);
        stock.attach(p2);

        stock.notifyObservers("上涨");

    }
}
