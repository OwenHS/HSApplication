package com.hs.lib.processannotation.processor;

import com.hs.lib.processannotation.annotation.Module;
import com.hs.lib.processannotation.annotation.ObjectProvider;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * Created by owen on 15-12-25.
 * <p/>
 * 获取Module和ObjectProvider两种注解
 */
@SupportedAnnotationTypes({"com.hs.lib.processannotation.annotation.Module"
        , "com.hs.lib.processannotation.annotation.ObjectInject"
        , "com.hs.lib.processannotation.annotation.ObjectProvider"})
public class ModuleProcessor extends AbstractProcessor {

    private Map<String, HSModuleInfo> mModuleMap = new HashMap<String, HSModuleInfo>();

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


    private Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
    }

    /**
     * Perform full-graph analysis on complete modules. This checks that all of
     * the module's dependencies are satisfied.
     */
    @Override
    public boolean process(Set<? extends TypeElement> types, RoundEnvironment env) {

        Set<? extends Element> elements = env.getElementsAnnotatedWith(Module.class);
        for (Element element : elements) {
            if (element.getKind() == ElementKind.CLASS) {
                TypeElement classElement = (TypeElement) element;

                PackageElement packageElement = (PackageElement) element.getEnclosingElement();

                String packageName = packageElement.getQualifiedName().toString();
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "packageElement　= " + packageName);
                String className = classElement.getSimpleName().toString();
                String qualifiedName = classElement.getQualifiedName().toString();

                HSModuleInfo info = mModuleMap.get(qualifiedName);
                List<Object> injects = null;
                for( AnnotationMirror am :  classElement.getAnnotationMirrors() ){
                    if( Module.class.getName().equals(am.getAnnotationType().toString()))
                    {
                        AnnotationValue action = null;
                        for( Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : am.getElementValues().entrySet() )
                        {
                            if("objectInjects".equals(entry.getKey().getSimpleName().toString() ) )
                            {
                                action = entry.getValue();
                                break;
                            }
                        }

                        injects = (List<Object>)action.getValue();
                    }
                }

                if (info == null) {
                    info = new HSModuleInfo();
                    info.setElement(classElement);
                    info.setClassName(className);
                    info.setPackageName(packageName);
                    info.setQuailtyName(qualifiedName);
                    mModuleMap.put(qualifiedName, info);
                }
                info.setInjects(injects);
            }
        }

        Set<? extends Element> providerElements = env.getElementsAnnotatedWith(ObjectProvider.class);
        for (Element element : providerElements) {
            if (element.getKind() == ElementKind.METHOD) {
                ExecutableElement methodElement = (ExecutableElement) element;

                TypeElement classElement = (TypeElement) methodElement.getEnclosingElement();
                String qualifiedName = classElement.getQualifiedName().toString();

                PackageElement packageElement = elementUtils.getPackageOf(classElement);
                String packageName = packageElement.getQualifiedName().toString();

                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "ObjectProvider　= " + packageName);

                HSModuleInfo info = mModuleMap.get(qualifiedName);

                if (info != null) {
                    List<? extends VariableElement> params = methodElement.getParameters();
                    String retrunType = methodElement.getReturnType().toString();
                    String methodName = methodElement.getSimpleName().toString();

                    HSMethodInfo methodInfo = new HSMethodInfo();
                    methodInfo.setMethodName(methodName);
                    methodInfo.setReturnType(retrunType);
                    methodInfo.setParams(params);
                    methodInfo.setFatherName(info.getClassName());

                    info.getMethodInfos().add(methodInfo);
                }
            }
        }


        HSModuleInfo info = null;
        try {
            // 遍历编写class文件
            for (String key : mModuleMap.keySet()) {
                info = mModuleMap.get(key);
                JavaFileObject jfo = processingEnv.getFiler().createSourceFile(info.getExtraFullName(),
                        info.getElement());
                Writer writer = jfo.openWriter();
                writer.write(info.generateJavaCode());
                writer.flush();
                writer.close();
            }
            mModuleMap.clear();
        } catch (IOException e) {
//            error(info.getElement(), "Unable to write injector for type %s: %s", info.getElement(), e.getMessage());
        }

        return false ;
    }


}
