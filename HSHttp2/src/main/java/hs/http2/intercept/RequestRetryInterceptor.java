package hs.http2.intercept;

import java.io.IOException;

import hs.http2.core.Interceptor;
import hs.http2.request.Request;
import hs.http2.response.Response;

/**
 * 请求重试拦截器
 * Created by huangshuo on 17/11/20.
 */

public class RequestRetryInterceptor implements Interceptor {

    public int maxRetry;//最大重试次数
    private int retryNum = 0;//假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.getRequest();

        //第一次请求 这里的request是初始的request，在责任链里，最好使用原型模式，或者其他创建对象型模式重新
        //创建request对象，否则，会在重试的时候request已经被其他类破坏了原始数据。
        Response response = chain.proceed(request);
        while (retryNum < maxRetry) {
            //重试请求
            retryNum++;
            response = chain.proceed(request);
        }

        return response;
    }

}
