package com.hs.libs.sample.index.home;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 文 件 名：TestItemFactory
 * 描    述：测试项目工厂类
 * 创 建 人: OWEN_HUANG
 * 创建日期: 2017/8/4 10:36
 */

public class TestItemFactory {


    public static AbstractTestItem createTestItem(Class<? extends AbstractTestItem> itemType) {
        AbstractTestItem item = null;
        try {
            Constructor constructor = itemType.getDeclaredConstructor();
            constructor.setAccessible(true);
            item = (AbstractTestItem) constructor.newInstance();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return item;
    }

}
