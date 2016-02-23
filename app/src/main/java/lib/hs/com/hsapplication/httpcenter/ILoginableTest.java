package lib.hs.com.hsapplication.httpcenter;

import com.hs.hshttplib.titan.GaiaCommonCallback;

import lib.hs.com.hsapplication.httpcenter.bean.LoginResponse;

/**
 * Created by owen on 16-2-23.
 */
public class ILoginableTest implements ILoginable {

    @Override
    public LoginResponse login( String lng,  String lat,  String userid,  GaiaCommonCallback gaiaCallBack) {
        gaiaCallBack.onSuccess(200, "it's right");
        return null;
    }
}