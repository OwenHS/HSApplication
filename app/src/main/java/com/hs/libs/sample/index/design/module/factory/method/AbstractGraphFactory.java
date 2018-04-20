package com.hs.libs.sample.index.design.module.factory.method;

import com.hs.libs.sample.index.design.module.factory.simple.Graph;

/**
 * 图形工厂抽象类
 * Created by huangshuo on 17/10/18.
 */

public abstract class AbstractGraphFactory {
    abstract public Graph createGraph();
}
