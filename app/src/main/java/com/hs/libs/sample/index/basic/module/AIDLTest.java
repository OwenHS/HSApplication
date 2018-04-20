package com.hs.libs.sample.index.basic.module;

import android.content.Context;
import android.content.Intent;

import com.hs.libs.sample.index.basic.AbstractBasicAndroid;
import com.hs.libs.sample.index.basic.aidltest.AIDLTestActivity;

/**
 * Created by huangshuo on 2018/3/7.
 */

public class AIDLTest extends AbstractBasicAndroid {

    @Override
    public String getTitle() {
        return "AIDL跨进程通信";
    }

    @Override
    public void startTest(Context context) {
        Intent intent = new Intent(context, AIDLTestActivity.class);
        context.startActivity(intent);
    }
}