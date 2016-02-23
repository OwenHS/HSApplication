package lib.hs.com.hsapplication.httpcenter;

import android.os.Handler;
import android.os.Message;

import com.hs.hshttplib.titan.GaiaCommonCallback;
import com.hs.hshttplib.titan.GaiaHttp;

import java.util.Map;

/**
 * Created by owen on 16-2-23.
 */
public class UrlClient extends GaiaHttp<Map> {

    HttpRequestor client;

    GaiaCommonCallback call;

    public UrlClient() {
        client = new HttpRequestor();
    }

    String response;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            call.onSuccess(200, response+" this is Url");
        }
    };

    @Override
    public Map translateTo(Map<String, String> paramsMap) {
        return paramsMap;
    }

    @Override
    public void post(final String url, final Map map, GaiaCommonCallback k) {
        call = k;
        new Thread() {
            @Override
            public void run() {
                HttpRequestor request = new HttpRequestor();
                try {
                    response = request.doPost(url, map);
                    handler.sendEmptyMessage(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
