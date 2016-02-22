package com.hs.hshttplib.titan;

import com.hs.hshttplib.HttpCallBack;

/**
 * Created by owen on 16-2-22.
 */
public class HSTestCallback extends GaiaHttpCallback<HttpCallBack> {

    @Override
    public HttpCallBack newInstance() {
        return new HttpCallBack() {
            @Override
            public void onSuccess(int code, String t) {
                HSTestCallback.this.onSuccess(code,t);
            }

        };
    }

    public void onSuccess(int code, String t){

    }
}
