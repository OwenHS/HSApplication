package com.hs.libs.sample.index.design.module.proxy;

import android.util.Log;

/**
 * 查询实体类
 * Created by huangshuo on 17/10/18.
 */
public class RealSearch extends AbstractSearch {
    @Override
    public String doSearch(String userId, String keyword) {
        Log.d("owen", String.format("用户%s使用关键词%s查询商务信息！", userId, keyword));
        return "查询成功的返回";
    }
}
