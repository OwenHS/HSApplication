package com.hs.libs.sample.index.home.instance;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.hs.libs.sample.index.home.AbstractTestItem;

/**
 * Created by huangshuo on 17/11/16.
 */

public class TestOpenAnotherApp  extends AbstractTestItem {


    @Override
    public void startItem(Context context) {
        //要调用另一个APP的activity所在的包名
        String packageName = "com.huaiye.fyzx2";
        Intent intent = new Intent();
        PackageManager packageManager = context.getPackageManager();
        intent = packageManager.getLaunchIntentForPackage(packageName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TOP) ;
        context.startActivity(intent);

    }

    @Override
    public String getTitle() {
        return "打开app";
    }

}

