package com.hs.libs.sample.index.design.module.state;

/**
 * 登录登出状态管理类
 * Created by huangshuo on 17/10/24.
 */

public class StateControl {
    /**
     * 初始是未登录状态
     */
    private AbstractState state = new LogoutState();


    public void setState(AbstractState state) {
        this.state = state;
    }

    public void openHome(){
        state.openHome();
    }

}
