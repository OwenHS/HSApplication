package com.hs.libs.sample.index.design.activity;

import android.content.Intent;

import com.hs.lib.inject.annotation.ViewInject;
import com.hs.libs.sample.R;
import com.hs.libs.sample.common.HSModulesBaseActivity;
import com.hs.libs.sample.index.design.module.AbstractPattern;

/**
 * 设计模式测试类
 */
@ViewInject(id = R.layout.activity_pattern_result)
public class TestPatternActivity extends HSModulesBaseActivity {

    AbstractPattern pattern;

    @Override
    public void getIntentData(Intent intent) {
        super.getIntentData(intent);
        pattern = (AbstractPattern) intent.getSerializableExtra(AbstractPattern.PatternType);
    }

    @Override
    public void initData() {
        super.initData();
        pattern.testPattern();
    }



}
