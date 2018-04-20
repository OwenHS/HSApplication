package com.hs.tools.common.autoview.adapter_model;

import android.view.View;

/**
 * Created by huangshuo on 16/6/14.
 */
public interface HolderAction {

    //设置TextView的值
    void setText(int viewId, String value);

    void setViewBackground(int viewId, int drawableId);

    void setOnViewClickListener(int viewId, View.OnClickListener listener);

    void setViewVisiable(int viewId, int visiable);

    void setCheckboxChecked(int viewId, boolean checked);

    void setViewTag(int viewId, Object obeject);

    void setTextColor(int viewId, int color);

    void setImageResource(int viewId, int drawableId);

    Object getViewTag(int viewId);
}
