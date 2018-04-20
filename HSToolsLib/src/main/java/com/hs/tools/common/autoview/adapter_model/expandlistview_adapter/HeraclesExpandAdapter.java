package com.hs.tools.common.autoview.adapter_model.expandlistview_adapter;

import android.content.Context;
import android.view.View;

import com.hs.tools.common.autoview.adapter_model.HSViewHolder;


/**
 * Created by huangshuo on 16/7/10.
 */
public abstract class HeraclesExpandAdapter<T, K> extends ExpandListViewBaseAdapter<T, K, HSViewHolder> {

    public HeraclesExpandAdapter(Context context, int groupLayoutId, int childLayoutId) {
        super(context, groupLayoutId, childLayoutId);
    }

    public HeraclesExpandAdapter(Context context) {
        super(context);
    }

    @Override
    public HSViewHolder getViewHolder(Context context, int position, int layoutId, View convertView) {
        return HSViewHolder.get(context, position, layoutId, convertView);
    }

    @Override
    public HSViewHolder getChildViewHolder(Context context, int position, int childLayoutId, int layoutId, View convertView) {
        return HSViewHolder.get(context, position, childLayoutId, layoutId, convertView);
    }
}
