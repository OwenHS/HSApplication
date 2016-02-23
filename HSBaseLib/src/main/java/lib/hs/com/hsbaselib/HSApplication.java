package lib.hs.com.hsbaselib;

import android.app.Application;

import com.hs.lib.processannotation.ObjectGraph;

import lib.hs.com.logsystemlib.support.ExceptionHandleUtil;
import lib.hs.com.logsystemlib.support.Log;
import lib.hs.com.logsystemlib.support.Trace;

/**
 * Created by owen on 15-12-8.
 * 基础App类
 * 1.添加了log系统的设置
 */
public class HSApplication extends Application{

    public ObjectGraph graph;

    @Override
    public void onCreate() {
        super.onCreate();

        /** 注册异常处理器 */
        Log.setDebug(Log.DEBUG);
        Log.setLevel(Trace.ALL);
        Log.setLocalLogFileOutput(getApplicationContext(), 7, Log.DEBUG);
        ExceptionHandleUtil.register(getApplicationContext());

    }

    public void ApplicationConfig(Object appApi,Object appConfig){
        graph = ObjectGraph.create(appConfig);
        graph.inject(appApi);
    }
}
