package hs.http2.intercept;

import java.io.IOException;
import java.util.List;

import hs.http2.core.Interceptor;
import hs.http2.request.Request;
import hs.http2.response.Response;

/**
 * 根据拦截器列表，逐步执行拦截器
 * Created by huangshuo on 17/11/20.
 */

public class RealInterceptorChain implements Interceptor.Chain {

    /**
     * 用于计数执行哪个拦截器
     */
    private int index;
    /**
     * 拦截器列表
     */
    private List<Interceptor> interceptors;

    private Request request;

    public RealInterceptorChain(List<Interceptor> interceptors, int index, Request originalRequest) {
        this.index = index;
        this.interceptors = interceptors;
        this.request = originalRequest;
    }

    @Override
    public Response proceed(Request request) throws IOException {

        Response response;

        Interceptor.Chain next = new RealInterceptorChain(interceptors, index + 1, request);
        response = interceptors.get(index).intercept(next);

        return response;
    }

    public Request getRequest() {
        return request;
    }
}
