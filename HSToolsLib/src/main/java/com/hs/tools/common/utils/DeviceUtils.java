package com.hs.tools.common.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.UUID;

/**
 * 设备相关信息的工具类
 * 1.设备单位换算
 * 2.设备信息获取
 * Created by huangshuo on 16/9/6.
 */
public class DeviceUtils {

    private static Context mContext;

    public static void init(Context context) {
        mContext = context.getApplicationContext();
    }

    /***************************** 设备单位换算 start***************************************/
    // sp convert to px
    public static int sp2px(int spValue) {
        float scaledDensity = mContext.getResources().getDisplayMetrics().scaledDensity;
        int pxValue = (int) (spValue * scaledDensity + 0.5f);
        return pxValue;
    }

    // px convert to sp
    public static int px2sp(int pxValue) {
        float scaledDensity = mContext.getResources().getDisplayMetrics().scaledDensity;
        int spValue = (int) (pxValue / scaledDensity + 0.5f);
        return spValue;
    }

    // dp convert to px
    public static int dp2px(int dpValue) {
        float density = mContext.getApplicationContext().getResources().getDisplayMetrics().density;
        int pxValue = (int) (dpValue * density + 0.5f);
        return pxValue;
    }

    // px convert to dp
    public static int px2dp(int pxValue) {
        float scaledDensity = mContext.getResources().getDisplayMetrics().scaledDensity;
        int dpValue = (int) (pxValue * scaledDensity + 0.5f);
        return dpValue;
    }
    /**************************** 设备单位换算 end***************************************/

    /***************************** 设备信息获取 start***************************************/

    // 获取包名
    public static String packageName() {
        String name = mContext.getPackageName();
        return name;
    }

    // 获取版本号
    public static int versionCode() {
        int version = -1;
        try {
            String packageName = packageName();
            PackageManager packageManager = mContext.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            version = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return version;
    }

    // 获取版本名称
    public static String versionName() {
        String versionName = null;
        try {
            String packageName = packageName();
            PackageManager packageManager = mContext.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            versionName = packageInfo.versionName.trim();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    // 窗口宽度
    public static int getScreenWidth() {
        return mContext.getApplicationContext().getResources().getDisplayMetrics().widthPixels;
    }

    // 窗口高度
    public static int getScreenHeight() {
        return mContext.getResources().getDisplayMetrics().heightPixels;
    }

    // IMEI
    public static String getIMEI() {
        String imei = ((TelephonyManager) mContext
                .getApplicationContext()
                .getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        return imei;
    }

    // IMSI
    public static String getIMSI() {
        String imsi = ((TelephonyManager) mContext.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
        return imsi;
    }

    // 设备型号
    public static String getModel() {
        return Build.MODEL;
    }

    // 设备制造商
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    // mac地址
    // 需要权限 ACCESS_WIFI_STATE
    public static String getLocalMacAddress() {
        String macSerial = null;
        String str = "";

        try {
            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (IOException ex) {
            // 赋予默认值
            ex.printStackTrace();
        }

        if(TextUtils.isEmpty(macSerial)){
            macSerial = ((TelephonyManager) mContext
                    .getApplicationContext()
                    .getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        }

        if(TextUtils.isEmpty(macSerial)){
            String serial = null;

            String m_szDevIDShort = "35" +
                    Build.BOARD.length()%10+ Build.BRAND.length()%10 +

                    Build.CPU_ABI.length()%10 + Build.DEVICE.length()%10 +

                    Build.DISPLAY.length()%10 + Build.HOST.length()%10 +

                    Build.ID.length()%10 + Build.MANUFACTURER.length()%10 +

                    Build.MODEL.length()%10 + Build.PRODUCT.length()%10 +

                    Build.TAGS.length()%10 + Build.TYPE.length()%10 +

                    Build.USER.length()%10 ; //13 位

            try {
                serial = Build.class.getField("SERIAL").get(null).toString();
                //API>=9 使用serial号
                return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
            } catch (Exception exception) {
                //serial需要一个初始化
                serial = "serial"; // 随便一个初始化
            }

            //使用硬件信息拼凑出来的15位号码
            macSerial = new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        }
        return macSerial;
    }

    // 系统版本名
    public static String getOsReleaseVersion() {
        return Build.VERSION.RELEASE;
    }

    // 系统SDK版本
    public static int getOsSdkVersion() {
        return Build.VERSION.SDK_INT;
    }

    // 获取 uuid
    public static String getUUid() {
        String uuidString = UUID.randomUUID().toString();
        return uuidString;
    }

    // Application <meta-data> 元素 key为name 获取对应的value
    public static <T> T getAppMetaData(String name) {
        ApplicationInfo appInfo;
        try {
            appInfo = mContext.getPackageManager().getApplicationInfo(
                    mContext.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        if (appInfo.metaData != null && appInfo.metaData.containsKey(name)) {
            return (T) appInfo.metaData.get(name);
        }
        return null;
    }

    /* 一个获得当前进程的名字的方法 */
    public static String getCurProcessName()
    {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses())
        {
            if (appProcess.pid == pid)
            {
                return appProcess.processName;
            }
        }
        return null;
    }

    /***************************** 设备信息获取 end***************************************/
}
