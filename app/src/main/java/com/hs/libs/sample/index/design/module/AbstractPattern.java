package com.hs.libs.sample.index.design.module;


import android.content.Intent;

import com.hs.libs.sample.common.HSModulesBaseActivity;
import com.hs.libs.sample.index.design.activity.TestPatternActivity;

import java.io.Serializable;

/**
 * Created by huangshuo on 17/9/29.
 */

public abstract class AbstractPattern implements Serializable {

    public static final String PatternType =  "PatternType";

    /**
     * 开启设计模式界面
     * @param context
     */
    public void startPattern(HSModulesBaseActivity context){
        Intent intent = new Intent(context, TestPatternActivity.class);
        intent.putExtra(PatternType,this);
        context.showActivity(context,intent);
    }

    public abstract String getTitle();

    /**
     * 启动设计模式（测试）
     */
    public abstract void testPattern();
}
