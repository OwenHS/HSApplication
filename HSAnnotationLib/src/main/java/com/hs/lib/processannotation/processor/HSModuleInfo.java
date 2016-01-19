package com.hs.lib.processannotation.processor;

import com.hs.lib.processannotation.core.adapter.GeneratedAdapters;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.TypeElement;

/**
 * Created by owen on 16-1-8.
 */
public class HSModuleInfo {
    private String packageName;
    private String className;
    private String quailtyName;
    private String extraClassName;

    private TypeElement element;

    private List<Object> injects;

    private ArrayList<HSMethodInfo> methodInfos = new ArrayList<>();

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
        this.extraClassName = className + GeneratedAdapters.MODULE_ADAPTER_SUFFIX;
    }

    public String getQuailtyName() {
        return quailtyName;
    }

    public void setQuailtyName(String quailtyName) {
        this.quailtyName = quailtyName;
    }



    public ArrayList<HSMethodInfo> getMethodInfos() {
        return methodInfos;
    }

    public void setMethodInfos(ArrayList<HSMethodInfo> methodInfos) {
        this.methodInfos = methodInfos;
    }

    public String getExtraFullName() {
        return packageName + "." + extraClassName;
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

        builder.append("import com.hs.lib.processannotation.core.adapter.ModuleAdapter;\n");
        builder.append("import com.hs.lib.processannotation.node.Node;\n");
        builder.append("import com.hs.lib.processannotation.node.NodeController;\n");
        builder.append('\n');

        builder.append("public class ").append(extraClassName);
        builder.append(" extends ModuleAdapter<").append(className).append(">");
        builder.append(" {\n");

        appendLocalVariable(builder);

        generateNodesMethod(builder);
        builder.append('\n');

        for(HSMethodInfo methodInfo : methodInfos) {
            methodInfo.getInnerClassStr(builder);
        }

        builder.append("}\n");
        return builder.toString();
    }

    private void appendLocalVariable(StringBuilder builder) {
        builder.append("private static final String[] INJECTS = new String[]{");
        for (int i = 0; i < injects.size(); i++) {
            if (i != 0) {
                builder.append(",");
            }
            builder.append("\"" + injects.get(i).toString()+"\"");
        }
        builder.append("};\n");

        builder.append(" public ").append(extraClassName).append("(){\n");
        builder.append("   super(INJECTS);}\n");
    }

    private void generateNodesMethod(StringBuilder builder) {
        builder.append("  @Override \n");
        builder.append(" public void getNodes(NodeController nodeController, ")
                .append(className).append(" module){\n");
        for (HSMethodInfo info : methodInfos) {
            builder.append("nodeController.methodStringMap.put(\"");
            builder.append(info.getReturnType() + "\"");
            builder.append(",new " + getExtraFullName()).append(".").append(info.getClassName()).append("(module));\n");
        }
        builder.append("}");
    }

    public void setInjects(List<Object> injects) {
        this.injects = injects;
    }
}
