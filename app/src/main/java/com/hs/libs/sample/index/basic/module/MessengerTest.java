package com.hs.libs.sample.index.basic.module;

import android.content.Context;
import android.content.Intent;

import com.hs.libs.sample.index.basic.AbstractBasicAndroid;
import com.hs.libs.sample.index.basic.aidltest.MessengerTestActivity;

/**
 * Created by huangshuo on 2018/3/8.
 */

public class MessengerTest extends AbstractBasicAndroid {
    @Override
    public String getTitle() {
        return "Messenger跨进程通信";
    }

    @Override
    public void startTest(Context context) {
        Intent intent = new Intent(context, MessengerTestActivity.class);
        context.startActivity(intent);
    }
}
