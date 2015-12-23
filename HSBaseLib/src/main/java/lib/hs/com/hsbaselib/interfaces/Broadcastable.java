package lib.hs.com.hsbaselib.interfaces;

/**
 * 用于广播的接口
 * Created by owen on 15-12-22.
 */
public interface Broadcastable {

    //添加广播
    public void registBroadcast();

    //解除广播
    public void unregistBroadcast();
}
