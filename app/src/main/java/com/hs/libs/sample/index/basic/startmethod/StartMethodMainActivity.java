package com.hs.libs.sample.index.basic.startmethod;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.hs.libs.sample.R;
import com.hs.libs.sample.common.CommonTitleView;
import com.hs.libs.sample.common.HSModulesBaseActivity;
import com.hs.tools.common.autoview.adapter_model.HSViewHolder;
import com.hs.tools.common.autoview.adapter_model.HeraclesAdapter;

import java.util.ArrayList;

/**
 * Created by huangshuo on 2018/3/6.
 */
public class StartMethodMainActivity extends HSModulesBaseActivity {

    CommonTitleView app_common_title;

    Button bt_standard;

    Button bt_singletop;

    Button bt_singletask;

    Button bt_singleinstance;

    ListView lv_activity_names;

    public static ArrayList<String> activityNames = new ArrayList<>();

    HeraclesAdapter<String> adapter;

    @Override
    public boolean setRootView() {
        setContentView(R.layout.layout_start_method_main);
        return true;
    }

    @Override
    public void initData() {
        super.initData();

        app_common_title = (CommonTitleView) findViewById(R.id.app_common_title);
        bt_standard = (Button) findViewById(R.id.bt_standard);
        bt_singletop = (Button) findViewById(R.id.bt_singletop);
        bt_singletask = (Button) findViewById(R.id.bt_singletask);
        bt_singleinstance = (Button) findViewById(R.id.bt_singleinstance);
        bt_standard.setOnClickListener(this);
        bt_singletop.setOnClickListener(this);
        bt_singletask.setOnClickListener(this);
        bt_singleinstance.setOnClickListener(this);
        lv_activity_names = (ListView) findViewById(R.id.lv_activity_names);

        app_common_title.setTitle("4种启动方式测试").setOnLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        activityNames.add(toString());

        adapter = new HeraclesAdapter<String>(this, R.layout.item_start_list, activityNames) {
            @Override
            public void handlerUI(HSViewHolder holder, String item) {
                super.handlerUI(holder, item);
                holder.setText(R.id.item_tv, item);
            }
        };
        lv_activity_names.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        },500);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("owen", "onNewIntent");
    }

    @Override
    public void onWidgetClick(View v) {
        super.onWidgetClick(v);
        Intent intent;
        switch (v.getId()) {
            case R.id.bt_standard:
                intent = new Intent(this, StandardActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_singletop:
                intent = new Intent(this, SingleTopActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_singletask:
                intent = new Intent(this, SingleTaskActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_singleinstance:
                intent = new Intent(this, SingleInstanceActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        synchronized (activityNames) {
            int count  = -1;
            for (int i = 0; i < activityNames.size(); i++) {
                if(activityNames.get(i).equals(toString())){
                    Log.d("owen","remove "+toString());
                    count = i;
                    break;
                }
            }
            if(count > -1){
                activityNames.remove(count);
            }
        }



    }
}
