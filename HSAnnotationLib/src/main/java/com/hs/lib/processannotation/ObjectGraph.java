package com.hs.lib.processannotation;

import com.hs.lib.processannotation.core.Loader;
import com.hs.lib.processannotation.core.adapter.ModuleAdapter;
import com.hs.lib.processannotation.core.loaders.DefaultLoader;
import com.hs.lib.processannotation.node.Node;
import com.hs.lib.processannotation.node.NodeController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by owen on 16-1-4.
 * 本类用于本类中的所有注入
 *
 * 目前有３个注解　＠Module :用于将对象和实例化联系起来
 *              ＠ObjectInject:注入的对象
 *              ＠ObjectProvider:实例化，用于注入
 *
 */
public abstract class ObjectGraph {

    //新建对象表，对注入进行前期准备工作
    public static ObjectGraph create(Object... obj) {
       return createGraph(new DefaultLoader(), obj);
    }

    /**
     *
     * @param loader 加载器，目前起到对一些java对象的缓存，当再次注入调用的时候，可以直接使用
     * @param obj   加载的被module注解注释的类，可以是多个。
     */
    public static ObjectGraph createGraph(Loader loader,Object... obj) {

       return HSObjectGraph.makeGraph(loader,obj);
    }

    //用于注入
    public abstract <T> T inject(T instance);

    static class HSObjectGraph extends ObjectGraph{

        public Map<String,ModuleAdapter<?>> injectStringMap;
        public Loader loader;

        public NodeController nodeController;

        public HSObjectGraph(Loader loader, Map<String,ModuleAdapter<?>> injectStringMap,NodeController nodeController) {
            this.injectStringMap = injectStringMap;
            this.loader = loader;
            this.nodeController = nodeController;
        }

        public static ObjectGraph makeGraph(Loader loader, Object[] obj) {
            Map<ModuleAdapter<?> ,Object> loadModuleAdapters = Modules.loadModules(loader, obj);

            Map<String,ModuleAdapter<?>> injectByModuleMap = new LinkedHashMap<>();

            NodeController nodeController = new NodeController();

            //遍历每个Module文件
            for(Map.Entry<ModuleAdapter<?>, Object> loadedModule : loadModuleAdapters.entrySet()) {
                //遍历每个module文件的injects变量，injects对应的moduleAdapter写入map
                ModuleAdapter<Object> moduleAdapter = (ModuleAdapter<Object>)loadedModule.getKey();
                for(String injectName : moduleAdapter.injects){
                    injectByModuleMap.put(injectName, moduleAdapter);
                }
                moduleAdapter.getNodes(nodeController, loadedModule.getValue());
            }

            return new HSObjectGraph(loader,injectByModuleMap,nodeController);
        }

        @Override
        public <T> T inject(T instance) {
            ClassLoader classLoader = instance.getClass().getClassLoader();
            //获取的是新文件的对象
            String injectName = instance.getClass().getName();
            Node<T> injectObjectNode = (Node<T>)loadObjectInject(injectName,classLoader);
            injectObjectNode.attach(nodeController);
            injectObjectNode.injectMembers(instance);
            return instance;
        }

        //用于得到有关ObjectInject的adapter对象
        private Node<?> loadObjectInject(String className, ClassLoader classLoader) {
            return loader.getAtInjectNode(className,classLoader);
        }
    }

}
