package com.hs.hshttplib.titan;

import com.hs.hshttplib.HSHttp;
import com.hs.hshttplib.HttpParams;

import org.json.JSONObject;

import java.io.File;
import java.util.Map;

/**
 * Created by owen on 16-2-19.
 */
public class DefaultHSHttp extends GaiaHttp<HSHttp, HttpParams, HSTestCallback> {

    public HSHttp httpClient;

    public DefaultHSHttp() {
        httpClient = new HSHttp();
    }


    @Override
    public void get(String url, HttpParams httpParams, HSTestCallback httpCallBack) {
        httpClient.get(url, httpParams, httpCallBack.callback);
    }

    @Override
    public void post(String url, HttpParams httpParams, HSTestCallback httpCallBack) {
        httpClient.get(url, httpParams, httpCallBack.callback);
    }

    @Override
    public void put(String url, HttpParams httpParams, HSTestCallback httpCallBack) {
        httpClient.get(url, httpParams, httpCallBack.callback);
    }

    @Override
    public void postJson(String url, JSONObject jsonObject, HSTestCallback httpCallBack) {
        httpClient.post(url, jsonObject, httpCallBack.callback);
    }

    @Override
    public void downFile(String url,File save,HSTestCallback httpCallBack) {
        httpClient.download(url,save,httpCallBack.callback);
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
