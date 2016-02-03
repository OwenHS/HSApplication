package com.hs.lib.processannotation.node;

/**
 * Created by owen on 16-1-20.
 */
public class SingletonNode<T> extends Node<T> {

    public SingletonNode() {
        super(Node.SINGLETON);
    }

    private String keySingle;

    /**
     * 装饰模式原始的node
     */
    private Node<T> orignNode;

    private T newInstance;

    //用于注入
    public void injectMembers(T t) {
        orignNode.injectMembers(t);
    }

    public void attach(NodeController nodeController) {
        orignNode.attach(nodeController);
    }


    public T get() {
        if (newInstance == null) {
            newInstance = orignNode.get();
        }
        return newInstance;
    }

    public Node<T> getOrignNode() {
        return orignNode;
    }

    public void setOrignNode(Node<T> orignNode) {
        this.orignNode = orignNode;
    }

    public String getKeySingle() {
        return keySingle;
    }

    public void setKeySingle(String keySingle) {
        this.keySingle = keySingle;
    }
}
