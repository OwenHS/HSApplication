package com.hs.libs.sample.index.design.module.bridge;

/**
 * 格式基类
 * Created by huangshuo on 17/10/27.
 */

public abstract class AbstractFormat {

    AbstractDatabase database;

    public AbstractFormat(AbstractDatabase database){
        this.database = database;
    }

    abstract public String translate();
}
