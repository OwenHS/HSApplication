package hs.http2.intercept;

import java.io.IOException;

import hs.http2.core.Interceptor;
import hs.http2.request.Request;
import hs.http2.response.Response;

/**
 * Request请求的处理
 * Respone回复的处理
 * Created by huangshuo on 17/11/21.
 */

public class EncapsulateReqAndRspInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //将要对Request进行重新创建对象
        Request originalRequest = chain.getRequest();


        return null;
    }
}
