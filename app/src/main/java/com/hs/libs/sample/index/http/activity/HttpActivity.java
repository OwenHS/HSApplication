package com.hs.libs.sample.index.http.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.hs.hshttplib.HSHttp;
import com.hs.hshttplib.HttpCallBack;
import com.hs.hshttplib.core.HSAsyncTask;
import com.hs.lib.inject.annotation.ViewInject;
import com.hs.libs.sample.R;
import com.hs.tools.common.autoview.adapter_model.HSViewHolder;
import com.hs.tools.common.autoview.adapter_model.HeraclesAdapter;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.PriorityBlockingQueue;

import lib.hs.com.hsbaselib.HSBaseActivity;

/**
 * Created by huangshuo on 17/11/7.
 */
@ViewInject(id = R.layout.activity_http)
public class HttpActivity extends HSBaseActivity {

    /**
     * 请求队列数据源
     */
    private PriorityBlockingQueue<String> data = new PriorityBlockingQueue<String>();

    private int num = 0;

    @ViewInject(id = R.id.bt1, onClick = true)
    Button bt1;
    @ViewInject(id = R.id.iv1)
    ImageView iv1;

    @ViewInject(id = R.id.lv1)
    ListView lv1;

    ArrayList<String> datas = new ArrayList<>();

    HeraclesAdapter<String> adapter;

    @Override
    public void onWidgetClick(View v) {
        super.onWidgetClick(v);
        switch (v.getId()) {
            case R.id.bt1:
//                datas.add(String.valueOf(num++));
//                iv1.setImageResource(R.drawable.p1);
                RequestOptions imgoptions = new RequestOptions();
                imgoptions.diskCacheStrategy(DiskCacheStrategy.ALL);
                imgoptions.centerCrop();
                imgoptions.placeholder(R.drawable.ic_launcher);
                imgoptions.error(R.drawable.ic_launcher);
                Glide.with(this)
                        .asBitmap()
                        .apply(imgoptions)
                        .load("http://192.168.3.28:8280/ECSFileServer/upload/image/180103/105611211.png")
                        .transition(new BitmapTransitionOptions().crossFade(500))
                        .into(iv1);

                break;
        }
    }

    @Override
    public void initData() {
        super.initData();

        String str1 = "owenhuang";
        String str2 = "\ntest";
        Log.e("owen", "str1.length = " + str1.length());
        Log.e("owen", "str2.length = " + str2.length());

        String str3 = str1 + str2;
        Log.e("owen", "str3.length = " + str3.length());

        datas.add("http://192.168.3.28:8280/ECSFileServer/upload/image/180103/105456208.jpg");
        datas.add("http://192.168.3.28:8280/ECSFileServer/upload/image/180103/105523209.jpg");
        datas.add("http://192.168.3.28:8280/ECSFileServer/upload/image/180103/105523209.jpg");
        datas.add("http://192.168.3.28:8280/ECSFileServer/upload/image/180103/105523209.jpg");
        datas.add("http://192.168.3.28:8280/ECSFileServer/upload/image/180103/105523209.jpg");
        datas.add("http://192.168.3.28:8280/ECSFileServer/upload/image/180103/105611211.png");

        adapter = new HeraclesAdapter<String>(this,R.layout.item_list,datas){
            @Override
            public void handlerUI(HSViewHolder holder, String item) {
                super.handlerUI(holder, item);
                RequestOptions imgoptions = new RequestOptions();
                imgoptions.diskCacheStrategy(DiskCacheStrategy.ALL);
                imgoptions.centerCrop();
//                imgoptions.placeholder(R.drawable.ic_launcher);
                imgoptions.error(R.drawable.ic_launcher);
                Glide.with(HttpActivity.this)
                        .asBitmap()
                        .apply(imgoptions)
                        .load(item)
                        .transition(new BitmapTransitionOptions().crossFade(500))
                        .into((ImageView) holder.getHolderView(R.id.item_iv));
            }
        };
        lv1.setAdapter(adapter);

//        testException();
//        testHSAsync();
//        testBlockingQueue();
    }

    private void testException() {

        HSHttp http = new HSHttp();
        http.get("http://www.baidu.com", new HttpCallBack() {

            @Override
            public void onPreStart() {
                super.onPreStart();
                String a = null;
                a.toString();
            }

            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                showToast(strMsg.toString());
            }
        });
    }

    static int count = 0;

    private void testHSAsync() {

        Executor executor1 = new HSAsyncTask.SerialExecutor();
        Executor executor2 = new HSAsyncTask.SerialExecutor();

        for (int i = 1; i < 8; i++) {
            if (i % 2 == 0) {
                count = 1;
                HSAsyncTask.setDefaultExecutor(executor1);
            } else {
                count = 2;
                HSAsyncTask.setDefaultExecutor(executor2);
            }
            HSAsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    Log.d("owen", count + " start");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.d("owen", count + " end");
                }
            });
        }

    }

    /**
     * 测试阻塞队列属性
     */
    private void testBlockingQueue() {
        TestThread thread1 = new TestThread(data);
        TestThread thread2 = new TestThread(data);
        thread1.start();
        thread2.start();
    }


    static class TestThread extends Thread {

        private BlockingQueue<String> datas;

        public TestThread(BlockingQueue<String> datas) {
            this.datas = datas;
        }

        @Override
        public void run() {
            super.run();
            String data;
            Log.e("owen", this.getName() + " start ");
            Random rand = new Random(5);
            try {
                while (true) {
                    data = datas.take();
                    int time = rand.nextInt(10);
                    Log.e("owen", this.getName() + " sleepTime =  " + time);
                    sleep(time * 1000);
                    Log.e("owen", this.getName() + " info =  " + data);
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
