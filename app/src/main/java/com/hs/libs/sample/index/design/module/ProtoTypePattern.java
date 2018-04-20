package com.hs.libs.sample.index.design.module;

import com.hs.libs.sample.index.design.module.prototype.Face;
import com.hs.libs.sample.index.design.module.prototype.Head;
import com.hs.libs.sample.index.design.module.prototype.PersonDeep;
import com.hs.libs.sample.index.design.module.prototype.PersonShallow;

/**
 * 原型模式测试类
 * Created by huangshuo on 17/10/19.
 */

public class ProtoTypePattern extends AbstractPattern {

    @Override
    public String getTitle() {
        return "原型模式";
    }

    @Override
    public void testPattern() {
        try {
            testShallowCopy();
            testDeepCopy();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }


    private void testShallowCopy() throws CloneNotSupportedException {

        PersonShallow p = new PersonShallow(23, "zhang");
        PersonShallow p1 = (PersonShallow) p.clone();

        System.out.println("p.getName().hashCode() : " + p.getName().hashCode());
        System.out.println("p1.getName().hashCode() : " + p1.getName().hashCode());

        String result = p.getName().hashCode() == p1.getName().hashCode()
                ? "clone是浅拷贝的" : "clone是深拷贝的";
        System.out.println(result);

    }


    private void testDeepCopy() throws CloneNotSupportedException {
        PersonDeep pDeep = new PersonDeep(new Head(new Face()));
        PersonDeep pDeepCopy = (PersonDeep) pDeep.clone();

        System.out.println("pDeep.mHead.hashCode() : " + pDeep.mHead.hashCode());
        System.out.println("pDeepCopy.mHead.hashCode() : " + pDeepCopy.mHead.hashCode());

        String result = pDeep.mHead.hashCode() == pDeepCopy.mHead.hashCode()
                ? "clone是浅拷贝的" : "clone是深拷贝的";
        System.out.println(result);


        System.out.println("pDeep.mHead.face.hashCode() : " + pDeep.mHead.face.hashCode());
        System.out.println("pDeepCopy.mHead.face.hashCode() : " + pDeepCopy.mHead.face.hashCode());

        String result1 = pDeep.mHead.face.hashCode() == pDeepCopy.mHead.face.hashCode()
                ? "clone是浅拷贝的" : "clone是深拷贝的";
        System.out.println(result1);
    }
}
