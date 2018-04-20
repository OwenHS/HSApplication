package hs.http2.request;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hs.http2.core.HSCachedTask;
import hs.http2.core.HSHttpClient;
import hs.http2.core.Interceptor;
import hs.http2.intercept.EncapsulateReqAndRspInterceptor;
import hs.http2.intercept.RealInterceptorChain;
import hs.http2.intercept.RedirectionInterceptor;
import hs.http2.intercept.RequestRetryInterceptor;
import hs.http2.response.Callback;
import hs.http2.response.Response;

/**
 * 作为http请求中控制请求行为的实例对象
 * 执行http请求的【流程】封装在这里
 * Created by huangshuo on 17/11/20.
 */

public class RealCall implements Call {

    /**
     * 请求客户端
     */
    private HSHttpClient hsHttpClient;
    /**
     * 原始请求数据
     */
    private final Request originalRequest;

    /**
     * 重试和处理重定向的拦截器
     */
    private RedirectionInterceptor redirectionInterceptor;

    public RealCall(HSHttpClient hsHttpClient, Request request) {
        this.hsHttpClient = hsHttpClient;
        this.originalRequest = request;
        redirectionInterceptor = new RedirectionInterceptor();
    }

    @Override
    public Request request() {
        return null;
    }

    @Override
    public void execute(Callback responseCallback) {
        new HSHttpTask("", "", 100).execute(originalRequest);
    }

    @Override
    public void cancel() {

    }

    @Override
    public boolean isExecuted() {
        return false;
    }

    @Override
    public boolean isCanceled() {
        return false;
    }

    /**
     * 创建真实的Call对象
     *
     * @param hsHttpClient
     * @param request
     */
    public static Call newRealCall(HSHttpClient hsHttpClient, Request request) {
        RealCall call = new RealCall(hsHttpClient, request);
        return call;
    }

    private class HSHttpTask extends HSCachedTask<Request, Object, Response> {

        /**
         * 构造方法
         *
         * @param cachePath 缓存路径
         * @param key       存储的key值，若重复将覆盖
         * @param cacheTime
         */
        public HSHttpTask(String cachePath, String key, long cacheTime) {
            super(cachePath, key, cacheTime);
        }

        @Override
        protected Response doConnectNetwork(Request... params) throws Exception {
            //线程开始
            Response response = getResponseWithInterceptorChain();
            return null;
        }


    }

    /**
     * 使用责任链模式，将功能封装分割。
     *
     * @return
     */
    private Response getResponseWithInterceptorChain() throws IOException {
        List<Interceptor> interceptors = new ArrayList<>();

        /**
         * retry
         */
        interceptors.add(new RequestRetryInterceptor());
        /**
         * 重定向操作类
         */
        interceptors.add(redirectionInterceptor);

        interceptors.add(new EncapsulateReqAndRspInterceptor());

        Interceptor.Chain chain = new RealInterceptorChain(interceptors, 0, originalRequest);

        return chain.proceed(originalRequest);
    }
}
