package lib.hs.com.hsapplication.httpcenter;

import com.hs.hshttplib.titan.GaiaCommonCallback;
import com.hs.hshttplib.titan.GaiaHttp;
import com.org.besth.kukithirdlibs.netcenter.netrequest.impl.cluster.NetRequest;
import com.org.besth.kukithirdlibs.netcenter.netrequest.interfaces.HttpCallback;
import com.org.besth.kukithirdlibs.netcenter.netrequest.params.AbstractParams;
import com.org.besth.kukithirdlibs.netcenter.netrequest.params.NormalParams;

import java.util.Map;

import lib.hs.com.hsapplication.HSExampleApplication;

/**
 * Created by owen on 16-2-23.
 */
public class JinHttp extends GaiaHttp<AbstractParams> {

    public JinHttp(){
        NetRequest.init(HSExampleApplication.current.getApplicationContext());
    }

    @Override
    public void post(String url, AbstractParams abstractParams, final GaiaCommonCallback k) {
        NetRequest.post(url, abstractParams, new HttpCallback<String>() {
            @Override
            public void preStart() {

            }

            @Override
            public void onSuccess(String s) {
                k.onSuccess(200,s+" jinqi");
            }

            @Override
            public void onFailure(String s) {

            }

            @Override
            public void onFinish() {

            }
        });
    }

    @Override
    public AbstractParams translateTo(Map<String, String> paramsMap) {

        NormalParams params = new NormalParams();

        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            params.put(entry.getKey(), entry.getValue());
        }
        return params;
    }
}
