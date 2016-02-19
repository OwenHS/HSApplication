package lib.hs.com.hsapplication.httpcenter;

import com.hs.hshttplib.HttpCallBack;
import com.hs.hshttplib.annotations.CallBack;
import com.hs.hshttplib.annotations.Get;
import com.hs.hshttplib.annotations.Param;


/**
 * Created by owen on 16-2-3.
 */
public interface ILoginable {
    @Get("/update_jwd")
    LoginResponse login(@Param("lng") String lng,
                        @Param("lat") String lat,
                        @Param("userid") String userid,
                        String a,
                        @CallBack HttpCallBack gaiaCallBack);

}
