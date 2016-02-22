package lib.hs.com.hsapplication.httpcenter;

import com.hs.hshttplib.HttpCallBack;
import com.hs.hshttplib.annotations.CallBack;
import com.hs.hshttplib.annotations.DownFile;
import com.hs.hshttplib.annotations.DownUrl;
import com.hs.hshttplib.annotations.FileContainer;
import com.hs.hshttplib.annotations.Get;
import com.hs.hshttplib.annotations.Param;
import com.hs.hshttplib.titan.HSTestCallback;

import java.io.File;


/**
 * Created by owen on 16-2-3.
 */
public interface ILoginable {

    @Get("/update_jwd")
    LoginResponse login(@Param String lng,
                        @Param String lat,
                        @Param String userid,
                        @CallBack HSTestCallback gaiaCallBack);

    @DownFile()
    LoginResponse downLoadFile(@DownUrl String url,
                               @FileContainer File file,
                               @CallBack HttpCallBack gaiaCallBack);

}
