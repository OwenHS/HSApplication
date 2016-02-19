package com.hs.hshttplib.titan;

import com.hs.hshttplib.HSHttp;
import com.hs.hshttplib.HttpCallBack;
import com.hs.hshttplib.HttpParams;

import java.util.Map;

/**
 * Created by owen on 16-2-19.
 */
public class DefaultHSHttp extends GaiaHttp<HSHttp, HttpParams, HttpCallBack> {

    public HSHttp httpClient;

    public DefaultHSHttp() {
        httpClient = new HSHttp();
    }


    @Override
    public void get(String url, HttpParams httpParams, HttpCallBack httpCallBack) {
        httpClient.get(url, httpParams, httpCallBack);
    }

    @Override
    public void post(String url, HttpParams httpParams, HttpCallBack httpCallBack) {
        httpClient.get(url, httpParams, httpCallBack);
    }

    @Override
    public void put(String url, HttpParams httpParams, HttpCallBack httpCallBack) {
        httpClient.get(url, httpParams, httpCallBack);
    }

    @Override
    public void postJson(String url, HttpParams httpParams, HttpCallBack httpCallBack) {
        httpClient.get(url, httpParams, httpCallBack);
    }

    @Override
    public HttpParams translateTo(Map<String, String> paramsMap) {
        HttpParams params = new HttpParams();
        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            params.put(entry.getKey(), entry.getValue());
        }
        return params;
    }

}
