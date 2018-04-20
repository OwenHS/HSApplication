package com.hs.lib.inject.processor;

import com.hs.lib.inject.InjectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.TypeElement;

public class HSElementInfo {

    private String packageName;
    private String className;
    private String extraClassName;
    private TypeElement element;

    private ArrayList<String> superClasses = new ArrayList<String>();

    private Map<Integer, HSViewInfo> viewIdMap = new HashMap<Integer, HSViewInfo>();

    private int layoutId = -1;

    public static final String Extra = "HSELEMENT";

    public HSElementInfo(String packageName, String className) {
        this.packageName = packageName;
        this.setClassName(className);
        extraClassName = className + "$$" + Extra;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public TypeElement getElement() {
        return element;
    }

    public void setElement(TypeElement element) {
        this.element = element;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    public void putViewInfo(int id, HSViewInfo viewInfo) {
        viewIdMap.put(id, viewInfo);
    }

    public CharSequence getExtraFullName() {

        return getPackageName() + "." + extraClassName;
    }

    public void addSuperClass(String superClass) {
        superClasses.add(superClass);
    }

    public String generateJavaCode() {

        StringBuilder builder = new StringBuilder();
        builder.append("package ").append(packageName).append(";\n\n");

        builder.append("import android.app.Activity;\n");
        builder.append("import android.view.LayoutInflater;\n");
        builder.append("import android.view.View.OnClickListener;\n");
        builder.append("import android.view.View;\n");
        builder.append("import android.view.ViewGroup;\n");
        builder.append("import com.hs.lib.inject.InjectType;\n");
        builder.append("import com.hs.lib.inject.AbstractInjector;\n");
        builder.append('\n');

        builder.append("public class ").append(extraClassName);
        builder.append("<T extends ").append(getTargetClassName()).append(">");
        builder.append(" implements AbstractInjector<T>");
        builder.append(" {\n");

        generateInjectMethod(builder);
        builder.append('\n');

        builder.append("}\n");
        return builder.toString();
    }

    private void generateInjectMethod(StringBuilder builder) {
        builder.append("  @Override ")
                .append("public Object inject(final InjectType type, final T target, Object source,boolean add) {\n");
        builder.append("View rootView = null;\n");
        builder.append(InjectType.ACTIVITY.getJudgement());
        builder.append("((Activity)source).setContentView(" + layoutId + ");\n");
        builder.append(InjectType.VIEW.getJudgement());
        builder.append("rootView = LayoutInflater.from(((View)source).getContext()).inflate(" + layoutId + ",add?(ViewGroup)source:null);\n}\n");

        builder.append(InjectType.ACTIVITY.getJudgement());
        for (Integer key : viewIdMap.keySet()) {
            HSViewInfo viewInfo = viewIdMap.get(key);
            builder.append("target."+viewInfo.getFieldName()+" = (" + viewInfo.getFieldType() + ")(((Activity)source).findViewById(" + viewInfo.getViewId() + "));");
            if (viewInfo.isOnclick()) {
                builder.append("target."+viewInfo.getFieldName()+".setOnClickListener((OnClickListener) target);");
            }
        }
        builder.append(InjectType.VIEW.getJudgement());
        builder.append("View sourceView = null\n;");
        builder.append("if(add){\nsourceView = (View)source;}");
        builder.append("else{\nsourceView = rootView;\n}\n");
        for (Integer key : viewIdMap.keySet()) {
            HSViewInfo viewInfo = viewIdMap.get(key);
            builder.append("target."+viewInfo.getFieldName()+" = (" + viewInfo.getFieldType() + ")(sourceView.findViewById(" + viewInfo.getViewId() + "));");
            if (viewInfo.isOnclick()) {
                builder.append("target."+viewInfo.getFieldName()+".setOnClickListener((OnClickListener) target);");
            }
        }
        builder.append("  }\n");
        builder.append("return rootView;");
        builder.append("  }\n");
    }

    private String getTargetClassName() {
        return className.replace("$", ".");
    }
}
