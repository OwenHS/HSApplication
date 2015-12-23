package lib.hs.com.hsbaselib.interfaces;

import android.view.View;

/**
 * 控制一般Activity的流程接口
 * Created by owen on 15-12-8.
 */
public interface UIProcessable {

    /**
     *  用于给Activity设置界面
     */
    public void setRootView();

    /**
     * 在线程中获取部分信息
     */
    public void getDataFromThread();

    /**
     *  在主线程中初始化信息
     */
    public void initData();

    /**
     * 初始化控件，设置初始值时使用
     */
    public void initWidget();

    /**
     *
     */
    public void onWidgetClick(View v);

}
