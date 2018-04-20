package com.hs.hshttplib.titan;

import android.content.Context;

/**
 * Created by owen on 16-2-22.
 */
public abstract class GaiaCommonCallback<T> {


    protected boolean isCancel = false;

    public Context context;

    public int respondCode = -1;

    private CallbackFinishCallback callback;

    public void setOnCallbackFinish(CallbackFinishCallback callback){
        this.callback = callback;
    }

    public interface CallbackFinishCallback{
        void onCallbackFinish();
    }


    public GaiaCommonCallback(Context context) {
        this.context = context;
    }


    /**
     * 检查是否调用http
     */
    public boolean checkToInvoke() {
        return true;
    }

    /**
     * 成功回调
     * @param t
     */
    public void onSuccess(T t) {

    }

    /**
     * 失败回调
     */
    /**
     * Http请求失败时回调
     *
     * @param t
     * @param errorNo 错误码
     * @param strMsg  错误原因
     */
    public void onFailure(Throwable t, int errorNo, String strMsg) {
    }

    public void setCancel(boolean cancel) {
        this.isCancel = cancel;
        if(cancel && callback != null){
            callback.onCallbackFinish();
        }
    }

    public boolean isCancel() {
        return isCancel;
    }

}
