package com.hs.tools.common.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatTools {

    /**
     * 判断是否是email
     *
     * @param value
     * @return
     */
    public static boolean isEmail(String value) {
        if (TextUtils.isEmpty(value))
            return false;
        String str = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher matcher = p.matcher(value);
        return matcher.matches();
    }

    /**
     * 判断是否是手机号
     *
     * @param value
     * @return
     */
    public static boolean isMobile(String value) {
        if (TextUtils.isEmpty(value))
            return false;
        Pattern p = Pattern.compile("\\+{0,1}[0-9]{11,13}");
        Matcher matcher = p.matcher(value);
        return matcher.matches();
    }


    public static boolean isMoveMobile(String value) {
        if (TextUtils.isEmpty(value))
            return false;
        Pattern p = Pattern.compile("\\+{0,1}[0-9]{11}");
        Matcher matcher = p.matcher(value);
        return matcher.matches();
    }


    public static boolean isNumber(String value) {
        if (TextUtils.isEmpty(value))
            return false;
        Pattern p = Pattern.compile("[0-9]+");
        Matcher matcher = p.matcher(value);
        return matcher.matches();
    }

}
