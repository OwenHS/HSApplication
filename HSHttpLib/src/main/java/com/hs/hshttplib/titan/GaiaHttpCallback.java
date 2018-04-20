package com.hs.hshttplib.titan;

import android.content.Context;

import java.io.File;
import java.net.HttpURLConnection;

/**
 * 文 件 名：GaiaBaseCallback
 * 描    述：
 * 创 建 人: OWEN_HUANG
 * 创建日期: 2017/7/6 17:40
 */

public class GaiaHttpCallback<T> extends GaiaCommonCallback<T> {

    public GaiaHttpCallback(Context context) {
        super(context);
    }

    /**
     * Http请求开始前回调
     */
    public void onPreStart() {
    }

    /**
     * 进度回调，仅支持Download时使用
     *
     * @param count   总数
     * @param current 当前进度
     */
    public void onLoading(long count, long current) {
    }

    /**
     * Http请求成功时回调
     *
     * @param t
     */
    public void onSuccess(String t) {
    }

    @Override
    public void onSuccess(T t) {
        super.onSuccess(t);
    }

    /**
     * Http请求成功时回调
     * 但是服务器返回失败
     */
    public boolean onServerError(T t, String errorMsg, int errorCode) {

        return false;
    }

    /**
     * Http请求成功时回调
     * 但是服务器返回失败
     *
     * @Return false:继续执行  true:停止执行
     */
    public boolean onServerError(String errorMsg, int errorCode) {
        return false;
    }



    /**
     * Http请求成功时回调
     *
     * @param code 请求码
     * @param t    Http请求返回信息
     */
    public void onSuccess(int code, String t) {
    }

    /**
     * Http下载成功时回调
     */
    public void onSuccess(File f) {
    }

    /**
     * Http请求结束后回调
     */
    public void onFinish() {
    }

    protected void onEvent(T t) {

    }

    /**
     * Http请求成功时回调
     *
     * @param code 请求码
     * @param t    Http请求返回信息
     */
    public void onCache(String code, T t) {
    }

    public void onHttpConnection(HttpURLConnection conn) {
    }
}
