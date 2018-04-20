package com.hs.libs.sample.index.design.module.state;

/**
 * 状态基类
 * Created by huangshuo on 17/10/24.
 */

public abstract class AbstractState {

    /**
     * 打开主页
     */
    public abstract void openHome();

    /**
     * 打开我的界面
     */
    public abstract void openUser();
}
