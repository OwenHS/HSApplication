package com.hs.lib.processannotation;

import com.hs.lib.processannotation.core.Loader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by owen on 16-1-4.
 */
public class Modules {

    /**
     * 用来加载module
     * @param modules  所有含有依赖的module
     */
    public static void loadModules(Loader loader,Object[] modules) {

        Map<Class<?>,String> moduleInfo = new HashMap<>();

        for(Object module : modules) {
            Class<?> moduleClass = module.getClass();
            moduleInfo.put(moduleClass,moduleClass.getSimpleName());
        }
    }
}
