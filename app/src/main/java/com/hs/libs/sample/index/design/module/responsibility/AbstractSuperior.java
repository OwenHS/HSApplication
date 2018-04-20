package com.hs.libs.sample.index.design.module.responsibility;

/**
 * 上级基类
 * Created by huangshuo on 17/10/27.
 */

public abstract class AbstractSuperior {

    public AbstractSuperior superior;

    public abstract void Approval(int days);

    public AbstractSuperior setSuperior(AbstractSuperior superior) {
        this.superior = superior;
        return superior;
    }
}
