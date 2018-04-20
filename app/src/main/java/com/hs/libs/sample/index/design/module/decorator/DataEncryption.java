package com.hs.libs.sample.index.design.module.decorator;

/**
 * 数据基础加密类
 * Created by huangshuo on 17/10/17.
 */

public class DataEncryption implements IEncryption {

    @Override
    public String encryption(String data) {

        return "{基础加密=》" + data + "《=基础加密}";
    }
}
