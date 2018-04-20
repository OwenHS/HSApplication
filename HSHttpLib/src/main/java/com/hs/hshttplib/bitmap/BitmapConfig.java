package com.hs.hshttplib.bitmap;


import android.app.Activity;

import com.hs.hshttplib.bitmap.helper.BitmapCreate;
import com.hs.hshttplib.bitmap.helper.BitmapDownLoader;

/**********************************************************
 * @文件名称：BitmapConfig.java
 * @文件作者：Administrator
 * @创建时间：2015-6-2 上午9:57:24
 * @文件描述：Bitmap配置类
 * @修改历史：2015-6-2创建初始版本
 **********************************************************/
public class BitmapConfig {
    public boolean isDEBUG = true;
    public Activity cxt;

    public long memoryCacheSize;

    //缓存文件名称前缀
    public static String CACHE_FILENAME_PREFIX = "HSLibrary_";
    //图片缓存路径
    public static String CACHEPATH = "HSLibrary/image";


    public I_ImageDownLoader downloader;

    public BitmapConfig() {
        memoryCacheSize = Runtime.getRuntime().maxMemory();
        downloader = new BitmapDownLoader(this);
    }

    public int getDefaultHeight() {
        return BitmapCreate.DEFAULT_H;
    }

    public int getDefaultWidth() {
        return BitmapCreate.DEFAULT_W;
    }

    public void setDefaultHeight(int height) {
        if (height > 0)
            BitmapCreate.DEFAULT_W = height;
    }

    public void setDefaultWidth(int width) {
        if (width > 0)
            BitmapCreate.DEFAULT_W = width;
    }

}

