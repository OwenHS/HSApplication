package lib.hs.com.hsbaselib.interfaces;

import android.content.Intent;
import android.view.View;

/**
 * 控制一般Activity的流程接口
 * Created by owen on 15-12-8.
 */
public interface UIProcessable {

    /**
     * 设置Activity风格
     */
    void setActivityStyle();

    /**
     * 用于给Activity设置界面
     */
    boolean setRootView();

    /**
     * 在线程中获取部分信息
     */
    void getDataFromThread();

    /**
     * 在主线程中初始化信息
     */
    void initData();

    /**
     * 初始化控件，设置初始值时使用
     */
    void initWidget();


    /**
     * 添加监听事件
     */
    void initListener();

    /**
     *
     */
    void onWidgetClick(View v);

    void getIntentData(Intent intent);

//    /**
//     * 设置Loading布局
//     */
//    void setLoadingView(View v);
//
//    void addInnerLoadView();

}
