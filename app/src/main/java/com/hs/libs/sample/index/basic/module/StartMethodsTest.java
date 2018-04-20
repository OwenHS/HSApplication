package com.hs.libs.sample.index.basic.module;

import android.content.Context;
import android.content.Intent;

import com.hs.libs.sample.index.basic.AbstractBasicAndroid;
import com.hs.libs.sample.index.basic.startmethod.StandardActivity;
import com.hs.libs.sample.index.basic.startmethod.StartMethodMainActivity;

/**
 * Created by huangshuo on 2018/3/6.
 */

public class StartMethodsTest extends AbstractBasicAndroid {

    @Override
    public String getTitle() {
        return "启动模式测试";
    }

    @Override
    public void startTest(Context context) {
        StartMethodMainActivity.activityNames.clear();
        Intent intent = new Intent(context, StandardActivity.class);
        context.startActivity(intent);
    }
}
