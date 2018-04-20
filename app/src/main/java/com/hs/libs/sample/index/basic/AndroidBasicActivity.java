package com.hs.libs.sample.index.basic;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hs.lib.inject.annotation.ViewInject;
import com.hs.libs.sample.R;
import com.hs.libs.sample.common.CommonTitleView;
import com.hs.libs.sample.common.HSModulesBaseActivity;
import com.hs.tools.common.autoview.adapter_model.HSViewHolder;
import com.hs.tools.common.autoview.adapter_model.HeraclesAdapter;

import java.util.ArrayList;

/**
 * Created by huangshuo on 2018/3/6.
 */
@ViewInject(id = R.layout.layout_android_basic_main)
public class AndroidBasicActivity extends HSModulesBaseActivity {

    @ViewInject(id = R.id.app_common_title)
    CommonTitleView app_common_title;

    @ViewInject(id = R.id.activity_basic_list)
    ListView activity_basic_list;
    /**
     * 需要展示的设计模式结合
     */
    ArrayList<AbstractBasicAndroid> patterns;

    HeraclesAdapter<AbstractBasicAndroid> adapter;

    @Override
    public void initData() {
        super.initData();
        app_common_title.setTitle("Android基础知识").setOnLeftClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        patterns = new ArrayList<>();

        try {
            String[] items = getResources().getStringArray(R.array.android_basic_names);
            for (String item : items) {
                patterns.add((AbstractBasicAndroid) Class.forName(item).newInstance());
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        adapter = new HeraclesAdapter<AbstractBasicAndroid>(this, R.layout.item_main, patterns) {
            @Override
            public void handlerUI(HSViewHolder holder, AbstractBasicAndroid item) {
                super.handlerUI(holder, item);
                holder.setText(R.id.tv_item_main, item.getTitle());
            }
        };

        activity_basic_list.setAdapter(adapter);
        activity_basic_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                patterns.get(i).startTest(AndroidBasicActivity.this);
            }
        });
    }


}
