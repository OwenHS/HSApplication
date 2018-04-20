package com.hs.libs.sample.index.design.module.decorator;

/**
 * 装饰抽象类
 * Created by huangshuo on 17/10/17.
 */

public class Decorator implements IEncryption{

    private IEncryption encryption;

    public void setEncryption(IEncryption encryption){
        this.encryption = encryption;
    }

    @Override
    public String encryption(String data) {
        return encryption.encryption(data);
    }
}
