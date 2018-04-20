package com.hs.libs.sample.index.design.module.factory.simple;

/**
 * 创建图形工厂类
 * Created by huangshuo on 17/10/17.
 */

public class GraphFactory {

    public static Graph createGraph(String type) throws UnSupportedShapeException {
        Graph graph = null;
        switch (type){
            case "Rectangle":
                graph = new RectangleGraph();
                break;
            case "Square":
                graph = new SquareGraph();
                break;
            case "Triangle":
                graph = new TriangleGraph();
                break;
            default:
                throw new UnSupportedShapeException();
        }

        return graph;
    }
}
