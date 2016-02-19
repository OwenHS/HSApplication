package com.hs.hshttplib.titan;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by owen on 16-2-18.
 */
public abstract class GaiaHttp<T, J, K> {
    public T httpClient;

    public GaiaHttp() {
    }

    public GaiaHttp(T t) {
        this.httpClient = t;
    }

    /**
     * 用于配置HttpClient,属于全局配置
     */
    public void config() {
    }

    ;

    /**
     * 接收到网络数据
     */
    public void translateFrom() {
    }

    ;

    /**
     * 数据包装放到网上
     */
    public J translateTo(Map<String, Object> paramsMap) {
        throw new UnsupportedOperationException("translateTo wrong");
    }

    ;

    public T getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(T httpClient) {
        this.httpClient = httpClient;
    }

    public void invoke(Object[] args, MethodHandler methodHandler) {
        RequestMethodInfo info = methodHandler.methodInfo;
        HashMap<String, Object> paramsMap = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            if (i != info.callBackIndex && info.params_values.get(i) != null) {
                paramsMap.put(info.params_values.get(i), args[i]);
            }
        }

        Log.d("owen", "request url = " + info.url);

        if (info.requestMethod == RequestMethodInfo.GaiaMethod.Get) {
            get(info.url, translateTo(paramsMap), (K) args[info.callBackIndex]);
        }else if(info.requestMethod == RequestMethodInfo.GaiaMethod.Post){
            post(info.url, translateTo(paramsMap), (K) args[info.callBackIndex]);
        }else if(info.requestMethod == RequestMethodInfo.GaiaMethod.Put){
            put(info.url, translateTo(paramsMap), (K) args[info.callBackIndex]);
        }else if(info.requestMethod == RequestMethodInfo.GaiaMethod.PostJson){
            postJson(info.url, translateTo(paramsMap), (K) args[info.callBackIndex]);
        }
    }

    public void get(String url, J j, K k) {

    }
    public void post(String url, J j, K k) {

    }
    public void put(String url, J j, K k) {

    }

    public void postJson(String url, J j, K k) {

    }
}
