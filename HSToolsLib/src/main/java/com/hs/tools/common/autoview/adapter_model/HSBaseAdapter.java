package com.hs.tools.common.autoview.adapter_model;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * BaseAdapter的基础封装类，将BaseAdapter的一些基础类封装起来，无需用户重复编写。
 * Created by huangshuo on 16/6/14.
 */
public abstract class HSBaseAdapter<T,K extends HSViewHolder> extends BaseAdapter {

    private List<T> mDatas;
    private Context context;

    private int layoutId;

    protected MultiItemTypeSupport multiSupport;

    public HSBaseAdapter(Context context, int layoutId, List<T> mDatas) {
        this.mDatas = mDatas == null ? new ArrayList<T>() : mDatas;
        this.layoutId = layoutId;
        this.context = context;
    }

    public HSBaseAdapter(Context context, List<T> mDatas,MultiItemTypeSupport multiSupport) {
        this.mDatas = mDatas == null ? new ArrayList<T>() : mDatas;
        this.context = context;
        this.multiSupport = multiSupport;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        K holder = getViewHolder(context, position, layoutId, convertView);

        handlerUI(holder,getItem(position));

        return holder.getConvertView();
    }

    /**
     * ArrayList<View>[] scrapViews = new ArrayList[viewTypeCount];
     * for (int i = 0; i < viewTypeCount; i++) {
     *    scrapViews[i] = new ArrayList<View>();
     * }
     * 上面是有关多个type的adapter复用的源码，可以这么理解在AbsListView(也就是ListView的父类)
     * 有个RecycleBin，也就是我们adapter复用中的那个回收器，他有个2层list的表，如果当前的convertView
     * 的type是0，如果复用成了1，那么返回的convertView就又变成了null。
     **/
    @Override
    public int getViewTypeCount() {
        if(multiSupport != null)
        return multiSupport.getViewTypeCount();

        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        if(multiSupport == null){
            return 0;
        }
        return position > mDatas.size() ? 0 : multiSupport.getItemViewType(position,getItem(position));
    }

    /**
     * 生成当前item的viewHolder
     * @param context
     * @param position
     * @param layoutId
     * @param convertView
     * @return
     */
    public abstract K getViewHolder(Context context, int position, int layoutId, View convertView);

    /**
     * 交给用户处理UI
     * @param holder
     * @param item
     */
    public abstract void handlerUI(HSViewHolder holder, T item);

    public void setmDatas(List<T> mDatas) {
        this.mDatas = mDatas;
    }

}
