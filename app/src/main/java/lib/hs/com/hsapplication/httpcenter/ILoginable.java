package lib.hs.com.hsapplication.httpcenter;

import com.hs.hshttplib.HttpCallBack;
import com.hs.hshttplib.annotations.CallBack;
import com.hs.hshttplib.annotations.DownFile;
import com.hs.hshttplib.annotations.DownUrl;
import com.hs.hshttplib.annotations.FileContainer;
import com.hs.hshttplib.annotations.ParamJson;
import com.hs.hshttplib.annotations.PostJson;

import org.json.JSONObject;

import java.io.File;


/**
 * Created by owen on 16-2-3.
 */
public interface ILoginable {
    @PostJson("/update_jwd")
    LoginResponse login(@ParamJson JSONObject aaa,
                        @CallBack HttpCallBack gaiaCallBack);

    @DownFile()
    LoginResponse downLoadFile(@DownUrl String url,
                               @FileContainer File file,
                               @CallBack HttpCallBack gaiaCallBack);

}
