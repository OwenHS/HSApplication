package com.hs.lib.log;

/**
 * 输出方式抽象接口
 * Created by huangshuo on 16/8/29.
 */
public abstract class BaseLogTask {

    /**
     * 打印日志类
     * @param type         日志类型
     * @param tagStr       日志tag
     * @param headString   日志简要信息
     * @param msg          日志内容
     */
    public abstract void printLog(int type, String tagStr, String headString, String msg);

}
