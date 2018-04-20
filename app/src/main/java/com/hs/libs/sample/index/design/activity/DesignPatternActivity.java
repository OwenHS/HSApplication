package com.hs.libs.sample.index.design.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hs.lib.inject.annotation.ViewInject;
import com.hs.libs.sample.R;
import com.hs.libs.sample.common.HSModulesBaseActivity;
import com.hs.libs.sample.index.design.module.AbstractPattern;
import com.hs.tools.common.autoview.adapter_model.HSViewHolder;
import com.hs.tools.common.autoview.adapter_model.HeraclesAdapter;

import java.util.ArrayList;


/**
 * 文 件 名：DesignPatternActivity
 * 描    述：设计模式main界面
 * 创 建 人: OWEN_HUANG
 * 创建日 2017/8/7 15:48
 */
@ViewInject(id = R.layout.activity_design_main)
public class DesignPatternActivity extends HSModulesBaseActivity {

    /**
     * 需要展示的设计模式结合
     */
    ArrayList<AbstractPattern> patterns;

    HeraclesAdapter<AbstractPattern> adapter;

    @ViewInject(id = R.id.activity_pattern_list)
    ListView activity_pattern_list;

    @Override
    public void initData() {
        super.initData();

        patterns = new ArrayList<>();

        try {
            String[] items = getResources().getStringArray(R.array.patterns_names);
            for (String item : items) {
                patterns.add((AbstractPattern) Class.forName(item).newInstance());
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        adapter = new HeraclesAdapter<AbstractPattern>(this, R.layout.item_main, patterns) {
            @Override
            public void handlerUI(HSViewHolder holder, AbstractPattern item) {
                super.handlerUI(holder, item);
                holder.setText(R.id.tv_item_main, item.getTitle());
            }
        };

        activity_pattern_list.setAdapter(adapter);
        activity_pattern_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                patterns.get(i).startPattern(DesignPatternActivity.this);
            }
        });
    }
}
