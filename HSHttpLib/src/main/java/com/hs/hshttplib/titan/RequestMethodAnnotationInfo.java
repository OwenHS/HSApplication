package com.hs.hshttplib.titan;

import com.hs.hshttplib.annotations.Get;
import com.hs.hshttplib.annotations.Path;
import com.hs.hshttplib.annotations.Put;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 解析传入的请求接口的注解，获得请求的信息
 * Created by owen on 16-2-16.
 */
public class RequestMethodAnnotationInfo {


    public String urlPath;
    public String requesthMethod;

    public void parse(Method method) {
        for (Annotation annotation : method.getAnnotations()) {
            if (annotation instanceof Path) {
                //获取接口路径
                urlPath = ((Path) annotation).value();
            } else if (annotation instanceof Get) {
                requesthMethod = "GET";
            } else if (annotation instanceof Put) {
                requesthMethod = "PUT";
            }
        }
    }
}
