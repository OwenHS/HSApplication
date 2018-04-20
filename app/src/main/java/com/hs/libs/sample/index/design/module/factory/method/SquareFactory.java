package com.hs.libs.sample.index.design.module.factory.method;

import com.hs.libs.sample.index.design.module.factory.simple.Graph;
import com.hs.libs.sample.index.design.module.factory.simple.SquareGraph;

/**
 * Created by huangshuo on 17/10/18.
 */

public class SquareFactory extends AbstractGraphFactory {
    @Override
    public Graph createGraph() {
        return new SquareGraph();
    }
}
