package com.hs.libs.sample.index.design.module;

import android.util.Log;

import com.hs.libs.sample.index.design.module.decorator.DataEncryption;
import com.hs.libs.sample.index.design.module.decorator.FirstEncryption;
import com.hs.libs.sample.index.design.module.decorator.SecondEncryption;

/**
 * 装饰模式测试类
 * Sunny软件公司欲开发了一个数据加密模块，可以对字符串进行加密。
 * 最简单的加密算法通过对字母进行移位来实现，同时还提供了稍复杂的逆向输出加密，
 * 还提供了更为高级的求模加密。用户先使用最简单的加密算法对字符串进行加密，
 * 如果觉得还不够可以对加密之后的结果使用其他加密算法进行二次加密，当然也可以进行第三次加密。
 * 试使用装饰模式设计该多重加密系统。
 * Created by huangshuo on 17/10/17.
 */

public class DecoratorPattern extends AbstractPattern {

    @Override
    public String getTitle() {
        return "装饰模式";
    }

    @Override
    public void testPattern() {
        DataEncryption encryption = new DataEncryption();
        FirstEncryption firstEncryption = new FirstEncryption();
        SecondEncryption secondEncryption = new SecondEncryption();

        firstEncryption.setEncryption(encryption);
        secondEncryption.setEncryption(firstEncryption);

        Log.d("owen",secondEncryption.encryption("我是大神"));
    }

}
