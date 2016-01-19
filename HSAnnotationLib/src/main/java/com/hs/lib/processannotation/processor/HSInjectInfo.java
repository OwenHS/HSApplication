package com.hs.lib.processannotation.processor;

import com.hs.lib.processannotation.core.adapter.GeneratedAdapters;

import java.util.ArrayList;

import javax.lang.model.element.TypeElement;

/**
 * Created by owen on 16-1-12.
 */
public class HSInjectInfo {

    private ArrayList<HSInjectFieldInfo> fieldInfos = new ArrayList<HSInjectFieldInfo>();

    private String packageName;
    private String className;
    private String quailtyName;
    private String extraClassName;

    private TypeElement element;


    public ArrayList<HSInjectFieldInfo> getFieldInfos() {
        return fieldInfos;
    }

    public void setFieldInfos(ArrayList<HSInjectFieldInfo> fieldInfos) {
        this.fieldInfos = fieldInfos;
    }

    public String getExtraFullName() {
        return packageName + "." + extraClassName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
        this.extraClassName = className + GeneratedAdapters.INJECT_ADAPTER_SUFFIX;
    }

    public String getQuailtyName() {
        return quailtyName;
    }

    public void setQuailtyName(String quailtyName) {
        this.quailtyName = quailtyName;
    }

    public String getExtraClassName() {
        return extraClassName;
    }

    public void setExtraClassName(String extraClassName) {
        this.extraClassName = extraClassName;
    }

    public TypeElement getElement() {
        return element;
    }

    public void setElement(TypeElement element) {
        this.element = element;
    }


    public String generateJavaCode() {
        StringBuilder builder = new StringBuilder();
        builder.append("package ").append(packageName).append(";\n\n");
        builder.append("import com.hs.lib.processannotation.node.Node;\n");
        builder.append("import com.hs.lib.processannotation.node.NodeController;\n");
        builder.append('\n');

        builder.append("public class ").append(extraClassName);
        builder.append(" extends Node<").append(className).append(">");
        builder.append(" {\n");

        appendLocalVariable(builder);

        generateAttachMethod(builder);
        builder.append('\n');

        generateInjectMethod(builder);
        generateGetMethod(builder);

        builder.append("}\n");
        return builder.toString();
    }

    private void generateGetMethod(StringBuilder builder) {

        builder.append("@Override\n public ").append(className).append(" get() {\n");
        builder.append(className).append(" newInstance = new ").append(className).append("();\n");
        builder.append("injectMembers(newInstance);");
        builder.append("return newInstance;\n");
        builder.append("\n}\n");
    }

    private void generateInjectMethod(StringBuilder builder) {
        builder.append("@Override\n public void injectMembers(");

        builder.append(className).append(" injectClazz) {\n");
        for(HSInjectFieldInfo info :fieldInfos) {
            builder.append("injectClazz.").append(info.getFieldValue()).append(" = this.node");
            builder.append(info.getFieldValue() + ".get();");
        }

        builder.append("\n}\n");

    }

    private void generateAttachMethod(StringBuilder builder) {
        builder.append("@Override\n public void attach(NodeController nodeController){\n\n");
        for(HSInjectFieldInfo info :fieldInfos) {
            builder.append("this.node").append(info.getFieldValue()).append(" = (Node<")
                    .append(info.getFieldType()).append(">)nodeController.requestNode(\"")
                    .append(info.getFieldType() + "\");");
        }

        builder.append("\n}\n");
    }

    private void appendLocalVariable(StringBuilder builder) {
        for(HSInjectFieldInfo info :fieldInfos){
            builder.append("public Node<").append(info.getFieldType()).append("> node")
                    .append(info.getFieldValue()+";\n");
        }

    }
}
