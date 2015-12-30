package com.hs.lib.http.processannotation.processor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.AnnotationValueVisitor;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.SimpleAnnotationValueVisitor6;
import javax.tools.Diagnostic;

/**
 * Created by owen on 15-12-30.
 */
public class Util {
    public static Map<String, Object> getAnnotation(Class<?> annotationType, Element element, ProcessingEnvironment env) {
        //getAnnotationMirrors 返回直接存在于此元素上的注释
        for (AnnotationMirror annotation : element.getAnnotationMirrors()) {
            env.getMessager().printMessage(Diagnostic.Kind.NOTE, "annotation.getAnnotationType() = "+annotation.getAnnotationType().toString());
            if (!rawTypeToString(annotation.getAnnotationType(), '$',env)
                    .equals(annotationType.getName())) {
                continue;
            }

            Map<String, Object> result = new LinkedHashMap<String, Object>();
            for (Method m : annotationType.getMethods()) {
                //遍历注解对象的方法
                result.put(m.getName(), m.getDefaultValue());
            }

            for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> e
                    : annotation.getElementValues().entrySet()) {
                String name = e.getKey().getSimpleName().toString();
                Object value = e.getValue().accept(VALUE_EXTRACTOR, null);
                Object defaultValue = result.get(name);
                if (!lenientIsInstance(defaultValue.getClass(), value)) {
                    throw new IllegalStateException(String.format(
                            "Value of %s.%s is a %s but expected a %s\n    value: %s",
                            annotationType, name, value.getClass().getName(), defaultValue.getClass().getName(),
                            value instanceof Object[] ? Arrays.toString((Object[]) value) : value));
                }
                result.put(name, value);
            }
            return result;
        }
        return null; // Annotation not found.
    }


    /**
     * Returns a string for the raw type of {@code type}. Primitive types are always boxed.
     */
    public static String rawTypeToString(TypeMirror type, char innerClassSeparator,ProcessingEnvironment env) {
        //TypeMirror是用与获取数据的信息，通过Element.asType获得
        if (!(type instanceof DeclaredType)) {
            throw new IllegalArgumentException("Unexpected type: " + type);
        }
        StringBuilder result = new StringBuilder();
        DeclaredType declaredType = (DeclaredType) type;
        rawTypeToString(result, (TypeElement) declaredType.asElement(), innerClassSeparator);
        return result.toString();
    }

    static void rawTypeToString(StringBuilder result, TypeElement type,
                                char innerClassSeparator) {
        String packageName = getPackage(type).getQualifiedName().toString();
        String qualifiedName = type.getQualifiedName().toString();
        if (packageName.isEmpty()) {
            result.append(qualifiedName.replace('.', innerClassSeparator));
        } else {
            result.append(packageName);
            result.append('.');
            result.append(
                    qualifiedName.substring(packageName.length() + 1).replace('.', innerClassSeparator));
        }
    }


    public static PackageElement getPackage(Element type) {
        while (type.getKind() != ElementKind.PACKAGE) {
            type = type.getEnclosingElement();
        }
        return (PackageElement) type;
    }

    private static final AnnotationValueVisitor<Object, Void> VALUE_EXTRACTOR =
            new SimpleAnnotationValueVisitor6<Object, Void>() {
                @Override
                public Object visitString(String s, Void p) {
                    if ("<error>".equals(s)) {
//                        throw new CodeGenerationIncompleteException("Unknown type returned as <error>.");
                    } else if ("<any>".equals(s)) {
//                        throw new CodeGenerationIncompleteException("Unknown type returned as <any>.");
                    }
                    return s;
                }

                @Override
                public Object visitType(TypeMirror t, Void p) {
                    return t;
                }

                @Override
                protected Object defaultAction(Object o, Void v) {
                    return o;
                }

                @Override
                public Object visitArray(List<? extends AnnotationValue> values, Void v) {
                    Object[] result = new Object[values.size()];
                    for (int i = 0; i < values.size(); i++) {
                        result[i] = values.get(i).accept(this, null);
                    }
                    return result;
                }
            };


    /**
     * Returns true if {@code value} can be assigned to {@code expectedClass}.
     * Like {@link Class#isInstance} but more lenient for {@code Class<?>} values.
     */
    private static boolean lenientIsInstance(Class<?> expectedClass, Object value) {
        if (expectedClass.isArray()) {
            Class<?> componentType = expectedClass.getComponentType();
            if (!(value instanceof Object[])) {
                return false;
            }
            for (Object element : (Object[]) value) {
                if (!lenientIsInstance(componentType, element)) return false;
            }
            return true;
        } else if (expectedClass == Class.class) {
            return value instanceof TypeMirror;
        } else {
            return expectedClass == value.getClass();
        }
    }
}
