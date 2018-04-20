package com.hs.libs.sample.index.home.instance;

import android.content.Context;
import android.content.Intent;

import com.hs.libs.sample.index.design.activity.DesignPatternActivity;
import com.hs.libs.sample.index.home.AbstractTestItem;

/**
 * 文 件 名：DesignPatternItem
 * 描    述：设计模式设计类
 * 创 建 人: OWEN_HUANG
 * 创建日期: 2017/8/4 10:40
 */

public class DesignPatternItem extends AbstractTestItem{


    @Override
    public void startItem(Context context) {
        Intent intent = new Intent(context,DesignPatternActivity.class);
        context.startActivity(intent);
    }

    @Override
    public String getTitle() {
        return "设计模式";
    }

}
