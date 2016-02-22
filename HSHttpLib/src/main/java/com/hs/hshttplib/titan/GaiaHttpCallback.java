package com.hs.hshttplib.titan;

/**
 * Created by owen on 16-2-22.
 */
public abstract class GaiaHttpCallback<T>{

    public T callback;

    public GaiaHttpCallback(){
        callback = newInstance();
    }

    public abstract T newInstance();
}
