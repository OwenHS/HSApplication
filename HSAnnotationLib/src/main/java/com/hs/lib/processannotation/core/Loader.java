package com.hs.lib.processannotation.core;

import com.hs.lib.processannotation.core.internal.Memoizer;

import java.lang.reflect.AccessibleObject;

/**
 * Created by owen on 16-1-4.
 * 加载器
 */
public abstract class Loader {
    private final Memoizer<ClassLoader, Memoizer<String, Class<?>>> caches =
            new Memoizer<ClassLoader, Memoizer<String, Class<?>>>() {
                @Override
                protected Memoizer<String, Class<?>> create(final ClassLoader classLoader) {
                    return new Memoizer<String, Class<?>>() {
                        @Override
                        protected Class<?> create(String className) {
                            try {
                                return classLoader.loadClass(className);
                            } catch (ClassNotFoundException e) {
                                return Void.class; // Cache the failure (negative case).
                            }
                        }
                    };
                }
            };

//    /**
//     * Returns a binding that uses {@code @Inject} annotations, or null if no valid binding can
//     * be found or created.
//     */
//    public abstract Binding<?> getAtInjectBinding(
//            String key, String className, ClassLoader classLoader, boolean mustHaveInjections);
//
//    /**
//     * Returns a module adapter for {@code moduleClass} or throws a {@code TypeNotPresentException} if
//     * none can be found.
//     */
//    public abstract <T> ModuleAdapter<T> getModuleAdapter(Class<T> moduleClass);
//
//    /**
//     * Returns the static injection for {@code injectedClass}.
//     */
//    public abstract StaticInjection getStaticInjection(Class<?> injectedClass);

    /**
     * 用来缓存被初始化过的对象，是一个嵌套的 Memoizer 结构，
     * 简单理解就是嵌套的 HashMap，第一层 Key 是 ClassLoader，第二层 Key 是 ClassName，Value 是 Class 对象。
     *
     * 其实对于编译时注解就是这块比运行时要快，直接使用缓存里已经加载到jvm中的类
     */
    protected Class<?> loadClass(ClassLoader classLoader, String name) {
        // A null classloader is the system classloader.
        classLoader = (classLoader != null) ? classLoader : ClassLoader.getSystemClassLoader();
        return caches.get(classLoader).get(name);
    }

    /**
     * Instantiates a class using its default constructor and the given {@link ClassLoader}. This
     * method does not attempt to {@linkplain AccessibleObject#setAccessible set accessibility}.
     */
    protected <T> T instantiate(String name, ClassLoader classLoader) {
        try {
            Class<?> generatedClass = loadClass(classLoader, name);
            if (generatedClass == Void.class) {
                return null;
            }
            @SuppressWarnings("unchecked")
            T instance = (T) generatedClass.newInstance();
            return instance;
        } catch (InstantiationException e) {
            throw new RuntimeException("Failed to initialize " + name, e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to initialize " + name, e);
        }
    }

}
