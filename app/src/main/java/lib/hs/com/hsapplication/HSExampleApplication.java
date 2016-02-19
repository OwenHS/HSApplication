package lib.hs.com.hsapplication;

import android.util.Log;

import com.hs.hshttplib.HttpCallBack;
import com.hs.hshttplib.titan.Titans;
import com.hs.lib.processannotation.ObjectGraph;

import lib.hs.com.hsapplication.config.AppApi;
import lib.hs.com.hsapplication.config.AppConfig;
import lib.hs.com.hsapplication.httpcenter.ILoginable;
import lib.hs.com.hsbaselib.HSApplication;

/**
 * Created by owen on 15-12-8.
 */
public class HSExampleApplication extends HSApplication {


    @Override
    public void onCreate() {
        super.onCreate();


        AppApi appApi = new AppApi();
        ObjectGraph graph = ObjectGraph.create(new AppConfig());
        graph.inject(appApi);

        Titans titans = appApi.titans;
        titans.create(ILoginable.class).login("11.0", "11.0", "2", "test",new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                Log.d("owen", t);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Log.d("owen", "no = "+errorNo+" "+t.toString());
            }
        });
    }

}
