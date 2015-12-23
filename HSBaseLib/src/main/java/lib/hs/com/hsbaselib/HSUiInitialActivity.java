package lib.hs.com.hsbaselib;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hs.lib.inject.HSInjectUtil;

import lib.hs.com.hsbaselib.interfaces.Broadcastable;
import lib.hs.com.hsbaselib.interfaces.SkipActivityable;
import lib.hs.com.hsbaselib.interfaces.UIProcessable;

/**
 * 用于整合activity的流程
 */
public abstract class HSUiInitialActivity extends AppCompatActivity implements UIProcessable,Broadcastable, SkipActivityable,View.OnClickListener{

    private static final String TAG = "HSUiInitialActivity";
    private static int WHICH_MSG = 0;
    private static ThreadDataCallBack callback;

    /**
     * 一个私有回调类，线程中初始化数据完成后的回调
     */
    private interface ThreadDataCallBack
    {
        void onSuccess();
    }

    // 当线程中初始化的数据初始化完成后，调用回调方法
    private static Handler threadHandle = new Handler()
    {
        @Override
        public void handleMessage(android.os.Message msg)
        {
            if (msg.what == WHICH_MSG)
            {
                callback.onSuccess();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //使用了编译时注解
        HSInjectUtil.inject(this);
        //如果用了编译时注解的注解类，setRootView可以不用复写
        setRootView();

        initializar();

        registBroadcast();
    }

    /*用于初始化信息 包括子线程和主线程两部分*/
    private void initializar() {
        //子线程中初始化信息
        new Thread(){
            @Override
            public void run() {
                getDataFromThread();
                threadHandle.sendEmptyMessage(WHICH_MSG);
            }
        }.start();

        //主线程中初始化信息
        initData();
        //初始化控件
        initWidget();
    }

    @Override
    public void setRootView() {

    }


    @Override
    public void getDataFromThread() {
        callback = new ThreadDataCallBack()
        {
            @Override
            public void onSuccess()
            {
                initThreadData();
            }
        };
    }

    /*在线程中获取信息后的回调，主线程*/
    protected void initThreadData() {
    }

    @Override
    public void initData() {

    }

    @Override
    public void initWidget() {

    }

    @Override
    public void onWidgetClick(View v) {

    }

    @Override
    public void onClick(View v) {
        onWidgetClick(v);
    }

    @Override
    public void registBroadcast() {

    }

    @Override
    public void unregistBroadcast() {

    }

    @Override
    protected void onDestroy() {
        unregistBroadcast();
        super.onDestroy();
    }
}
