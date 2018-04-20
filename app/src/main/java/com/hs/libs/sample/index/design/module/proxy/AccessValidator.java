package com.hs.libs.sample.index.design.module.proxy;

import android.util.Log;

/**
 * 验证用户类
 * Created by huangshuo on 17/10/18.
 */

public class AccessValidator {

    //模拟实现登录验证
    public boolean validate(String userId) {
        Log.d("owen", "在数据库中验证用户'" + userId + "'是否是合法用户？");
        if (userId.equals("杨过")) {
            Log.d("owen", String.format("%s登录成功！", userId));
            return true;
        } else {
            Log.d("owen", String.format("%s登录失败！", userId));
            return false;
        }
    }

}
