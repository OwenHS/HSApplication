package com.hs.hshttplib.titan;

import com.hs.hshttplib.annotations.CallBack;
import com.hs.hshttplib.annotations.DownFile;
import com.hs.hshttplib.annotations.DownUrl;
import com.hs.hshttplib.annotations.Execute;
import com.hs.hshttplib.annotations.FileContainer;
import com.hs.hshttplib.annotations.Get;
import com.hs.hshttplib.annotations.JNI;
import com.hs.hshttplib.annotations.Param;
import com.hs.hshttplib.annotations.ParamJson;
import com.hs.hshttplib.annotations.Post;
import com.hs.hshttplib.annotations.PostJson;
import com.hs.hshttplib.annotations.Put;
import com.hs.hshttplib.annotations.Request;
import com.hs.hshttplib.annotations.UploadFile;

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
        try {
            parseParams(info, method);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return info;
    }

    //解析方法上注解的信息,默认是get方法
    private static void parseAnnotation(RequestMethodInfo info, Method method) {

        for (Annotation annotation : method.getAnnotations()) {
            if (annotation instanceof Put) {
                info.url = ((Put) annotation).value();
                info.requestMethod = RequestMethodInfo.GaiaMethod.Put;
            } else if (annotation instanceof Post) {
                info.url = ((Post) annotation).value();
                info.requestMethod = RequestMethodInfo.GaiaMethod.Post;
            } else if (annotation instanceof PostJson) {
                info.url = ((PostJson) annotation).value();
                info.requestMethod = RequestMethodInfo.GaiaMethod.PostJson;
            } else if (annotation instanceof DownFile) {
                info.requestMethod = RequestMethodInfo.GaiaMethod.DownFile;
            } else if(annotation instanceof UploadFile){
                info.url = ((UploadFile) annotation).value();
                info.requestMethod = RequestMethodInfo.GaiaMethod.UploadFile;
            }else if (annotation instanceof Get) {
                //默认的是get方法
                info.url = ((Get) annotation).value();
                info.requestMethod = RequestMethodInfo.GaiaMethod.Get;
            } else if (annotation instanceof JNI) {
                info.url = ((JNI) annotation).value();
                info.requestMethod = RequestMethodInfo.GaiaMethod.JNI;
            } else if (annotation instanceof Request) {
                info.requestMethod = RequestMethodInfo.GaiaMethod.Request;
            } else if (annotation instanceof Execute) {
                info.url = ((Execute) annotation).value();
                info.requestMethod = RequestMethodInfo.GaiaMethod.Execute;
            }
        }
    }

    //分析方法参数上注解的信息
    private static void parseParams(RequestMethodInfo info, Method method) throws Exception {
        Type[] methodTypes = method.getParameterTypes();
        Annotation[][] annotations = method.getParameterAnnotations();

        Class<?> returnType = method.getReturnType();


        //用于存放数据的key值
        HashMap<Integer, String> params_values = new HashMap<>();

        int count = methodTypes.length;
        for (int i = 0; i < count; i++) {
            //将包含Param标注的写入map中
            for (Annotation annotation : annotations[i]) {
                if (annotation instanceof Param) {
//                    if (info.requestMethod == RequestMethodInfo.GaiaMethod.PostJson) {
//                        throw new Exception("PostJson Method donnot have Param ,must use ParamJson");
//                    }
                    params_values.put(i, ((Param) annotation).value());
                } else if (annotation instanceof CallBack) {
                    info.callBackIndex = i;
                } else if (annotation instanceof ParamJson) {
                    info.jsonIndex = i;
                } else if (annotation instanceof FileContainer) {
                    info.fileIndex = i;
                    info.fileKey = ((FileContainer) annotation).value();
                } else if (annotation instanceof DownUrl) {
                    info.fileUrlIndex = i;
                }
            }
        }
        info.params_values = params_values;
        info.retrunType = returnType;
    }
}
