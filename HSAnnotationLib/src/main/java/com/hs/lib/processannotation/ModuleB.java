package com.hs.lib.processannotation;

import com.hs.lib.processannotation.annotation.Module;
import com.hs.lib.processannotation.annotation.ObjectProvider;

/**
 * Created by owen on 16-1-4.
 * <p/>
 * 组件二
 */
@Module
public class ModuleB {
    @ObjectProvider
    public ClassB getModuleBInfo() {
        return new ClassB("ModuleB");
    }
}
