package com.hs.lib.processannotation;

import com.hs.lib.processannotation.core.Loader;
import com.hs.lib.processannotation.core.adapter.ModuleAdapter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by owen on 16-1-4.
 */
public class Modules {

    /**
     * 用来加载module
     * @param modules  所有含有依赖的module
     */
    public static Map<ModuleAdapter<?>,Object> loadModules(Loader loader,Object[] modules) {

        //存放获得的processor编译所的的文件对象
        Map<ModuleAdapter<?>,Object> seedAdapter = new LinkedHashMap<>(modules.length);

        //循环module注解的文件，将对应的生成的ModuleAdapter，以及来Module对象装入map中
        for(int i = 0 ;i < modules.length;i++) {
            if(modules[i] instanceof Class<?>) {
                //目前没有用到
                //ModuleAdapter<?> moduleAdapter = loader.getModuleAdapter((Class<?>) modules[i]);
            }
            else {
                ModuleAdapter<?> moduleAdapter = loader.getModuleAdapter(modules[i].getClass());
                seedAdapter.put(moduleAdapter, modules[i]);
            }
        }
        return seedAdapter;
    }
}
