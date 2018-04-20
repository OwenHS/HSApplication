package com.hs.libs.sample.index.home.instance;

import android.content.Context;
import android.content.Intent;

import com.hs.libs.sample.index.home.AbstractTestItem;
import com.hs.libs.sample.index.http.activity.HttpActivity;

/**
 * Created by huangshuo on 17/11/7.
 */

public class HttpItem extends AbstractTestItem {
    @Override
    public void startItem(Context context) {
        Intent intent = new Intent(context,HttpActivity.class);
        context.startActivity(intent);
    }

    @Override
    public String getTitle() {
        return "Http请求";
    }
}
