package com.hs.hshttplib.titan;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedHashMap;

/**
 * Created by owen on 16-2-3.
 * <p/>
 * Titan是建造者模式中的，目前使用build的设计模式来写，
 * 其中模块含有:  １
 * ２
 * 　         　　3
 */
public class Titans {

    //director类，用来生成需要的产品

    private static final String TAG = "Titans";

    //代理对象的缓存
    private LinkedHashMap<String, Object> proxyCache = new LinkedHashMap<>();

    //所有接口方法的缓存
    private LinkedHashMap<Method, MethodHandler> methodHandlerCache = new LinkedHashMap();

    public GaiaHttp httpClient;

    public String baseUrl;

    private Titans(String baseUrl, GaiaHttp gaiaHttp) {
        this.httpClient = gaiaHttp;
        this.baseUrl = baseUrl;
    }

    //创建动态对象,proxyCache用来缓存动态代理生成的对象,重复创建对象
    public <T> T create(Class<T> service) {
        T t = null;
        String name = service.getName();
        synchronized (proxyCache) {
            Log.d(TAG, "需要生成的对象名 ： " + name);
            t = (T) proxyCache.get(name);
            if (t == null) {
                Log.d(TAG, "缓存中不含有接口 " + name + " 的对象");
                t = (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //当动态代理触发相应代码的时候调用
                        MethodHandler handler = loadMethodInfo(method);
                        if(handler != null){
                            handler.invoke(args);
                        }
                        return null;
                    }
                });
                proxyCache.put(name, t);
            }
        }
        return t;
    }

    /**
     * 通过对相应方法的注解的解析，获得需要的信息,然后执行http的操作
     *
     * @param method 需要load的方法名
     */
    private MethodHandler loadMethodInfo(Method method) {

        MethodHandler methodHandler = null;

        synchronized (methodHandlerCache) {
            methodHandler = methodHandlerCache.get(method);
            if (methodHandler == null) {
                methodHandler = MethodHandler.create(method, this);
                if(methodHandler == null){
                    return null;
                }
                methodHandlerCache.put(method, methodHandler);
            }
        }
        return methodHandler;
    }


    public static final class Builder {

        //是一个接口用于代理所有可以用到的http
        private GaiaHttp httpClient;
        private String baseUrl = "";

        //建造者builder,用于初始化Titan的所有模块。
        public Builder() {

        }

        public Titans build() {

            if (httpClient == null) {
                httpClient = new DefaultHSHttp();
            }

            return new Titans(baseUrl, httpClient);
        }

        public Builder setHttpClient(GaiaHttp httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }
    }

}
