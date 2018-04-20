package com.hs.hshttplib.bitmap.helper;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;

import com.hs.hshttplib.util.SystemTool;

/**********************************************************
 * @文件名称：BitmapMemoryCache.java
 * @文件作者：Administrator
 * @创建时间：2015-6-5 下午1:40:45
 * @文件描述：内存放置bitmap，使用lru算法，动态去除不用的bitmap
 * @修改历史：2015-6-5创建初始版本
 **********************************************************/
public class BitmapMemoryCache
{

    private MemoryLruCache<String, Bitmap> cache;

    public BitmapMemoryCache()
    {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        init(maxMemory / 4);
    }

    public BitmapMemoryCache(long maxMemory)
    {
        init(maxMemory / 4);
    }

    /**
     * @param maxSize
     *            使用内存缓存的内存大小，单位：kb
     */
    @SuppressLint("NewApi")
    private void init(long maxSize)
    {
        cache = new MemoryLruCache<String, Bitmap>(maxSize)
        {

            @Override
            protected int sizeOf(String key, Bitmap value)
            {
                super.sizeOf(key, value);

                if (SystemTool.getSDKVersion() >= 12)
                {
                    return value.getByteCount();
                }
                else
                {
                    return value.getRowBytes() * value.getHeight();
                }
            }

        };
    }

    public void put(String key, Bitmap bitmap)
    {
        if (this.get(key) == null)
        {
            cache.put(key, bitmap);
        }
    }

    public Bitmap get(String key)
    {
        return cache.get(key);
    }

    public void remove(String key)
    {
        cache.remove(key);
    }

    public void removeAll()
    {
        cache.removeAll();
    }

}
