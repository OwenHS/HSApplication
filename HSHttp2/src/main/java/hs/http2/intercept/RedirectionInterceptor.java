package hs.http2.intercept;

import java.io.IOException;

import hs.http2.core.Interceptor;
import hs.http2.request.Request;
import hs.http2.response.Response;

/**
 * 拦截器：主要功能提供重试和重定向功能
 * Created by huangshuo on 17/11/20.
 */

public class RedirectionInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.getRequest();

        Response response = chain.proceed(request);

        return response;
    }
}
