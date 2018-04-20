package com.hs.lib.inject.processor;

import com.google.auto.service.AutoService;
import com.hs.lib.inject.annotation.ViewInject;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic.Kind;
import javax.tools.JavaFileObject;

/**
 * 每一个注解处理器类都必须有一个空的构造函数，默认不写就行;
 *
 * @author owen
 */
@AutoService(Processor.class)
public class HSInjectProcessor extends AbstractProcessor {

    /**
     * 日志打印类
     */
    private Messager messager;

    /**
     * 元素工具类
     */
    private Elements elementUtils;

    /**
     * 保存所有的要生成的注解文件信息
     */
    private Map<String, HSElementInfo> mElementMap = new HashMap<String, HSElementInfo>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();

        messager = processingEnv.getMessager();

        // 在这里打印gradle文件传进来的参数
        Map<String, String> map = processingEnv.getOptions();
        for (String key : map.keySet()) {
            messager.printMessage(Kind.NOTE, "key" + "：" + map.get(key));
        }
    }

    /**
     * 这里必须指定，这个注解处理器是注册给哪个注解的。注意，它的返回值是一个字符串的集合，包含本处理器想要处理的注解类型的合法全称
     *
     * @return 注解器所支持的注解类型集合，如果没有这样的类型，则返回一个空集合
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotataions = new LinkedHashSet<String>();
        annotataions.add(ViewInject.class.getCanonicalName());
        return annotataions;
    }

    /**
     * 指定使用的Java版本，通常这里返回SourceVersion.latestSupported()，默认返回SourceVersion.RELEASE_6
     *
     * @return 使用的Java版本
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        /**
         * 防止处理多次，要清空
         * */
        mElementMap.clear();

        String packageName;
        String qfClassName;
        String className;

        for (Element element : roundEnv.getElementsAnnotatedWith(ViewInject.class)) {
            if (element.getKind() == ElementKind.CLASS) {
                messager.printMessage(Kind.NOTE, "ElementKind.CLASS start");
                TypeElement classElement = (TypeElement) element;


                // 获取完全类名
                qfClassName = classElement.getQualifiedName().toString();
                messager.printMessage(Kind.NOTE, "qfClassName = " + qfClassName);
                // 获取类名
                className = classElement.getSimpleName().toString();
                messager.printMessage(Kind.NOTE, "className = " + className);
//				classElement.getSuperclass().getClass().getName();

                PackageElement packageElement = null;
                if (classElement.getEnclosingElement() instanceof PackageElement) {
                    packageElement = (PackageElement) classElement.getEnclosingElement();
                } else {
                    packageElement = (PackageElement) classElement.getEnclosingElement().getEnclosingElement();
                    className = classElement.getEnclosingElement().getSimpleName().toString() + "$" + className;
                }
                // 获取包名
                packageName = packageElement.getQualifiedName().toString();
                messager.printMessage(Kind.NOTE, "packageName = " + packageName);

                // 获取类注解获得的view的id
                int layoutId = classElement.getAnnotation(ViewInject.class).id();

                HSElementInfo info = mElementMap.get(qfClassName);
                if (info != null) {
                    info.setLayoutId(layoutId);
                } else {
                    info = new HSElementInfo(packageName, className);
                    info.setLayoutId(layoutId);
                    info.setElement(classElement);
                    mElementMap.put(qfClassName, info);
                }
                messager.printMessage(Kind.NOTE, "ElementKind.CLASS end");
            } else if (element.getKind() == ElementKind.FIELD) {
                messager.printMessage(Kind.NOTE, "ElementKind.FIELD start");
                VariableElement variableElment = (VariableElement) element;
                TypeElement classElement = (TypeElement) variableElment.getEnclosingElement();
                PackageElement packageElement = elementUtils.getPackageOf(classElement);

                packageName = packageElement.getQualifiedName().toString();
                className = getClassName(classElement, packageName);
                qfClassName = classElement.getQualifiedName().toString();

                // 对应view的id
                int viewId = variableElment.getAnnotation(ViewInject.class).id();
                boolean isOnclick = variableElment.getAnnotation(ViewInject.class).onClick();

                HSElementInfo info = mElementMap.get(qfClassName);
                if (info == null) {
                    info = new HSElementInfo(packageName, className);
                    info.setElement(classElement);
                    mElementMap.put(qfClassName, info);
                }

                HSViewInfo viewInfo = new HSViewInfo();
                String fieldName = variableElment.getSimpleName().toString();
                String fieldType = variableElment.asType().toString();

                viewInfo.setFieldName(fieldName);
                viewInfo.setFieldType(fieldType);
                viewInfo.setViewId(viewId);
                viewInfo.setOnclick(isOnclick);
                info.putViewInfo(viewId, viewInfo);

                messager.printMessage(Kind.NOTE, "ElementKind.FIELD end");

            }
        }

        HSElementInfo info = null;
        try {
            // 遍历编写class文件
            for (String key : mElementMap.keySet()) {
                info = mElementMap.get(key);
                JavaFileObject jfo = processingEnv.getFiler().createSourceFile(info.getExtraFullName(),
                        info.getElement());
                Writer writer = jfo.openWriter();
                writer.write(info.generateJavaCode());
                writer.flush();
                writer.close();
            }
            mElementMap.clear();
        } catch (IOException e) {
            error(info.getElement(), "Unable to write injector for type %s: %s", info.getElement(), e.getMessage());
        }

        return true;
    }

    private static String getClassName(TypeElement type, String packageName) {
        int packageLen = packageName.length() + 1;
        return type.getQualifiedName().toString().substring(packageLen).replace('.', '$');
    }

    private void error(Element element, String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        messager.printMessage(Kind.ERROR, message, element);
    }


    /**
     * 检查BindView修饰的元素的合法性
     */
    private boolean checkSAnnotationValid(Element element, Class<?> clazz) {

        if (ClassValidator.isPrivate(element)) {
            error(element, "%s() must can not be private.", element.getSimpleName());
            return false;
        }
        return true;

    }

}
