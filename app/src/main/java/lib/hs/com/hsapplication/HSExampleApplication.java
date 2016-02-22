package lib.hs.com.hsapplication;

import android.os.Environment;
import android.util.Log;

import com.hs.hshttplib.HttpCallBack;
import com.hs.hshttplib.titan.Titans;
import com.hs.lib.processannotation.ObjectGraph;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

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

        JSONObject ooo = new JSONObject();
        try {
            ooo.put("lng","11.0");
            ooo.put("lnlatg","11.0");
            ooo.put("userid","2");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Titans titans = appApi.titans;
//        titans.create(ILoginable.class).login(ooo,new HttpCallBack() {
//            @Override
//            public void onSuccess(String t) {
//                Log.d("owen", t);
//            }
//
//            @Override
//            public void onFailure(Throwable t, int errorNo, String strMsg) {
//                super.onFailure(t, errorNo, strMsg);
//                Log.d("owen", "no = "+errorNo+" "+t.toString());
//            }
//        });

        String downloadPath = "hsstc.apk";
        File file = new File(Environment.getExternalStorageDirectory(), downloadPath);

        titans.create(ILoginable.class).downLoadFile("http://pipahr.ppp.com/mobile/android/pipahrjobseeker2015_4_23-0.apk", file, new HttpCallBack() {
            @Override
            public void onSuccess(File f) {
                super.onSuccess(f);
                Log.d("owen","downLoadfile success size = "+f.length());
            }

            @Override
            public void onLoading(long count, long current) {
                super.onLoading(count, current);
                Log.d("owen", "downLoadfile count = "+count+" current = "+current);
            }
        });
    }

}
