package com.hs.lib.processannotation.core.adapter;

import com.hs.lib.processannotation.node.NodeController;

/**
 * Created by owen on 16-1-4.
 */
public abstract class ModuleAdapter<T> {

    public final String[] injects;

    public ModuleAdapter(String[] injects){
        this.injects = injects;
    }

    public abstract void getNodes(NodeController nodeController, T value);

}
