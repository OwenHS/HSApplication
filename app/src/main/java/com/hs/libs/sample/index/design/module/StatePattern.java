package com.hs.libs.sample.index.design.module;

import com.hs.libs.sample.index.design.module.state.LoginState;
import com.hs.libs.sample.index.design.module.state.StateControl;

/**
 * 状态模式测试类
 * Created by huangshuo on 17/10/24.
 */

public class StatePattern extends AbstractPattern {
    @Override
    public String getTitle() {
        return "状态模式";
    }

    @Override
    public void testPattern() {
        //一开始状态是未登录，openHome打不开
        StateControl control = new StateControl();
        control.openHome();

        //状态置成了可登陆，openHOme就可以打开了
        control.setState(new LoginState());
        control.openHome();
    }
}
