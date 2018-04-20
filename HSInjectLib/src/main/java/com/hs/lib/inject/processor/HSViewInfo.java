package com.hs.lib.inject.processor;

public class HSViewInfo {
	
	private int viewId;
	private String fieldName;
	private String fieldType;
	private boolean isOnclick = false;
	
	public int getViewId() {
		return viewId;
	}
	public void setViewId(int viewId) {
		this.viewId = viewId;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public boolean isOnclick() {
		return isOnclick;
	}
	public void setOnclick(boolean isOnclick) {
		this.isOnclick = isOnclick;
	}

}
