package com.hs.libs.sample.index.design.module;

import com.hs.libs.sample.index.design.module.factory.method.AbstractGraphFactory;
import com.hs.libs.sample.index.design.module.factory.method.RectangleFactory;
import com.hs.libs.sample.index.design.module.factory.simple.Graph;
import com.hs.libs.sample.index.design.module.factory.simple.GraphFactory;
import com.hs.libs.sample.index.design.module.factory.simple.UnSupportedShapeException;

/**
 * （1）简单工厂（静态工厂）模式
 * （2）工厂方法模式
 * （3）抽象工厂模式
 *
 * 使用工厂模式设计一个可以创建不同几何形状（如圆形、方形和三角形等）的绘图工具，
 * 每个几何图形都具有绘制draw()和擦除erase()两个方法，要求在绘制不支持的几何图形时，
 * 提示一个UnSupportedShapeException。
 */

public class FactoryPattern extends AbstractPattern {


    @Override
    public String getTitle() {
        return "简单工厂模式";
    }

    @Override
    public void testPattern() {

        startSimpleFactoryPattern();
        startMethodFactoryPattern();

    }

    /**
     * 简单工厂模式
     */
    private void startSimpleFactoryPattern() {
        try {
            Graph graph = GraphFactory.createGraph("Square");
            graph.draw();
            graph.erase();
        } catch (UnSupportedShapeException e) {
            e.printStackTrace();
        }
    }

    /**
     * 工厂方法模式
     * 选择方式交由客户选择，这样，就不会出现要添加另一种图形要修改工厂类的情况
     * 这样做到了对修改关闭，对扩展开放。
     */
    private void startMethodFactoryPattern() {
        AbstractGraphFactory factory = new RectangleFactory();
        Graph graph = factory.createGraph();
        graph.draw();
        graph.erase();
    }

}
