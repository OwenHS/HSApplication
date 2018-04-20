package com.hs.libs.sample.index.home.instance;

import android.content.Context;
import android.content.Intent;

import com.hs.libs.sample.index.basic.AndroidBasicActivity;
import com.hs.libs.sample.index.home.AbstractTestItem;

/**
 * Created by huangshuo on 2018/3/6.
 */

public class AndroidBasicItem extends AbstractTestItem {


    @Override
    public void startItem(Context context) {
        Intent intent = new Intent(context, AndroidBasicActivity.class);
        context.startActivity(intent);
    }

    @Override
    public String getTitle() {
        return "Android基础知识";
    }

}
