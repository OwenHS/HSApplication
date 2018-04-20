package hs.http2.core;

import java.io.IOException;

import hs.http2.request.Request;
import hs.http2.response.Response;

/**
 * Created by huangshuo on 17/11/20.
 */

public interface Interceptor {

    Response intercept(Chain chain) throws IOException;

    /**
     * 接口Chain才是真正的拦截发生者的主要父类接口
     */
    interface Chain {
        /**
         * 获取request
         */
        Request getRequest();

        /**
         * 执行拦截器
         */
        Response proceed(Request request) throws IOException;
    }
}
