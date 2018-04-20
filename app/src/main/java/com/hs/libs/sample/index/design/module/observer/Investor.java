package com.hs.libs.sample.index.design.module.observer;

import android.util.Log;

/**
 * 投资者
 * Created by huangshuo on 17/10/23.
 */

public class Investor extends Observer{

    @Override
    public void update(String action) {
        Log.d("owen",this.getName() + "发现股票正在" + action);
        switch (action){
            case "上涨":
                Log.d("owen","他准备抛出");
                break;
            case "下跌":
                Log.d("owen","他准备买入");
                break;
        }
    }
}
