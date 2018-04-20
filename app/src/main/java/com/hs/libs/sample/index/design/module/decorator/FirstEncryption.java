package com.hs.libs.sample.index.design.module.decorator;

/**
 * Created by huangshuo on 17/10/17.
 */

public class FirstEncryption extends Decorator {

    @Override
    public String encryption(String data) {
        return "{第一层加密=》" + super.encryption(data) + "《=第一层加密}";
    }
}
