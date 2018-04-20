package com.hs.libs.sample.index.design.module;

import android.util.Log;

import com.hs.libs.sample.index.design.module.bridge.AbstractFormat;
import com.hs.libs.sample.index.design.module.bridge.MySqlDatabase;
import com.hs.libs.sample.index.design.module.bridge.OracleDataBase;
import com.hs.libs.sample.index.design.module.bridge.PdfFormat;
import com.hs.libs.sample.index.design.module.bridge.TxtFormat;

/**
 * 桥接模式测试类
 *
 * Sunny软件公司欲开发一个数据转换工具，可以将数据库中的数据转换成多种文件格式，
 * 例如txt、xml、pdf等格式，同时该工具需要支持多种不同的数据库。
 * 试使用桥接模式对其进行设计。
 *
 * Created by huangshuo on 17/10/27.
 */

public class BridgePattern extends AbstractPattern{

    @Override
    public String getTitle() {
        return "桥接模式";
    }

    @Override
    public void testPattern() {
        AbstractFormat format1 = new PdfFormat(new OracleDataBase());
        Log.d("owen",format1.translate());

        AbstractFormat format2 = new TxtFormat(new MySqlDatabase());
        Log.d("owen",format2.translate());
    }

}
