package com.hs.libs.sample.index.basic.module;

import android.content.Context;
import android.content.Intent;

import com.hs.libs.sample.index.basic.AbstractBasicAndroid;
import com.hs.libs.sample.index.basic.eventtest.EventTestActivity;

/**
 * Created by huangshuo on 2018/3/8.
 */

public class EventTest extends AbstractBasicAndroid {

    @Override
    public String getTitle() {
        return "事件分发机制";
    }

    @Override
    public void startTest(Context context) {
        Intent intent = new Intent(context, EventTestActivity.class);
        context.startActivity(intent);
    }
}