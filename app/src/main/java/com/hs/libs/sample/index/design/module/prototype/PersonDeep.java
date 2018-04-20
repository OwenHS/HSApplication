package com.hs.libs.sample.index.design.module.prototype;

/**
 * Created by huangshuo on 17/10/19.
 */

public class PersonDeep implements Cloneable{
    public Head mHead;

    public PersonDeep(Head mHead){
        this.mHead = mHead;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        PersonDeep personDeep = (PersonDeep) super.clone();
        personDeep.mHead = (Head) mHead.clone();

        return personDeep;
    }
}
