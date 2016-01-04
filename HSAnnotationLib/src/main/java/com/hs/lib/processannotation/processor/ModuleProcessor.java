package com.hs.lib.processannotation.processor;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

/**
 * Created by owen on 15-12-25.
 *
 * 获取Module和ObjectProvider两种注解
 */
@SupportedAnnotationTypes("com.hs.lib.http.processannotation.annotation.Module")
public class ModuleProcessor extends AbstractProcessor {

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * Perform full-graph analysis on complete modules. This checks that all of
     * the module's dependencies are satisfied.
     */
    @Override
    public boolean process(Set<? extends TypeElement> types, RoundEnvironment env) {
        return false;
    }



}
