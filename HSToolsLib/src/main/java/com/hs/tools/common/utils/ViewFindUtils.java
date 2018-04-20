package com.hs.tools.common.utils;

import android.app.Activity;
import android.content.Context;
import android.util.SparseArray;
import android.view.View;

public class ViewFindUtils {
	
	/**
	 * fragment统一管理器
	 * @param id
	 * @param convertView
	 * @return
	 */
	public static <T extends View> T findViewById(int id , View convertView){
		View childView = null;
		childView = convertView.findViewById(id);
		return (T)childView;
	}
	
	public static <T extends View> T hold(int id , View convertView){
		SparseArray<View> tag = (SparseArray<View>)convertView.getTag();
		View childView = null;
		if(tag == null){
			tag = new SparseArray<View>();
			childView = convertView.findViewById(id);
			tag.put(id, childView);
			convertView.setTag(tag);
		}else{
			childView = tag.get(id);
			if(childView == null){
				childView = convertView.findViewById(id);
				tag.put(id, childView);
			}
		}
		
		return (T)childView;
	}
	
	/***
	 * activity findview工具类
	 * @param id
	 * @param context
	 * @return
	 */
	public static <T extends View> T findViewById(int id , Context context){

		View declaredView = ((Activity)context).getWindow().getDecorView();
		T childView = (T) declaredView.findViewById(id);
		return childView; 
		
	}
}
