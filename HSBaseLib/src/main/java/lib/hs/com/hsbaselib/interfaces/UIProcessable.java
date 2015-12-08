package lib.hs.com.hsbaselib.interfaces;

/**
 * 控制一般Activity的流程接口
 * Created by owen on 15-12-8.
 */
public interface UIProcessable {

    /**
     *  用于给Activity设置界面
     * @param rootViewId
     */
    public void setRootView(int rootViewId);

    public void initData();

    public void initWidget();

    public void onWidgetClick();

}
