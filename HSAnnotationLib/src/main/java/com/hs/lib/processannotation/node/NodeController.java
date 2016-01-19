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


}
