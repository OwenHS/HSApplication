package com.hs.hshttplib.bitmap;


import android.graphics.Bitmap;
import android.view.View;

/**
 * ********************************************************
 *
 * @文件名称：BitmapCallback.java
 * @文件作者：huangshuo
 * @创建时间：2015-6-5 上午10:35:03
 * @文件描述：bitmap生成总体回调方法
 * @修改历史：2015-6-5创建初始版本 ********************************************************
 */
public abstract class BitmapCallback {

    /**
     * 下载前 载入前回调
     */
    public void onPreLoad(final View view) {
    }

    /**
     * bitmap载入完成将回调
     */
    public void onSuccess(final View view, final Bitmap bitmap) {
    }

    /**
     * bitmap载入失败将回调
     */
    public void onFailure(final Exception e) {
    }

    /**
     * bitmap载入完成不管成功失败
     */
    public void onFinish(final View view) {
    }


}
