package com.hs.tools.common.autoview.adapter_model;

/**
 * Created by huangshuo on 16/6/14.
 */
public interface MultiItemTypeSupport<T> {

    int getViewTypeCount();

    int getItemViewType(int position, T t);

    int getLayoutId(int position, T item);
}
