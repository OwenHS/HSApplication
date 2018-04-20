package com.hs.tools.common.autoview.expand_linearlayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

/**
 * Created by huangshuo on 16/6/15.
 */
public abstract class HSLinearListAdapter<T> extends HSLinearListBaseAdapter<T> {

    public HSLinearListAdapter(Context context, List<T> mDatas, int layoutId) {
        super(context, mDatas, layoutId);
    }

    @Override
    T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position, T t) {
        return layoutId;
    }

    @Override
    final public View getView(int position) {

        int layoutId = getItemViewType(position, getItem(position));

        View convertView = LayoutInflater.from(context).inflate(layoutId, null);

        parent.addView(convertView);

        handleUI(getItem(position), position);

        return convertView;
    }

    @Override
    public void addAllViewByData() {
        parent.removeAllViews();
        for (int i = 0; i < mDatas.size(); i++) {
            getView(i);
        }
    }
}
