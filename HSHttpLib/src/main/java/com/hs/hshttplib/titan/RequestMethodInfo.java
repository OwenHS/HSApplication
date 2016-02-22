package com.hs.hshttplib.titan;

import java.util.HashMap;

/**
 * Created by owen on 16-2-16.
 */
public class RequestMethodInfo {

    public enum GaiaMethod{Get,Put,Post,PostJson,DownFile};

    public String url = "";

    public int callBackIndex;

    public int jsonIndex;

    public int fileIndex;

    public int fileUrlIndex;

    public GaiaMethod requestMethod;

    public HashMap<Integer,String> params_values;


}
