package com.hs.lib.log.task;

import com.hs.lib.log.BaseLogTask;
import com.hs.lib.log.HLog;
import com.hs.lib.log.task.terminal.CustomLog;
import com.hs.lib.log.task.terminal.JsonLog;
import com.hs.lib.log.task.terminal.XmlLog;

/**
 * 终端输出接口
 * （1）解决终端超过4000字符不打印的问题
 * Created by huangshuo on 16/8/29.
 */
public class TerminalLogTask extends BaseLogTask {

    @Override
    public void printLog(int type, String tagStr, String headString, String msg) {

        switch (type) {
            case HLog.V:
            case HLog.D:
            case HLog.I:
            case HLog.W:
            case HLog.E:
            case HLog.A:
                new CustomLog().print(type, tagStr, msg);
                break;
            case HLog.JSON:
                new JsonLog().print(tagStr, msg, headString);
                break;
            case HLog.XML:
                new XmlLog().print(tagStr, msg, headString);
                break;
            case HLog.CRASH:
                new CustomLog().print(type, tagStr, msg);
                break;

        }

    }

}
