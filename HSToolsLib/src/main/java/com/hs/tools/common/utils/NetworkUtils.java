package com.hs.tools.common.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by owen on 16-3-4.
 */
public class NetworkUtils {

    /**
     * 判断网络状态
     */
    public static int isWiFi(Context cxt) {
        ConnectivityManager cm = (ConnectivityManager) cxt
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // wifi的状态：ConnectivityManager.TYPE_WIFI
        // 3G的状态：ConnectivityManager.TYPE_MOBILE

        NetworkInfo info = cm.getActiveNetworkInfo();

        if (info != null
                && info.isConnected()) {
            // 有网状态

            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                // wifi 状态
                return 0;
            } else {
                // 3G/4G
                return 1;
            }

        } else {
            // 无网状态
            return -1;
        }
    }
}
