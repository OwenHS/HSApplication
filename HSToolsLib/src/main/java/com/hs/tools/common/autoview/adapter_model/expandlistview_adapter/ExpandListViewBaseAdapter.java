package com.hs.tools.common.autoview.adapter_model.expandlistview_adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;

import com.hs.tools.common.autoview.adapter_model.HSViewHolder;

import java.util.List;


/**
 * Created by huangshuo on 16/7/9.
 *
 * @param <T> 父类数据类型
 * @param <K> 子类数据类型
 */
public abstract class ExpandListViewBaseAdapter<T, K,R extends HSViewHolder> implements ExpandableListAdapter {

    public List<T> groupData;
    public List<List<K>> childData;

    //组的布局文件
    private int groupLayoutId = -1;
    //子的布局文件
    private int childLayoutId = -1;

    private Context context;

    public ExpandListViewBaseAdapter(Context context,int groupLayoutId,int childLayoutId){
        this.context = context;
        this.groupLayoutId = groupLayoutId;
        this.childLayoutId = childLayoutId;
    }

    public ExpandListViewBaseAdapter(Context context){
    }

    /**
     * 　注册一个观察者(observer)，当此适配器数据修改时即调用此观察者。
     */
    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    /**
     * 获取组的个数
     * 返回值:组的个数
     */
    @Override
    public int getGroupCount() {
        return groupData == null ? 0 : groupData.size();
    }

    /**
     * 获取指定组中的数据
     */
    @Override
    public T getGroup(int groupPosition) {
        return groupData == null ? null : groupData.get(groupPosition);
    }


    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        R holder = getViewHolder(context,groupPosition,groupLayoutId,convertView);

        handlerUI(holder,getGroup(groupPosition));

        return holder.getConvertView();
    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return childData == null ? 0 : childData.get(groupPosition).size();
    }

    /**
     * 获取指定组中的指定子元素数据
     */
    @Override
    public K getChild(int groupPosition, int childPosition) {
        return childData == null ? null : childData.get(groupPosition).get(childPosition);
    }

    /**
     * 获取指定组中的指定子元素ID，这个ID在组里一定是唯一的。
     * 联合ID（getCombinedChildId(long, long)）在所有条目（所有组和所有元素）中也是唯一的。
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    /**
     * 获取一个视图对象，显示指定组中的指定子元素数据
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        R holder = getChildViewHolder(context,groupPosition,childPosition,childLayoutId,convertView);

        handlerChildUI(holder,getChild(groupPosition,childPosition));

        return holder.getConvertView();
    }

    /**
     * 是否选中指定位置上的子元素。
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    /**
     * 　组和子元素是否持有稳定的ID,也就是底层数据的改变不会影响到它们。
     * 返回值：返回一个Boolean类型的值，
     * 如果为TRUE，意味着相同的ID永远引用相同的对象
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * ExpandableListAdapter里面的所有条目都可用吗？ 如果是yes，就意味着所有条目可以选择和点击了。
     *
     * @return 返回True表示所有条目均可用。
     */
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    /**
     * 返回值：如果当前适配器不包含任何数据则返回True。
     * 经常用来决定一个空视图是否应该被显示。
     * 一个典型的实现将返回表达式getCount() == 0的结果，但是由于getCount()包含了头部和尾部，
     * 适配器可能需要不同的行为。
     */
    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     * 当组展开状态的时候此方法被调用。
     */
    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    /**
     * 当组收缩状态的时候此方法被调用。
     */
    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    /**
     * 从列表所有项(组或子项)中获得一个唯一的子ID号。
     * 可折叠列表要求每个元素(组或子项)在所有的子元素和组中有一个唯一的ID。
     * 本方法负责根据所给的子ID号和组ID号返回唯一的ID。此外
     * ，若hasStableIds()是true，那么必须要返回稳定的ID。
     *
     * @param groupId
     * @param childId
     * @return
     */
    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    /**
     * 生成当前item的viewHolder
     * @param context
     * @param position
     * @param layoutId
     * @param convertView
     * @return
     */
    public abstract R getViewHolder(Context context, int position, int layoutId, View convertView);

    public abstract R getChildViewHolder(Context context, int position, int childLayoutId,int layoutId, View convertView);


    /**
     * 交给用户处理UI
     * @param holder
     * @param item
     */
    public abstract void handlerUI(HSViewHolder holder, T item);

    /**
     * 交给用户处理UI
     * @param holder
     * @param item
     */
    public abstract void handlerChildUI(HSViewHolder holder, K item);

    public int getGroupLayoutId() {
        return groupLayoutId;
    }

    public void setGroupLayoutId(int groupLayoutId) {
        this.groupLayoutId = groupLayoutId;
    }

    public int getChildLayoutId() {
        return childLayoutId;
    }

    public void setChildLayoutId(int childLayoutId) {
        this.childLayoutId = childLayoutId;
    }
}
