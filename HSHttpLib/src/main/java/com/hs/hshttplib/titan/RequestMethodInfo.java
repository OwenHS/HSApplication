package com.hs.hshttplib.titan;

import java.util.HashMap;

/**
 * Created by owen on 16-2-16.
 */
public class RequestMethodInfo {

    public enum GaiaMethod{Get,Put,Post,PostJson};

    public String url;

    public int callBackIndex;

    public GaiaMethod requestMethod;

    public HashMap<Integer,String> params_values;
}
