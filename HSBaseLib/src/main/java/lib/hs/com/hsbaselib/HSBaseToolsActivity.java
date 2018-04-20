package lib.hs.com.hsbaselib;

import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import lib.hs.com.hsbaselib.interfaces.ContextLoading;


/**
 * 在Activity
 * Created by huangshuo on 16/8/26.
 */
public abstract class HSBaseToolsActivity extends HSUiInitialActivity implements ContextLoading{

    /**
     * 是否已经添加过loadView
     */
    private boolean isAddInnerLoadView = false;

    /**
     * 内置加载视图
     */
    private View mLoadView;

    @Override
    protected void initializar() {
        setLoadingView(getLayoutInflater().inflate(R.layout.innerloading, null));
        addInnerLoadView();
        super.initializar();
    }


    /******************* Loading 展示层******************************************/

    public void setLoadingView(View v) {

        mLoadView = v;
        mLoadView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    private synchronized void addInnerLoadView() {
        if (!isAddInnerLoadView) {

            RelativeLayout layout = new RelativeLayout(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.FILL_PARENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            layout.addView(mLoadView, params);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.FILL_PARENT,
                    LinearLayout.LayoutParams.FILL_PARENT);
            layout.setLayoutParams(layoutParams);
            this.addContentView(layout, layoutParams);
            mLoadView.setVisibility(View.GONE);
            isAddInnerLoadView = true;
        }
    }

    @Override
    public void showLoading() {
        mLoadView.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissLoading() {
        mLoadView.setVisibility(View.GONE);
    }

    @Override
    public void showLoading(String text) {
        mLoadView.setVisibility(View.VISIBLE);
    }

    public View getLoadView() {
        return mLoadView;
    }

    public boolean isLoading(){
        return mLoadView.getVisibility() == View.VISIBLE;
    }
    /****************** Loading 展示层 ******************************************/

    /******************* Handler使用层 *****************************************/
    public abstract class HSHandler extends Handler {

        private WeakReference<HSBaseToolsActivity> mRefActivity;

        public HSHandler(HSBaseToolsActivity activity) {
            mRefActivity = new WeakReference<HSBaseToolsActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mRefActivity != null && mRefActivity.get() != null) {
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
            Toast.makeText(this, content, duration).show();
        } else {
            Toast localToast = new Toast(this);
//            localToast.setGravity(Gravity.CENTER, 0, DeviceUtils.getScreenHeight() / 6);
            localToast.setView(mToastView);
            localToast.setDuration(duration);
            localToast.show();
        }
    }

    /****************** TOAST 展示层 ******************************************/
}
