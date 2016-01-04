package com.hs.lib.processannotation;

import com.hs.lib.processannotation.core.Loader;
import com.hs.lib.processannotation.core.loaders.DefaultLoader;

/**
 * Created by owen on 16-1-4.
 * 本类用于本类中的所有注入
 *
 * 目前有３个注解　＠Module :用于将对象和实例化联系起来
 *              ＠ObjectInject:注入的对象
 *              ＠ObjectProvider:实例化，用于注入
 *
 */
public class ObjectGraph {

    //新建对象表，对注入进行前期准备工作
    public static void makeGraph(Object... obj) {
        makeGraph(new DefaultLoader(), obj);
    }

    public static void makeGraph(Loader loader,Object... obj) {
        Modules.loadModules(loader,obj);
    }
}
