package com.hs.libs.sample.index.home.instance;

import android.content.Context;
import android.content.Intent;

import com.hs.libs.sample.index.home.AbstractTestItem;
import com.hs.libs.sample.index.knowledge.activity.KnowledgeMainActivity;

/**
 * Created by huangshuo on 17/11/7.
 */

public class KnowLedgeItem extends AbstractTestItem {
    @Override
    public void startItem(Context context) {
        Intent intent = new Intent(context,KnowledgeMainActivity.class);
        context.startActivity(intent);
    }

    @Override
    public String getTitle() {
        return "Java知识点测试";
    }
}
