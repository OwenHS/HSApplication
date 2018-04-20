package hs.http2.request;

import hs.http2.response.Callback;

/**
 * 请求行为接口
 * Created by huangshuo on 17/11/20.
 */

public interface Call {

    /**
     * 获取原始请求数据Request
     * @return
     */
    Request request();

    /**
     * 执行并发操作
     * @param responseCallback
     */
    void execute(Callback responseCallback);

    /**
     * 取消并发操作
     */
    void cancel();

    /**
     * 并发操作是否开始
     * @return
     */
    boolean isExecuted();

    /**
     * 并发操作是否结束
     * @return
     */
    boolean isCanceled();

    /**
     * 抽象工厂创建请求行为对象
     */
    interface Factory {
        Call newCall(Request request);
    }
}
