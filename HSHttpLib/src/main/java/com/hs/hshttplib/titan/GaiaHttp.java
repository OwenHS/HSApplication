package com.hs.hshttplib.titan;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by owen on 16-2-18.
 */
public abstract class GaiaHttp<J> {
    public GaiaHttp() {
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
    public abstract J translateTo(Map<String, String> paramsMap);

    public void invoke(Object[] args, MethodHandler methodHandler) {
        RequestMethodInfo info = methodHandler.methodInfo;
        HashMap<String, String> paramsMap = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            if (info.params_values != null && i != info.callBackIndex && info.params_values.get(i) != null) {
                paramsMap.put(info.params_values.get(i), String.valueOf(args[i]));
            }
        }
        try {
            if (info.requestMethod == RequestMethodInfo.GaiaMethod.Get) {
                get(info.url, translateTo(paramsMap), (GaiaCommonCallback) args[info.callBackIndex]);
            } else if (info.requestMethod == RequestMethodInfo.GaiaMethod.Post) {
                post(info.url, translateTo(paramsMap), (GaiaCommonCallback) args[info.callBackIndex]);
            } else if (info.requestMethod == RequestMethodInfo.GaiaMethod.Put) {
                put(info.url, translateTo(paramsMap), (GaiaCommonCallback) args[info.callBackIndex]);
            } else if (info.requestMethod == RequestMethodInfo.GaiaMethod.PostJson) {
                postJson(info.url, (JSONObject) args[info.jsonIndex], (GaiaCommonCallback) args[info.callBackIndex]);
            } else if (info.requestMethod == RequestMethodInfo.GaiaMethod.DownFile) {
                downFile((String) args[info.fileUrlIndex], (File) args[info.fileIndex], (GaiaCommonCallback) args[info.callBackIndex]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void get(String url, J j, GaiaCommonCallback k) {

    }

    public void post(String url, J j, GaiaCommonCallback k) {

    }

    public void put(String url, J j, GaiaCommonCallback k) {

    }

    public void postJson(String url, JSONObject jsonObject, GaiaCommonCallback k) {

    }

    public void downFile(String url, File save, GaiaCommonCallback k) {

    }
}
