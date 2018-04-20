package com.hs.libs.sample.index.basic.aidltest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hs.lib.inject.annotation.ViewInject;
import com.hs.libs.sample.R;
import com.hs.libs.sample.common.CommonTitleView;
import com.hs.libs.sample.common.HSModulesBaseActivity;

import java.util.List;

/**
 * AIDL, 那是给IPC准备的, 如果需要把自己的服务暴露给其他的app调用, 就需要用AIDL.
 * Created by huangshuo on 2018/3/7.
 */
@ViewInject(id = R.layout.layout_aidl_main)
public class AIDLTestActivity extends HSModulesBaseActivity {

    @ViewInject(id = R.id.title_view)
    CommonTitleView titleView;

    @ViewInject(id = R.id.bt, onClick = true)
    Button bt;


    //由AIDL文件生成的Java类
    private BookManager mBookManager = null;

    //标志当前与服务端连接状况的布尔值，false为未连接，true为连接中
    private boolean mBound = false;

    //包含Book对象的list
    private List<Book> mBooks;

    @Override
    public void initData() {
        super.initData();

        titleView.setTitle("AIDL测试").setOnLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!mBound) {
            attemptToBindService();
        }
    }

    /**
     * 尝试与服务端建立连接
     */
    private void attemptToBindService() {
        Intent intent = new Intent();
        //要打开指定了action=com.hs.libs.sample的service
        intent.setAction("com.hs.aidl");
        //intent 指定包名Intent.setPackage 设置广播仅对相同包名的有效
        intent.setPackage("test.com.aidlservice");
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * 由于bindservice不是立即返回。两者间需要通信，使用serviceConnection
     */
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(getLocalClassName(), "service connected");
            mBookManager = BookManager.Stub.asInterface(service);
            mBound = true;

            if (mBookManager != null) {
                try {
                    mBooks = mBookManager.getBooks();
                    Log.e(getLocalClassName(), mBooks.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(getLocalClassName(), "service disconnected");
            mBound = false;
        }
    };

    @Override
    public void onWidgetClick(View v) {
        super.onWidgetClick(v);
        switch (v.getId()) {
            case R.id.bt:
                //如果与服务端的连接处于未连接状态，则尝试连接
                if (!mBound) {
                    attemptToBindService();
                    Toast.makeText(this, "当前与服务端处于未连接状态，正在尝试重连，请稍后再试", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mBookManager == null) return;

                Book book = new Book();
                book.setName("APP研发录In");
                book.setPrice(30);
                try {
                    mBookManager.addBook(book);
                    Log.e(getLocalClassName(), book.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mServiceConnection);
            mBound = false;
        }
    }
}
