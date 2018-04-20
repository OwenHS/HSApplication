package com.hs.libs.sample;

import com.hs.lib.log.HLog;
import com.hs.lib.log.task.SDCardLogTask;
import com.hs.lib.log.task.TerminalLogTask;
import com.hs.lib.processannotation.ObjectGraph;
import com.hs.libs.sample.common.AppApi;
import com.hs.tools.common.utils.DeviceUtils;
import com.hs.tools.common.utils.ResourceUtils;

import lib.hs.com.hsbaselib.HSApplication;

/**
 * 文 件 名：HSSampleApplication
 * 描    述：
 * 创 建 人: OWEN_HUANG
 * 创建日期: 2017/8/3 11:01
 */

public class HSSampleApplication extends HSApplication {

    /**
     * 全局项目对象
     */
    public static HSSampleApplication current;
    /**
     * 全局使用模块对象
     */
    public AppApi appApi;
    /**
     * 视图对象
     */
    public ObjectGraph graph;

    @Override
    public void onCreate() {
        super.onCreate();

        DeviceUtils.init(this);
        ResourceUtils.init(this);

        /**
         * Log系统的初始化
         */
        HLog.init().setDebug(BuildConfig.IS_DEBUG)
                .catchError(true, true, this)
                .addLogTask(new TerminalLogTask())
                .addLogTask(new SDCardLogTask("HSSampleLog", this));
    }

}
