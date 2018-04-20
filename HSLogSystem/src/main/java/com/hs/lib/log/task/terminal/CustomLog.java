package com.hs.lib.log.task.terminal;

import android.util.Log;

import com.hs.lib.log.HLog;

/**
 * Created by huangshuo on 16/8/30.
 */
public class CustomLog {

    public int index = 0;
    private static int MAX_LENGET = 4000;

    public  void print(int type, String tagStr, String msg) {
        int countOfSub = msg.length() / MAX_LENGET;

        if (countOfSub > 0) {
            for (int i = 0; i < countOfSub; i++) {
                String sub = msg.substring(index, index + MAX_LENGET);
                printNormal(type, tagStr, sub);
                index += MAX_LENGET;
            }
            printNormal(type, tagStr, msg.substring(index, msg.length()));
        } else {
            printNormal(type, tagStr, msg);
        }
    }

    private void printNormal(int type, String tag, String sub) {
        switch (type) {
            case HLog.V:
                Log.v(tag, sub);
                break;
            case HLog.D:
                Log.d(tag, sub);
                break;
            case HLog.I:
                Log.i(tag, sub);
                break;
            case HLog.W:
                Log.w(tag, sub);
                break;
            case HLog.E:
                Log.e(tag, sub);
                break;
            case HLog.A:
                Log.wtf(tag, sub);
                break;
            case HLog.CRASH:
                Log.e(tag,sub);
                break;
        }
    }

}
