package com.hs.tools.common.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by huangshuo on 16/9/6.
 */
public class ResourceUtils {

    private static Context context;

    public static void init(Context mContext) {
        context = mContext;
    }

    // 获取xml中配置的String 值
    public static String getStringResource(int id){
        return context.getResources().getString(id);
    }

    // 获取xml中的Color 色值
    public static int getColorResource(int id){
        return context.getResources().getColor(id);
    }

    // 获取xml中的Integer 值
    public static int getIntegerResource(int id){
        return context.getResources().getInteger(id);
    }

    // 获取xml中的String数组
    public static String[] getStringsResource(int id){
        return context.getResources().getStringArray(id);
    }

    // 获取Assets下的资源输入流
    public static InputStream getAssetsResource( String filename){
        try {
            return context.getAssets().open(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 获取Assets下的String资源
    public static String getAssetString(String name) {
        String string = "";
        AssetManager asset = context.getAssets();
        try {
            InputStream input = asset.open(name);
            byte[] buffer = new byte[input.available()];
            input.read(buffer);
            input.close();
            string = new String(buffer, "utf-8");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return string;
    }

    // 获取Raw下的资源输入流
    public static InputStream getRawResource( int id){
        return context.getResources().openRawResource(id);
    }

    // 获取xml中的Integer数组
    public static int[] getIntegersResource(int id){
        return context.getResources().getIntArray(id);
    }

    // 获取Drawable
    public static Drawable getDrawable( int id){
        return context.getResources().getDrawable(id);
    }

    // 获取Animations
    public static Animation getAnimation( int id){
        return AnimationUtils.loadAnimation(context,id);
    }

}
