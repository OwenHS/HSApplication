package com.hs.hshttplib.titan;

import java.util.HashMap;

/**
 * Created by owen on 16-2-16.
 */
public class RequestMethodInfo {

    public Class<?> retrunType;

    public enum GaiaMethod{Get,Put,Post,PostJson,DownFile,UploadFile,JNI,Request,Execute};

    public String url = "";

    public String allUrl = "";

    //回调接口的位置
    public int callBackIndex = -1;

    //json接口的位置
    public int jsonIndex = -1;

    //下载文件存放地址参数位置
    public int fileIndex;

    public String fileKey;
    //文件网络下载参数存放地址
    public int fileUrlIndex;

    public GaiaMethod requestMethod;

    public HashMap<Integer,String> params_values;

}
