package com.hs.libs.sample.index.home;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hs.lib.inject.annotation.ViewInject;
import com.hs.libs.sample.R;
import com.hs.libs.sample.common.HSModulesBaseActivity;
import com.hs.libs.sample.index.home.instance.AndroidBasicItem;
import com.hs.libs.sample.index.home.instance.AndroidWidgetItem;
import com.hs.libs.sample.index.home.instance.DesignPatternItem;
import com.hs.libs.sample.index.home.instance.HttpItem;
import com.hs.libs.sample.index.home.instance.KnowLedgeItem;
import com.hs.libs.sample.index.home.instance.TestOpenAnotherApp;
import com.hs.tools.common.autoview.adapter_model.HSViewHolder;
import com.hs.tools.common.autoview.adapter_model.HeraclesAdapter;

import java.util.ArrayList;

@ViewInject(id = R.layout.activity_main)
public class MainActivity extends HSModulesBaseActivity {

    /**
     * 1.请求{http请求，中间件请求}
     * 2.自定义view
     * 3.测试设计模式
     * 4.android基础内容测试
     */

    /**
     * 放置各大测试组件的控件
     */
    @ViewInject(id = R.id.activity_main_list)
    ListView activity_main_list;

    HeraclesAdapter<AbstractTestItem> adapter;

    ArrayList<AbstractTestItem> items;

    @Override
    public void initData() {
        super.initData();
        items = new ArrayList<>();
        items.add(TestItemFactory.createTestItem(DesignPatternItem.class));
        items.add(TestItemFactory.createTestItem(HttpItem.class));
        items.add(TestItemFactory.createTestItem(KnowLedgeItem.class));
        items.add(TestItemFactory.createTestItem(TestOpenAnotherApp.class));
        items.add(TestItemFactory.createTestItem(AndroidBasicItem.class));
        items.add(TestItemFactory.createTestItem(AndroidWidgetItem.class));

        adapter = new HeraclesAdapter<AbstractTestItem>(this, R.layout.item_main, items) {
            @Override
            public void handlerUI(HSViewHolder holder, AbstractTestItem item) {
                super.handlerUI(holder, item);
                holder.setText(R.id.tv_item_main, item.getTitle());
            }
        };
        activity_main_list.setAdapter(adapter);

        activity_main_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.getItem(i).startItem(MainActivity.this);
            }
        });
    }
}
