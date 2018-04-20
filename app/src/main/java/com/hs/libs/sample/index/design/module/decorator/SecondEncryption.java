package com.hs.libs.sample.index.design.module.decorator;

/**
 * Created by huangshuo on 17/10/17.
 */

public class SecondEncryption extends Decorator {

    @Override
    public String encryption(String data) {
        return "{第二层加密=》" + super.encryption(data) + "《=第二层加密}";
    }
}
