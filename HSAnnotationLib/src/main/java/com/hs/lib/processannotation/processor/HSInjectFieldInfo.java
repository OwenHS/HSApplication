package com.hs.lib.processannotation.processor;

/**
 * Created by owen on 16-1-12.
 */
public class HSInjectFieldInfo {
    private String fieldType;
    private String fieldValue;
    private String className;

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
