package com.hs.hshttplib.titan;

import com.hs.hshttplib.annotations.CallBack;
import com.hs.hshttplib.annotations.Get;
import com.hs.hshttplib.annotations.Param;
import com.hs.hshttplib.annotations.Post;
import com.hs.hshttplib.annotations.PostJson;
import com.hs.hshttplib.annotations.Put;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by owen on 16-2-16.
 */
public class RequestMethodInfoFactory {

    private static String TAG = "RequestMethodInfoFactory";

    //用于解析
    public static RequestMethodInfo parse(Method method) {
        RequestMethodInfo info = new RequestMethodInfo();
        parseAnnotation(info, method);
        parseParams(info, method);
        return info;
    }

    //解析方法上注解的信息,默认是get方法
    private static void parseAnnotation(RequestMethodInfo info, Method method) {

        for (Annotation annotation : method.getAnnotations()) {
            if(annotation instanceof Put){
                info.url = ((Put) annotation).value();
                info.requestMethod = RequestMethodInfo.GaiaMethod.Put;
            }else if(annotation instanceof Post){
                info.url = ((Post) annotation).value();
                info.requestMethod = RequestMethodInfo.GaiaMethod.Post;
            }else if(annotation instanceof PostJson){
                info.url = ((PostJson) annotation).value();
                info.requestMethod = RequestMethodInfo.GaiaMethod.PostJson;
            }else{
                //默认的是get方法
                info.url = ((Get) annotation).value();
                info.requestMethod = RequestMethodInfo.GaiaMethod.Get;
            }
        }
    }

    //分析方法参数上注解的信息
    private static void parseParams(RequestMethodInfo info, Method method) {
        Type[] methodTypes = method.getParameterTypes();
        Annotation[][] annotations = method.getParameterAnnotations();

        //用于存放数据的key值
        HashMap<Integer,String> params_values = new HashMap<>();

        int count = methodTypes.length;
        for (int i = 0; i < count; i++) {
            //将包含Param标注的写入map中
            for(Annotation annotation : annotations[i]){
                if(annotation instanceof Param){
                    params_values.put(i, ((Param) annotation).value());
                }
                else if(annotation instanceof CallBack){
                    info.callBackIndex = i;
                }
            }
        }
        info.params_values = params_values;
    }
}
