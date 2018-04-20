package com.hs.tools.common.autoview.expand_linearlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 这是一个LinearLayout，但是可以用来在scrollview中线性叠加view，并简化方便了操作
 * Created by huangshuo on 16/6/15.
 */
public class HSLinearListView extends LinearLayout{

    public HSLinearListBaseAdapter adapter;

    public HSLinearListView(Context context) {
        super(context);
    }

    public HSLinearListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAdapter(HSLinearListAdapter adapter){
        this.adapter = adapter;
        adapter.parent = this;
        adapter.addAllViewByData();
    }

    public void notifyDataSetChanged(){
        adapter.addAllViewByData();
    }

}
