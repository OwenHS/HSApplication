package lib.hs.com.hsapplication;

import lib.hs.com.hsapplication.config.AppApi;
import lib.hs.com.hsapplication.config.AppConfig;
import lib.hs.com.hsbaselib.HSApplication;

/**
 * Created by owen on 15-12-8.
 */
public class HSExampleApplication extends HSApplication {

    public AppApi appApi;

    public static HSExampleApplication current;

    public HSExampleApplication(){
        current = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appApi = new AppApi();
        ApplicationConfig(appApi, new AppConfig());
    }

}
