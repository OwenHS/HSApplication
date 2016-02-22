package lib.hs.com.hsbaselib;

import android.app.Application;

/**
 * Created by owen on 15-12-8.
 * 基础App类
 * 1.添加了log系统的设置
 */
public class HSApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

//        /** 注册异常处理器 */
//        Log.setDebug(Log.DEBUG);
//        Log.setLevel(Trace.ALL);
//        Log.setLocalLogFileOutput(getApplicationContext(), 7, Log.DEBUG);
//        ExceptionHandleUtil.register(getApplicationContext());

    }
}
