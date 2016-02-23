package lib.hs.com.hsapplication.httpcenter;

import com.hs.hshttplib.annotations.CallBack;
import com.hs.hshttplib.annotations.Param;
import com.hs.hshttplib.annotations.Post;
import com.hs.hshttplib.titan.GaiaCommonCallback;

import lib.hs.com.hsapplication.httpcenter.bean.LoginResponse;


/**
 * Created by owen on 16-2-3.
 */
public interface ILoginable {

    @Post("/update_jwd")
    LoginResponse login(@Param("lng") String lng,
                        @Param("lat") String lat,
                        @Param("userid") String userid,
                        @CallBack GaiaCommonCallback gaiaCallBack);

}
