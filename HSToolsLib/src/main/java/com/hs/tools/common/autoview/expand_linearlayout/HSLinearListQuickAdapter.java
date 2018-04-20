package com.hs.tools.common.autoview.expand_linearlayout;

import android.content.Context;
import android.widget.TextView;

import java.util.List;

/**
 * Created by huangshuo on 16/6/15.
 */
public class HSLinearListQuickAdapter<T> extends HSLinearListAdapter<T> implements IQuickOption {

    public HSLinearListQuickAdapter(Context context, List mDatas, int layoutId) {
        super(context, mDatas, layoutId);
    }

    @Override
    public void setText(int viewId, String value, int position) {
        ( (TextView)parent.getChildAt(position).findViewById(viewId)).setText(value);
    }

    @Override
    public void handleUI(T item, int position) {
    }

}
