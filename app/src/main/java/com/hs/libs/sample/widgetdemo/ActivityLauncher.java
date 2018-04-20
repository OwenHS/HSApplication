package com.hs.libs.sample.widgetdemo;

import android.content.Intent;
import android.os.Bundle;

import com.hs.libs.sample.widgetdemo.extend.BaseActivity;


/**
 * TODO:功能说明
 *
 * @author: renzheng
 * @date: 2016-11-03 15:38
 */
public class ActivityLauncher extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, ActivityMain.class));
        finish();
    }
}
