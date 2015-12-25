package com.hs.lib.http.processannotation.processor;

import com.hs.lib.http.processannotation.annotation.Module;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

/**
 * Created by owen on 15-12-25.
 *
 * 获取Module和ObjectProvider两种注解
 */
@SupportedAnnotationTypes({"com.hs.lib.http.processannotation.annotation.Module",
        "com.hs.lib.http.processannotation.annotation.ObjectProvider"})
public class ModuleProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        for(Element element : roundEnv.getElementsAnnotatedWith(Module.class)){
            //找到由module注解的模块，也就是注入需要的依赖实例化的文件
            if(element.getKind() == ElementKind.CLASS){
                TypeElement classElement = (TypeElement)element;
                getProviderMethodByClass(classElement);
            }

        }

        return false;
    }

    /**
     * 获取module中所有Provider的方法
     * @param element　
     */
    private void getProviderMethodByClass(TypeElement element) {
        for(Element childElement : element.getEnclosedElements()) {
            if (childElement.getKind() == ElementKind.METHOD) {
                //找到用于依赖注入的方法

            }
        }
    }
}
