package com.hs.libs.sample.common;

import com.hs.lib.processannotation.annotation.Module;
import com.hs.lib.processannotation.annotation.Named;
import com.hs.lib.processannotation.annotation.ObjectProvider;
import com.hs.libs.sample.HSSampleApplication;
import com.hs.tools.common.utils.ApolloPerferences;


/**
 * 类名称：ECSApp
 * 类描述：全局模块构造类
 * 创建人：OwenHuang
 * 创建时间：2017/2/9 0009 上午 9:56
 */

@Module(objectInjects = AppApi.class)
public class AppConfig {

    @ObjectProvider
    @Named(name = "SettingSp")
    public ApolloPerferences getSettingSharePreference(){
        return ApolloPerferences.create(Constants.SP.SettingSp, HSSampleApplication.current);
    }
}
