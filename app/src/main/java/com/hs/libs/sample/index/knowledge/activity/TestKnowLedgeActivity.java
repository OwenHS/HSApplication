package com.hs.libs.sample.index.knowledge.activity;

import android.content.Intent;

import com.hs.lib.inject.annotation.ViewInject;
import com.hs.libs.sample.R;
import com.hs.libs.sample.index.knowledge.module.AbstractKnowledge;

import lib.hs.com.hsbaselib.HSBaseActivity;

/**
 * Created by huangshuo on 17/11/7.
 */
@ViewInject(id = R.layout.activity_knowledge_result)
public class TestKnowLedgeActivity extends HSBaseActivity {
    AbstractKnowledge pattern;

    @Override
    public void getIntentData(Intent intent) {
        super.getIntentData(intent);
        pattern = (AbstractKnowledge) intent.getSerializableExtra(AbstractKnowledge.KnowledgeType);
    }

    @Override
    public void initData() {
        super.initData();
        pattern.testKnowledge();
    }

}
