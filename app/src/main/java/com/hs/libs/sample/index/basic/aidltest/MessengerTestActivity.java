package com.hs.libs.sample.index.basic.aidltest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hs.lib.inject.annotation.ViewInject;
import com.hs.libs.sample.R;
import com.hs.libs.sample.common.CommonTitleView;
import com.hs.libs.sample.common.HSModulesBaseActivity;

import java.util.List;

/**
 * Created by huangshuo on 2018/3/8.
 */

@ViewInject(id = R.layout.layout_aidl_main)
public class MessengerTestActivity extends HSModulesBaseActivity {

    @ViewInject(id = R.id.title_view)
    CommonTitleView titleView;

    @ViewInject(id = R.id.bt, onClick = true)
    Button bt;


    private Messenger mMessenger = null;
    private boolean mBound = false;

    //包含Book对象的list
    private List<Book> mBooks;

    @Override
    public void initData() {
        super.initData();

        titleView.setTitle("Manager测试").setOnLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent();
        intent.setAction("com.hs.messenger");
        //intent 指定包名Intent.setPackage 设置广播仅对相同包名的有效
        intent.setPackage("test.com.aidlservice");
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(getLocalClassName(), "service connected");
            mMessenger = new Messenger(service);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(getLocalClassName(), "service disconnected");
            mBound = false;
        }
    };


    @Override
    public void onWidgetClick(View v) {
        super.onWidgetClick(v);
        switch (v.getId()) {
            case R.id.bt:
                //如果与服务端的连接处于未连接状态，则尝试连接
                sayHello();
                break;
        }
    }

    private static final int SAY_HOLLE = 0;
    public void sayHello(){
        if (!mBound) return;
        //发送一条信息给服务端
        Message msg = Message.obtain(null, SAY_HOLLE, 0, 0);
        try {
            mMessenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
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