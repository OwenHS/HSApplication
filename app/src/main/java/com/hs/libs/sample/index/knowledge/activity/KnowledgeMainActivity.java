package com.hs.libs.sample.index.knowledge.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hs.lib.inject.annotation.ViewInject;
import com.hs.libs.sample.R;
import com.hs.libs.sample.common.HSModulesBaseActivity;
import com.hs.libs.sample.index.knowledge.module.AbstractKnowledge;
import com.hs.tools.common.autoview.adapter_model.HSViewHolder;
import com.hs.tools.common.autoview.adapter_model.HeraclesAdapter;

import java.util.ArrayList;

/**
 * 一些java知识点测试总结
 * Created by huangshuo on 17/11/7.
 */
@ViewInject(id = R.layout.layout_knowledge)
public class KnowledgeMainActivity extends HSModulesBaseActivity {
    /**
     * 需要展示的设计模式结合
     */
    ArrayList<AbstractKnowledge> patterns;

    HeraclesAdapter<AbstractKnowledge> adapter;

    @ViewInject(id = R.id.activity_knowledge_list)
    ListView activity_pattern_list;

    @Override
    public void initData() {
        super.initData();

        patterns = new ArrayList<>();

        try {
            String[] items = getResources().getStringArray(R.array.knowledge_names);
            for (String item : items) {
                patterns.add((AbstractKnowledge) Class.forName(item).newInstance());
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        adapter = new HeraclesAdapter<AbstractKnowledge>(this, R.layout.item_main, patterns) {
            @Override
            public void handlerUI(HSViewHolder holder, AbstractKnowledge item) {
                super.handlerUI(holder, item);
                holder.setText(R.id.tv_item_main, item.getTitle());
            }
        };

        activity_pattern_list.setAdapter(adapter);
        activity_pattern_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                patterns.get(i).startKnowledge(KnowledgeMainActivity.this);
            }
        });
    }
}
