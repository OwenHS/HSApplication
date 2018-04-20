package com.hs.hshttplib.bitmap;

import com.hs.hshttplib.bitmap.helper.DiskCache;

/**********************************************************
 * @文件名称：I_ImageDownLoader.java
 * @文件作者：Administrator
 * @创建时间：2015-6-5 下午3:19:44
 * @文件描述：图片下载器
 * @修改历史：2015-6-5创建初始版本
 **********************************************************/
public interface I_ImageDownLoader
{
    void setImageCallback(BitmapCallback callback);

    void loadImage(String uri, DiskCache diskCache);
}

