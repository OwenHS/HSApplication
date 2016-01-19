package com.hs.lib.processannotation.processor;

import com.hs.lib.processannotation.annotation.ObjectInject;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * Created by owen on 16-1-12.
 */
@SupportedAnnotationTypes({"com.hs.lib.processannotation.annotation.Module"
        , "com.hs.lib.processannotation.annotation.ObjectInject"
        , "com.hs.lib.processannotation.annotation.ObjectProvider"})
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class ObjectInjectProcessor extends AbstractProcessor {


    private Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(ObjectInject.class);
        Map<String,HSInjectInfo> mElementMap = new LinkedHashMap<>();
        for (Element element : elements) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "onjectInjectProcessor");
            if(element.getKind() == ElementKind.FIELD) {

                VariableElement variableElement = (VariableElement)element;

                TypeElement classElement = (TypeElement)element.getEnclosingElement();
                PackageElement packageElement = elementUtils.getPackageOf(variableElement);

                String qualifiedName = classElement.getQualifiedName().toString();

                HSInjectInfo info = mElementMap.get(qualifiedName);
                if(info == null) {
                    info = new HSInjectInfo();
                    info.setElement(classElement);
                    info.setPackageName(packageElement.getQualifiedName().toString());
                    info.setClassName(classElement.getSimpleName().toString());
                    info.setQuailtyName(classElement.getQualifiedName().toString());
                    mElementMap.put(qualifiedName,info);
                }
                HSInjectFieldInfo fieldInfo = new HSInjectFieldInfo();
                String fieldType = variableElement.asType().toString();
                String fieldValue = variableElement.getSimpleName().toString();
                fieldInfo.setFieldType(fieldType);
                fieldInfo.setFieldValue(fieldValue);
                fieldInfo.setClassName(qualifiedName);
                info.getFieldInfos().add(fieldInfo);
            }
        }

        HSInjectInfo info = null;
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
//            error(info.getElement(), "Unable to write injector for type %s: %s", info.getElement(), e.getMessage());
        }

        return false;
    }

}
