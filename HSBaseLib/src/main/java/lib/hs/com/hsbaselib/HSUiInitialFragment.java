package lib.hs.com.hsbaselib;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hs.lib.inject.HSInjectUtil;
import com.hs.lib.inject.InjectType;

/**********************************************************
 * @文件名称：TemplateFragment.java
 * @文件作者：huangshuo
 * @创建时间：2015-4-3 上午10:06:53
 * @文件描述：
 * @修改历史：2015-4-3创建初始版本
 **********************************************************/
public abstract class HSUiInitialFragment extends Fragment implements View.OnClickListener {
    public static final int INIT_IN_THREAD = 0x37211;

    private static ThreadCallBack callback;

    protected View rootView;

    private interface ThreadCallBack {
        void onSuccess();
    }

    private static Handler threadHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == INIT_IN_THREAD) {
                callback.onSuccess();
            }
        }

        ;
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {

            rootView = inflaterView(inflater,container,savedInstanceState);

            if(rootView == null){
                rootView = (View) HSInjectUtil.inject(InjectType.VIEW,this,new View(getActivity()),false);
            }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    initDataFromThread();
                    threadHandler.sendEmptyMessage(INIT_IN_THREAD);
                }
            }).start();
            initWidget(rootView);
            initData();
        } else {
            //缓存的rootView需要判断是否已经被加过parent，
            //如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }

        return rootView;
    }

    protected abstract View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    private void initDataFromThread() {
        callback = new ThreadCallBack() {
            @Override
            public void onSuccess() {
                threadDataInited();
            }
        };
    }

    /**
     * 如果调用了initDataFromThread()，则当数据初始化完成后将回调该方法。
     */
    protected void threadDataInited() {
    }

    /**
     * initialization widget, you should look like parentView.findviewbyid(id);
     * call method
     *
     * @param parentView
     */
    protected void initWidget(View parentView) {
    }

    /**
     * initialization data
     */
    protected void initData() {
    }

    @Override
    public void onClick(View v) {
        initWidget(v);
    }

}
