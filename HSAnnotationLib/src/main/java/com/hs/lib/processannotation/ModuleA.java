package com.hs.lib.processannotation;

import com.hs.lib.processannotation.annotation.Module;
import com.hs.lib.processannotation.annotation.ObjectProvider;

/**
 * Created by owen on 16-1-4.
 *
 * 组件一
 */

@Module
public class ModuleA {

    @ObjectProvider
    public ClassA getModuleAInfo() {
        return new ClassA("ModuleA");
    }

}
