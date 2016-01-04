package com.hs.lib.processannotation.core.loaders;

import com.hs.lib.processannotation.core.Loader;
import com.hs.lib.processannotation.core.adapter.GeneratedAdapters;
import com.hs.lib.processannotation.core.adapter.ModuleAdapter;
import com.hs.lib.processannotation.core.internal.Memoizer;

/**
 * Created by owen on 16-1-4.
 */
public class DefaultLoader extends Loader{

    private final Memoizer<Class<?>, ModuleAdapter<?>> loadedAdapters =
            new Memoizer<Class<?>, ModuleAdapter<?>>() {
                @Override
                protected ModuleAdapter<?> create(Class<?> type) {
                    //根据＠module修饰的类找到生成的＋ModuleAdapter的新java文件
                    ModuleAdapter<?> result =
                            instantiate(type.getName().concat(GeneratedAdapters.MODULE_ADAPTER_SUFFIX), type.getClassLoader());
                    if (result == null) {
                        throw new IllegalStateException("Module adapter for " + type + " could not be loaded. "
                                + "Please ensure that code generation was run for this module.");
                    }
                    return result;
                }
            };
}
