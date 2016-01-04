package com.hs.lib.processannotation;

/**
 * Created by owen on 15-12-30.
 */
public class hsmain {

    public static void main(String[] args){

        //提交依赖注入的实例化文件
        ObjectGraph.makeGraph(new ModuleA(),new ModuleB());

    }
}
