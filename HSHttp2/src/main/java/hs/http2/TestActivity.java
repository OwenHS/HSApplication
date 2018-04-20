package hs.http2;

import android.app.Activity;
import android.os.Bundle;

import hs.http2.core.HSHttpClient;
import hs.http2.request.HttpUrl;
import hs.http2.request.Request;
import hs.http2.response.Callback;

/**
 * Created by huangshuo on 17/11/20.
 */

public class TestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        HSHttpClient client = new HSHttpClient();

        Request request = new Request.Builder().url(
                new HttpUrl.Builder()
                        .scheme("http")
                        .host("www.baidu.com")
                        .pathSegment("s")
                        .addQueryParameter("wd","黄硕").build()).builder();

        client.newCall(request).execute(new Callback());
    }
}
