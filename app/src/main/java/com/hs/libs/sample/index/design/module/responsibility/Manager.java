package com.hs.libs.sample.index.design.module.responsibility;

import android.util.Log;

/**
 * Created by huangshuo on 17/10/27.
 */

public class Manager extends AbstractSuperior {
    @Override
    public void Approval(int days) {
        if(days<10){
            Log.d("owen","我是经理，我批准你"+days+"天的假期");
        }else{
            if(superior != null){
                superior.Approval(days);
            }else{
                Log.d("owen", "无上级可以批准你的假期");
            }
        }
    }
}
