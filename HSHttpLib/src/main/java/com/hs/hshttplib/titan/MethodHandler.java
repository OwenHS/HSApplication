package com.hs.hshttplib.titan;

import java.lang.reflect.Method;

/**
 * 首先，我希望我的http是插件式的，那么无论什么http都能用在我的lib中，我需要代理，静态代理。
 * <p/>
 * http（1）创建http对象，写入公共的head，
 * （2）写入每个调用的参数。
 * （3）进行http请求。
 * （4）回调处理返回。
 * Created by owen on 16-2-4.
 */
public class MethodHandler {

    private static final String TAG = "Titans";

    //http代理对象
    private IHttplibable http;

    //请求接口的信息
    public RequestMethodInfo methodInfo;

    protected Titans titans;

    protected MethodHandler(RequestMethodInfo methodInfo, Titans titans) {
        this.methodInfo = methodInfo;
        this.titans = titans;
    }

    /**
     * 用于创建一个MethodHandler
     *
     * @param method 需要执行
     * @param titans
     * @return
     */
    public static MethodHandler create(Method method, Titans titans) {
        RequestMethodInfo methodInfo = RequestMethodInfoFactory.parse(method);
        if (methodInfo == null) {
            return null;
        }
        return new MethodHandler(methodInfo, titans);
    }

    //用于执行此方法
    public void invoke(Object... args) {

        methodInfo.allUrl = titans.baseUrl + methodInfo.url;

        if (methodInfo.callBackIndex == -1) {
            //有可能是忘写callback，有可能是不需要callback，只是调用接口
            titans.httpClient.invokeWithoutCallback(args, this);
        } else if (((GaiaCommonCallback) args[methodInfo.callBackIndex]).checkToInvoke()) {
            titans.httpClient.invoke(args, this);
        }

    }
}
