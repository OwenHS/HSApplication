package com.hs.libs.sample.index.knowledge.module;


import android.content.Intent;

import com.hs.libs.sample.common.HSModulesBaseActivity;
import com.hs.libs.sample.index.knowledge.activity.TestKnowLedgeActivity;

import java.io.Serializable;

/**
 * Created by huangshuo on 17/9/29.
 */

public abstract class AbstractKnowledge implements Serializable {

    public static final String KnowledgeType =  "KnowledgeType";

    /**
     * 开启设计模式界面
     * @param context
     */
    public void startKnowledge(HSModulesBaseActivity context){
        Intent intent = new Intent(context, TestKnowLedgeActivity.class);
        intent.putExtra(KnowledgeType,this);
        context.showActivity(context,intent);
    }

    public abstract String getTitle();

    /**
     * 启动设计模式（测试）
     */
    public abstract void testKnowledge();
}
