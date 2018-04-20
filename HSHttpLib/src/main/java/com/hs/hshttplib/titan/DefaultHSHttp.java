package com.hs.hshttplib.titan;

import com.hs.hshttplib.HSHttp;
import com.hs.hshttplib.HttpCallBack;
import com.hs.hshttplib.HttpParams;
import com.hs.lib.log.HLog;

import java.util.Map;

/**
 * Created by owen on 16-2-19.
 */
public class DefaultHSHttp extends GaiaHttp<HttpParams> {

    public HSHttp httpClient;

    public DefaultHSHttp() {
        httpClient = new HSHttp();
    }

    @Override
    public HttpParams translateTo(Map<String, Object> paramsMap, RequestMethodInfo.GaiaMethod gigamethod) {
        HttpParams params = new HttpParams();
        for (Map.Entry<String, Object> entry : paramsMap.entrySet()) {
            params.put(entry.getKey(), (String)entry.getValue());
        }
        return params;
    }

    @Override
    public <T> void get(String url, HttpParams httpParams,final GaiaCommonCallback k, Class<?> t) {
        httpClient.get(url,httpParams,new HttpCallBack(){
            @Override
            public void onSuccess(String t) {
                HLog.xml(t);
                k.onSuccess(t);
            }
        });

    }


    //    @Override
//    public void get(String url, HttpParams httpParams, GaiaCommonCallback httpCallBack) {
//        callBack = httpCallBack;
//        httpClient.get(url, httpParams, new HttpCallBack() {
//            @Override
//            public void onSuccess(int code, String t) {
//                callBack.onSuccess(code,t);
//            }
//        });
//    }





}
