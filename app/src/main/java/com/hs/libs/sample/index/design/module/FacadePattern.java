package com.hs.libs.sample.index.design.module;

import com.hs.libs.sample.index.design.module.facade.EncryptFacade;

/**
 * 外观模式测试类
 *
 *  某软件公司欲开发一个可应用于多个软件的文件加密模块，
 *  该模块可以对文件中的数据进行加密并将加密之后的数据存储在一个新文件中，
 *  具体的流程包括三个部分，分别是：
 *  （1）读取源文件（2）加密（3）保存加密之后的文件
 *  其中，读取文件和保存文件使用流来实现，加密操作通过求模运算实现。
 *
 *  这三个操作相对独立，为了实现代码的独立重用，让设计更符合单一职责原则，
 *  这三个操作的业务代码封装在三个不同的类中。
 *  现使用外观模式设计该文件加密模块。
 *
 * Created by huangshuo on 17/10/19.
 */

public class FacadePattern extends AbstractPattern {

    @Override
    public String getTitle() {
        return "外观模式";
    }

    @Override
    public void testPattern() {
        EncryptFacade ef = new EncryptFacade();
        ef.fileEncrypt("src.txt", "des.txt");
    }
}
