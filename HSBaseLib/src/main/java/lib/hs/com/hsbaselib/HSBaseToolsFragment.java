package lib.hs.com.hsbaselib;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.hs.tools.common.utils.DeviceUtils;

import java.lang.ref.WeakReference;

import lib.hs.com.hsbaselib.interfaces.ContextLoading;


/**
 * Created by huangshuo on 16/9/8.
 */
public abstract  class HSBaseToolsFragment extends HSUiInitialFragment implements ContextLoading{

    protected LoadingDialog mLoadingDialog;


    /******************* Loading 展示层******************************************/
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLoadingDialog = new LoadingDialog(getActivity());
        mLoadingDialog.setCancelable(true);
        mLoadingDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void showLoading(String text) {
        mLoadingDialog.setMessage(text);
        mLoadingDialog.show();
    }

    @Override
    public void showLoading() {
        mLoadingDialog.show();
    }

    @Override
    public void dismissLoading() {
        mLoadingDialog.dismiss();
    }
    /****************** Loading 展示层 ******************************************/


    /******************* Handler使用层 *****************************************/
    public abstract class HSHandler extends Handler {

        private WeakReference<HSBaseToolsFragment> mRefFragment;

        public HSHandler(HSBaseToolsFragment fragment) {
            mRefFragment = new WeakReference<HSBaseToolsFragment>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mRefFragment != null && mRefFragment.get() != null) {
                dealMessage(msg);
            }
        }

        public void dealMessage(Message msg){

        }
    }
    /******************* Handler使用层 *****************************************/
    /****************** TOAST 展示层 *******************************************/

    public void showToast(int string_id) {
        showToast(string_id, Toast.LENGTH_SHORT);
    }

    public void showToast(int string_id, int duration) {
        showToast(getString(string_id), duration);
    }

    public void showToast(String content, int duration) {
        if (content.length() > 0) {
            showToast(content, duration, null);
        }
    }

    public void showToast(String content) {
        if (content.length() > 0) {
            showToast(content, Toast.LENGTH_SHORT, null);
        }
    }

    public synchronized void showToast(String content, int duration, View mToastView) {
        if (mToastView == null) {
            Toast.makeText(this.getActivity(), content, duration).show();
        } else {
            Toast localToast = new Toast(this.getActivity());
            localToast.setGravity(Gravity.CENTER, 0, DeviceUtils.getScreenHeight() / 6);
            localToast.setView(mToastView);
            localToast.setDuration(duration);
            localToast.show();
        }
    }
    /****************** TOAST 展示层 ******************************************/

}
