package com.hs.lib.processannotation.node;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by owen on 16-1-8.
 */
public class NodeController {

    public Map<String,Node<?>> methodStringMap = new HashMap<>();

    public Node<?> requestNode(String injectName) {
        return methodStringMap.get(injectName);
    }

    public void addNode(String nodeName ,Node<?> nodeValue) {

        if(nodeValue.isSingle){
            SingletonNode singletonNode = new SingletonNode();
            singletonNode.setOrignNode(nodeValue);
            singletonNode.setKeySingle(nodeName);
            methodStringMap.put(nodeName,singletonNode);
        }else{
            methodStringMap.put(nodeName,nodeValue);
        }
    }
}
