package com.hs.lib.inject;

import com.hs.lib.inject.processor.HSElementInfo;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 用于注入解析
 *
 * @author owen
 */
public class HSInjectUtil {

    static final Map<Class<?>, AbstractInjector<Object>> INJECT = new LinkedHashMap<Class<?>, AbstractInjector<Object>>();


    /**
     */
    public static Object inject(InjectType type, Object target, Object source) {
        return inject(type, target, source, true);
    }

    public static Object inject(InjectType type, Object target, Object source, boolean add) {
        AbstractInjector<Object> injector = findInjector(target);
        if (injector == null) {
            return null;
        }
        return injector.inject(type, target, source, add);
    }

    public static AbstractInjector<Object> findInjector(Object obj) {
        Class<?> clazz = obj.getClass();
        AbstractInjector<Object> injector = INJECT.get(clazz);
        if (injector == null) {
            try {
                String a = clazz.getName();
                Class<?> injectClazz = Class.forName(clazz.getName() + "$$" + HSElementInfo.Extra);
                injector = (AbstractInjector<Object>) injectClazz.newInstance();
                INJECT.put(clazz, injector);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            } catch (InstantiationException e) {
                e.printStackTrace();
                return null;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
        }
        return injector;
    }
}
