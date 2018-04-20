package com.hs.libs.sample.common;

import com.hs.lib.processannotation.annotation.Named;
import com.hs.lib.processannotation.annotation.ObjectInject;
import com.hs.libs.sample.HSSampleApplication;
import com.hs.tools.common.utils.ApolloPerferences;

/**
 * 类名称：ECSApp
 * 类描述：全局模块引用类
 * 创建人：OwenHuang
 * 创建时间：2017/2/9 0009 上午 9:56
 */

public class AppApi {

    /**
     * 设置相关的sp
     */
    @ObjectInject
    @Named(name = "SettingSp")
    public ApolloPerferences settingPerference;

    /**
     * 获取SharedPreferences对象
     * @return
     */
    public static ApolloPerferences getSettingSp() {
        return HSSampleApplication.current.appApi.settingPerference;
    }



}
