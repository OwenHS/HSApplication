package com.hs.libs.sample.index.design.module.proxy;

import android.util.Log;

/**
 * Created by huangshuo on 17/10/18.
 */

public class Logger {

    public void d(String userId) {
        Log.d("owen", String.format("更新数据库，用户%s查询次数加1！", userId));
    }
}
