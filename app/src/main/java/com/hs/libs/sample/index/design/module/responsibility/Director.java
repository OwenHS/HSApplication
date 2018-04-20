package com.hs.libs.sample.index.design.module.responsibility;

import android.util.Log;

/**
 * 主任批准
 * Created by huangshuo on 17/10/27.
 */

public class Director extends AbstractSuperior {
    @Override
    public void Approval(int days) {
        if (days < 3) {
            Log.d("owen", "我是主任，我批准了你" + days + "天的假期");
        } else {
            if (superior != null) {
                superior.Approval(days);
            } else {
                Log.d("owen", "无上级可以批准你的假期");
            }

        }
    }
}
