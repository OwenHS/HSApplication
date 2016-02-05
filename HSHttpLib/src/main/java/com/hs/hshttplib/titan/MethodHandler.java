package com.hs.hshttplib.titan;

import android.util.Log;

/**
 * 首先，我希望我的http是插件式的，那么无论什么http都能用在我的lib中，我需要代理，静态代理。
 *
 * http（1）创建http对象，写入公共的head，
 *     （2）写入每个调用的参数。
 *     （3）进行http请求。
 *     （4）回调处理返回。
 * Created by owen on 16-2-4.
 */
public class MethodHandler {

    private static final String TAG = "Titans";

    //http代理对象
    private IHttplibable http;

    private MethodHandler() {

    }


    //用于创建一个MethodHandler
    public static MethodHandler create() {
        return new MethodHandler();
    }

    //用于执行此方法
    public void invoke(Object... args) {
        Log.d(TAG, "动态代理方法执行");
    }


}
