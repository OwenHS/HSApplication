package com.hs.libs.sample.widgetdemo.extend;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.View;

public class ViewUtils {


    public static <T extends View> T view(Activity aty, int id) {
        if (aty != null) {
            return (T) aty.findViewById(id);
        }
        return null;
    }

    public static <T extends View> T view(Fragment frag, int id) {
        if (frag != null && frag.getView() != null) {
            return (T) frag.getView().findViewById(id);
        }
        return null;
    }

    public static <T extends View> T view(View container, int id) {
        if (container != null) {
            return (T) container.findViewById(id);
        }
        return null;
    }

    @SuppressLint("NewApi")
    public static void setBackground(View v, Drawable d) {
        int sdk = Build.VERSION.SDK_INT;
        if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
            v.setBackgroundDrawable(d);
        } else {
            v.setBackground(d);
        }
    }

}
