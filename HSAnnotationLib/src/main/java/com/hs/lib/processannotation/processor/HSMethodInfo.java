package com.hs.lib.processannotation.processor;

import com.hs.lib.processannotation.core.adapter.GeneratedAdapters;

import java.util.List;

import javax.lang.model.element.VariableElement;

/**
 * Created by owen on 16-1-11.
 */
public class HSMethodInfo {
    //方法的参数
    private List<? extends VariableElement> params;

    private String returnType;

    private String methodName;

    private String className;

    private String fatherName;

    private boolean isSingle = false;

    private String injectName = "";

    public List<? extends VariableElement> getParams() {
        return params;
    }

    public void setParams(List<? extends VariableElement> params) {
        this.params = params;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
        setClassName(methodName+ GeneratedAdapters.PROVIDE_ADAPER_SUFFIX);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void getInnerClassStr(StringBuilder builder){
        builder.append("public final static class ")
                .append(getClassName())
                .append(" extends Node<" + returnType + "> {\n\n");
        builder.append("private final " + fatherName + " module;\n\n");

        builder.append("public " + getClassName() + "(" + fatherName + " module) {\n");
        if(isSingle()) {
            builder.append("super(Node.SINGLETON);\n");
        }

        builder.append("this.module = module;\n}");

        builder.append("@Override\n").append("public "+getReturnType()+" get() {\n");
        builder.append("return module."+getMethodName()+"();\n}\n}");
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public boolean isSingle() {
        return isSingle;
    }

    public void setIsSingle(boolean isSingle) {
        this.isSingle = isSingle;
    }

    public String getInjectName() {
        return injectName;
    }

    public void setInjectName(String injectName) {
        this.injectName = injectName;
    }
}
