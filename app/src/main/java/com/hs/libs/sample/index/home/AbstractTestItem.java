package com.hs.libs.sample.index.home;

import android.content.Context;

/**
 * 文 件 名：AbstractTestItem
 * 描    述：首页测试类抽象接口
 * 创 建 人: OWEN_HUANG
 * 创建日期: 2017/8/4 09:37
 */

public abstract class AbstractTestItem {

    /**
     * 开启测试项
     */
    public abstract void startItem(Context context);

    public abstract String getTitle();
}
