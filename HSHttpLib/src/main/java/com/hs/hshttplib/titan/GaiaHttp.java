package com.hs.hshttplib.titan;

import android.content.Context;
import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by owen on 16-2-18.
 */
public abstract class GaiaHttp<J> {

    /**
     * 用于持有
     */
    ConcurrentHashMap<Context, ArrayList<GaiaCommonCallback>> handleMap;

    public GaiaHttp() {
        handleMap = new ConcurrentHashMap<>();
    }

    /**
     * 用于配置HttpClient,属于全局配置
     */
    public void config() {
    }

    ;

    /**
     * 接收到网络数据
     */
    public void translateFrom() {
    }

    ;

    /**
     * 数据包装放到网上
     */
    public abstract J translateTo(Map<String, Object> paramsMap, RequestMethodInfo.GaiaMethod gigamethod);

    public void invoke(Object[] args, MethodHandler methodHandler) {
        RequestMethodInfo info = methodHandler.methodInfo;
        HashMap<String, Object> paramsMap = new HashMap<>();

        for (int i = 0; i < args.length; i++) {
            if (info.params_values != null && i != info.callBackIndex && info.params_values.get(i) != null) {
                paramsMap.put(info.params_values.get(i), args[i]);
            }
        }

        //将对象放入持有，然后必须释放，这样就不会造成内存泄漏
        GaiaCommonCallback gcCallBack = (GaiaCommonCallback<Class<?>>) args[info.callBackIndex];
        if (gcCallBack != null && gcCallBack.context != null) {
            if (!handleMap.containsKey(gcCallBack.context)) {
                Log.d("owen", "map put new key " + gcCallBack.context);
                handleMap.put(gcCallBack.context, new ArrayList<GaiaCommonCallback>());
            }
            Log.d("owen", "map add new value" + gcCallBack);
            handleMap.get(gcCallBack.context).add(gcCallBack);
        }

        try {
            if (info.requestMethod == RequestMethodInfo.GaiaMethod.Get) {
                get(info.allUrl, translateTo(paramsMap, RequestMethodInfo.GaiaMethod.Get), gcCallBack, info.retrunType);
            } else if (info.requestMethod == RequestMethodInfo.GaiaMethod.Post) {
                post(info.allUrl, translateTo(paramsMap, RequestMethodInfo.GaiaMethod.Post), gcCallBack, info.retrunType);
            } else if (info.requestMethod == RequestMethodInfo.GaiaMethod.Put) {
                put(info.allUrl, translateTo(paramsMap, RequestMethodInfo.GaiaMethod.Put), gcCallBack, info.retrunType);
            } else if (info.requestMethod == RequestMethodInfo.GaiaMethod.PostJson) {
                if (info.jsonIndex < 0 || (JSONObject) args[info.jsonIndex] == null)
                    postJson(info.allUrl, translateTo(paramsMap, RequestMethodInfo.GaiaMethod.Put), null, gcCallBack, info.retrunType);
                else
                    postJson(info.allUrl, (JSONObject) args[info.jsonIndex], gcCallBack, info.retrunType);
            } else if (info.requestMethod == RequestMethodInfo.GaiaMethod.DownFile) {
                if (args[info.fileIndex] instanceof String) {
                    downFile((String) args[info.fileUrlIndex], (String) args[info.fileIndex], (GaiaCommonCallback) args[info.callBackIndex], info.retrunType);
                }
            } else if (info.requestMethod == RequestMethodInfo.GaiaMethod.UploadFile) {
                if (args[info.fileIndex] instanceof List) {
                    uploadFile(info.allUrl, translateTo(paramsMap, RequestMethodInfo.GaiaMethod.UploadFile),info.fileKey ,(List<String>) args[info.fileIndex], (GaiaCommonCallback) args[info.callBackIndex], info.retrunType);
                } else if (args[info.fileIndex] instanceof String) {
                    List<String> adds = new ArrayList<>();
                    adds.add((String) args[info.fileIndex]);
                    uploadFile(info.allUrl, translateTo(paramsMap, RequestMethodInfo.GaiaMethod.UploadFile),info.fileKey ,adds, (GaiaCommonCallback) args[info.callBackIndex], info.retrunType);

                }
            } else if (info.requestMethod == RequestMethodInfo.GaiaMethod.JNI) {
                invokeJNI(info.allUrl, args, gcCallBack, info.retrunType);
            } else if (info.requestMethod == RequestMethodInfo.GaiaMethod.Execute) {
                execute(info.url, paramsMap, gcCallBack);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void invokeWithoutCallback(Object[] args, MethodHandler methodHandler) {
        RequestMethodInfo info = methodHandler.methodInfo;

        HashMap<String, Object> paramsMap = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            if (info.params_values != null && i != info.callBackIndex && info.params_values.get(i) != null) {
                paramsMap.put(info.params_values.get(i), args[i]);
            }
        }

        if (info.requestMethod == RequestMethodInfo.GaiaMethod.Request) {
            request(translateTo(paramsMap, RequestMethodInfo.GaiaMethod.Request));
        }
    }


    public <T> void get(String url, J j, GaiaCommonCallback httpCallBack, Class<?> t) {

    }

    public <T> void post(String url, J j, GaiaCommonCallback httpCallBack, Class<?> t) {

    }

    public <T> void put(String url, J j, GaiaCommonCallback httpCallBack, Class<?> t) {

    }

    public <T> void postJson(String url, J j, JSONObject jsonObject, GaiaCommonCallback httpCallBack, Class<?> t) {

    }

    public <T> void postJson(String url, JSONObject jsonObject, GaiaCommonCallback httpCallBack, Class<?> t) {

    }

    public <T> void downFile(String url, String save, GaiaCommonCallback httpCallBack, Class<?> t) {

    }

    public <T> void uploadFile(String url, J j, String fileKey, List<String> adds, GaiaCommonCallback httpCallBack, Class<?> t) {

    }


    public <T> void invokeJNI(String methodName, Object[] args, GaiaCommonCallback httpCallBack, Class<?> t) {

    }

    public <T> void execute(String methodName, Map<String, Object> paramsMap, GaiaCommonCallback httpCallBack) {

    }

    public void request(J j) {

    }

    /**
     * 将对应持有的对象删除
     */
    public void clear(Object context) {
        for (Map.Entry<Context, ArrayList<GaiaCommonCallback>> gcCallbackMap : handleMap.entrySet()) {

            Object mContext = gcCallbackMap.getKey();

            //去除引用
            if (mContext == context) {
                ArrayList<GaiaCommonCallback> list = gcCallbackMap.getValue();
                //将对象状态设置为结束
                for (GaiaCommonCallback callback : list) {
                    callback.setCancel(true);
                }
                list.clear();
                Log.d("owen", "map remove key " + mContext);
                handleMap.remove(mContext);
                Log.d("owen", "map size " + handleMap.size());
            }
        }
    }

    public void clear(Context context, Object obj) {
        for (Map.Entry<Context, ArrayList<GaiaCommonCallback>> gcCallbackMap : handleMap.entrySet()) {

            Object mContext = gcCallbackMap.getKey();

            //去除引用
            if (mContext == context) {
                ArrayList<GaiaCommonCallback> list = gcCallbackMap.getValue();
                //将对象状态设置为结束
                for (GaiaCommonCallback callback : list) {
                    callback.setCancel(true);
                }
                list.clear();
                Log.d("owen", "map remove key " + mContext);
                handleMap.remove(mContext);
                Log.d("owen", "map size " + handleMap.size());
            }
        }
    }
}
