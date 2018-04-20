package com.hs.tools.common.autoview.adapter_model;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 用于持有每个item的数据
 * Created by huangshuo on 16/6/14.
 */
public class HSViewHolder implements HolderAction {

    /**
     * 其实view的对象是一定的，只是在复用过程中获取view的对象然后设置值
     */
    private SparseArray<View> views;

    private int position;
    private int childPosition = -1;
    private int layoutId;
    private View convertView;
    private Context context;

    public HSViewHolder(Context context, int position, int layoutId) {
        this.context = context;
        this.position = position;
        this.layoutId = layoutId;
        this.views = new SparseArray<View>();

        convertView = LayoutInflater.from(context).inflate(layoutId, null);
        convertView.setTag(this);
    }

    public HSViewHolder(Context context, int position, int layoutId, int childPosition) {

        this.context = context;
        this.position = position;
        this.layoutId = layoutId;
        this.views = new SparseArray<View>();
        this.childPosition = childPosition;

        convertView = LayoutInflater.from(context).inflate(layoutId, null);
        convertView.setTag(this);
    }


    public View getConvertView() {
        return convertView;
    }
    //获取ViewHolder实例

    /**
     * 获取对应item的ViewHolder
     *
     * @param position
     * @param layoutId
     * @param convertView
     * @return
     */
    public static HSViewHolder get(Context context, int position, int layoutId, View convertView) {

        if (convertView == null) {
            //不存在就新建一个新的
            return new HSViewHolder(context, position, layoutId);
        }

        //存在获取tag中的
        HSViewHolder holder = (HSViewHolder) convertView.getTag();

        if (layoutId != holder.layoutId) {
            holder.setLayoutId(layoutId);
        }
        holder.position = position;

        return holder;
    }

    public static HSViewHolder get(Context context, int position, int childPosition, int layoutId, View convertView) {

        if (convertView == null) {
            //不存在就新建一个新的
            return new HSViewHolder(context, position, layoutId, childPosition);
        }

        //存在获取tag中的
        HSViewHolder holder = (HSViewHolder) convertView.getTag();

        if (layoutId != holder.layoutId) {
            holder.setLayoutId(layoutId);
        }
        holder.position = position;
        holder.childPosition = childPosition;

        return holder;
    }

    /**
     * 获取holder中的对应的子view的对象
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getHolderView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    @Override
    public void setText(int viewId, String value) {
        TextView view = getHolderView(viewId);
        if (view != null)
            view.setText(value);
    }

    @Override
    public void setViewBackground(int viewId, int drawableId) {
        View view = getHolderView(viewId);
        if (view != null)
            view.setBackgroundResource(drawableId);
    }

    @Override
    public void setOnViewClickListener(int viewId, View.OnClickListener listener) {
        View view = getHolderView(viewId);
        if (view != null)
            view.setOnClickListener(listener);
    }

    @Override
    public void setViewVisiable(int viewId, int visiable) {
        View view = getHolderView(viewId);
        if (view != null)
            view.setVisibility(visiable);
    }

    @Override
    public void setCheckboxChecked(int viewId, boolean checked) {
        CheckBox view = getHolderView(viewId);
        if (view != null)
            view.setChecked(checked);
    }

    @Override
    public void setViewTag(int viewId, Object obeject) {
        View view = getHolderView(viewId);
        if (view != null)
            view.setTag(obeject);
    }

    @Override
    public Object getViewTag(int viewId) {
        View view = getHolderView(viewId);
        if (view != null) {
            return view.getTag();
        }
        return null;
    }

    @Override
    public void setTextColor(int viewId, int color) {
        View view = getHolderView(viewId);
        if(view != null){
            ((TextView)view).setTextColor(color);
        }
    }

    @Override
    public void setImageResource(int viewId, int drawableId) {
        ImageView view = getHolderView(viewId);
        if(view != null){
            view.setImageResource(drawableId);
        }
    }

    public int getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getChildPosition() {
        return childPosition;
    }

    public void setChildPosition(int childPosition) {
        this.childPosition = childPosition;
    }
}
