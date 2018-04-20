package com.hs.libs.sample.index.design.module;

import com.hs.libs.sample.index.design.module.responsibility.Director;
import com.hs.libs.sample.index.design.module.responsibility.GeneralManager;
import com.hs.libs.sample.index.design.module.responsibility.Manager;

/**
 * 责任链模式
 * Created by huangshuo on 17/10/27.
 *
 *  Sunny软件公司的OA系统需要提供一个假条审批模块：
 *  如果员工请假天数小于3天，主任可以审批该假条；
 *  如果员工请假天数大于等于3天，小于10天，经理可以审批；
 *  如果员工请假天数大于等于10天，小于30天，总经理可以审批；
 *  如果超过30天，总经理也不能审批，提示相应的拒绝信息。
 *  试用职责链模式设计该假条审批模块。
 *
 */

public class ChainOfResponsibilityPattern extends AbstractPattern{
    @Override
    public String getTitle() {
        return "责任链模式";
    }

    @Override
    public void testPattern() {
        Director director = new Director();
        director.setSuperior(new Manager()).setSuperior(new GeneralManager());

        director.Approval(28);
    }
}
