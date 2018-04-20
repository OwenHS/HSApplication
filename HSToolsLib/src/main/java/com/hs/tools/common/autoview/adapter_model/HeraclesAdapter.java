package com.hs.tools.common.autoview.adapter_model;

import android.content.Context;
import android.view.View;

import java.util.List;

/**
 * Created by huangshuo on 16/6/14.
 */
public class HeraclesAdapter<T> extends HSBaseAdapter<T, HSViewHolder> {

    public HeraclesAdapter(Context context, int layoutId, List<T> mDatas) {
        super(context, layoutId, mDatas);
    }

    public HeraclesAdapter(Context context, List<T> mDatas, MultiItemTypeSupport multiSupport) {
        super(context, mDatas, multiSupport);
    }

    @Override
    public HSViewHolder getViewHolder(Context context, int position, int layoutId, View convertView) {
        if(multiSupport != null){
            return  HSViewHolder.get(context,position,multiSupport.getLayoutId(position,getItem(position)),convertView);
        }
        return HSViewHolder.get(context, position, layoutId, convertView);
    }

    @Override
    public void handlerUI(HSViewHolder holder, T item) {

    }
}
