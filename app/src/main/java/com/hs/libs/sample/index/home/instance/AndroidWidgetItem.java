package com.hs.libs.sample.index.home.instance;

import android.content.Context;
import android.content.Intent;

import com.hs.libs.sample.index.basic.AndroidBasicActivity;
import com.hs.libs.sample.index.home.AbstractTestItem;
import com.hs.libs.sample.index.widget.activity.WidgetMainActivity;

/**
 * Created by shuohuang on 18-4-20.
 */

public class AndroidWidgetItem extends AbstractTestItem {


    @Override
    public void startItem(Context context) {
        Intent intent = new Intent(context, WidgetMainActivity.class);
        context.startActivity(intent);
    }

    @Override
    public String getTitle() {
        return "Android控件";
    }

}