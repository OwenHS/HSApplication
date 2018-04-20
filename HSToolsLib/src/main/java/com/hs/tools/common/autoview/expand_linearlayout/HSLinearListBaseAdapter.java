package com.hs.tools.common.autoview.expand_linearlayout;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangshuo on 16/6/15.
 */
public abstract class HSLinearListBaseAdapter<T> {

    protected Context context;
    /**
     * 数据源
     */
    protected List<T> mDatas;
    /**
     * 布局id
     */
    protected int layoutId = -1;

    public HSLinearListView parent;


    public HSLinearListBaseAdapter(Context context, List<T> mDatas, int layoutId) {
        this.context = context;
        this.layoutId = layoutId;
        this.mDatas = mDatas == null ? new ArrayList<T>() : mDatas;
    }


    abstract int getItemCount();

    abstract T getItem(int position);

    /**
     * 获取对应item的view
     *
     * @param position
     * @return
     */
    abstract View getView(int position);

    /**
     * 判断返回需要的layout_id
     *
     * @param position
     * @return layoutId
     */
    abstract public int getItemViewType(int position, T t);

    abstract public int getViewTypeCount();

    /**
     * 用于设置界面UI
     * @param item
     */
    public abstract void handleUI(T item,int position);

    public abstract void addAllViewByData();

    public List<T> getmDatas(){
        return mDatas;
    }
}
