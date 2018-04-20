package com.hs.libs.sample.index.design.module.factory.method;

import com.hs.libs.sample.index.design.module.factory.simple.Graph;
import com.hs.libs.sample.index.design.module.factory.simple.TriangleGraph;

/**
 * Created by huangshuo on 17/10/18.
 */

public class TriangleFactory extends AbstractGraphFactory {
    @Override
    public Graph createGraph() {
        return new TriangleGraph();
    }
}
