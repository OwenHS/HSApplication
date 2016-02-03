package com.hs.lib.processannotation.node;

/**
 * Created by owen on 16-1-6.
 */
public abstract class Node<T> {

    public static final boolean SINGLETON = true;

    public boolean isSingle = false;

    protected Node(){

    }

    protected Node(boolean isSingle){
        this.isSingle = isSingle;
    }

    //用于注入
    public void injectMembers(T t) {

    }

    public void attach(NodeController nodeController) {
    }


    public T get() {
        throw new UnsupportedOperationException("No injectable constructor on " + getClass().getName());
    }
}
