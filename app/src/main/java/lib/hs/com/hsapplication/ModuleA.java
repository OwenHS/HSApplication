package lib.hs.com.hsapplication;

import com.hs.lib.processannotation.annotation.Module;
import com.hs.lib.processannotation.annotation.ObjectProvider;
import com.hs.lib.processannotation.annotation.Singleton;

/**
 * Created by owen on 16-1-4.
 *
 * 组件一
 */
@Module(objectInjects = {ApiA.class,ApiB.class})
public class ModuleA {

    @ObjectProvider
    @Singleton
    public ClassA getModuleAInfo() {
        return new ClassA(1);
    }

    @ObjectProvider
    public ClassB getModuleBInfo() {
        return new ClassB("ModuleB");
    }

}
