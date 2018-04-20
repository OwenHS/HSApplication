package com.hs.hshttplib.bitmap.helper;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

import com.hs.hshttplib.bitmap.HSBitmap;
import com.hs.hshttplib.util.FileUtils;

import java.io.InputStream;

/**********************************************************
 * @文件名称：BitmapCreate.java
 * @文件作者：Administrator
 * @创建时间：2015-6-2 上午10:52:44
 * @文件描述：创建bitmap工具类，应该不会再oom
 * @修改历史：2015-6-2创建初始版本
 **********************************************************/
public class BitmapCreate
{
    // 加载出错时，使用默认宽高再次加载
    public static int DEFAULT_H = 40;
    public static int DEFAULT_W = 30;

    /**
     * 获取一个指定大小的bitmap
     *
     * @param res
     *            Resources
     * @param resId
     *            图片ID
     * @param reqWidth
     *            目标宽度
     * @param reqHeight
     *            目标高度
     */
    public static Bitmap bitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight)
    {
        // 通过JNI的形式读取本地图片达到节省内存的目的
        InputStream is = res.openRawResource(resId);
        return bitmapFromStream(is, null, reqWidth, reqHeight);
    }

    /**
     * 获取一个指定大小的bitmap
     *
     * @param data
     *            Bitmap的byte数组
     * @param offset
     *            image从byte数组创建的起始位置
     * @param length
     *            the number of bytes, 从offset处开始的长度
     * @param reqWidth
     *            目标宽度
     * @param reqHeight
     *            目标高度
     */
    public static Bitmap bitmapFromByteArray(byte[] data, int offset, int length, int reqWidth, int reqHeight)
    {
        if (reqHeight == 0 || reqWidth == 0)
        {
            try
            {
                Log.e(HSBitmap.TAG,"bitmapFromByteArray 长宽有的为0，length = "+length);
                return BitmapFactory.decodeByteArray(data, offset, length);
            }
            catch (OutOfMemoryError e)
            {
                reqHeight = DEFAULT_H;
                reqWidth = DEFAULT_W;
            }
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPurgeable = true;
        options.inPreferredConfig = Bitmap.Config.ALPHA_8;
        options.inInputShareable = true;
        BitmapFactory.decodeByteArray(data, offset, length, options);
        options = BitmapHelper.calculateInSampleSize(options, reqWidth, reqHeight);
        Log.e(HSBitmap.TAG,"bitmapFromByteArray 长宽不为0，option.inSampleSize = "+options.inSampleSize);
        return BitmapFactory.decodeByteArray(data, offset, length, options);
    }

    /**
     * 获取一个指定大小的bitmap<br>
     * 实际调用的方法是bitmapFromByteArray(data, 0, data.length, w, h);
     *
     * @param is
     *            从输入流中读取Bitmap
     * @param reqWidth
     *            目标宽度
     * @param reqHeight
     *            目标高度
     */
    public static Bitmap bitmapFromStream(InputStream is, int reqWidth, int reqHeight)
    {
        if (reqHeight == 0 || reqWidth == 0)
        {
            try
            {
                return BitmapFactory.decodeStream(is);
            }
            catch (OutOfMemoryError e)
            {
                reqHeight = DEFAULT_H;
                reqWidth = DEFAULT_W;
            }
        }
        byte[] data = FileUtils.input2byte(is);
        return bitmapFromByteArray(data, 0, data.length, reqWidth, reqHeight);
    }

    /**
     * 获取一个指定大小的bitmap
     *
     * @param is
     *            从输入流中读取Bitmap
     * @param outPadding
     *            If not null, return the padding rect for the bitmap if it
     *            exists, otherwise set padding to [-1,-1,-1,-1]. If no bitmap
     *            is returned (null) then padding is unchanged.
     * @param reqWidth
     *            目标宽度
     * @param reqHeight
     *            目标高度
     */
    public static Bitmap bitmapFromStream(InputStream is, Rect outPadding, int reqWidth, int reqHeight)
    {
        Bitmap bmp = null;
        if (reqHeight == 0 || reqWidth == 0)
        {
            try
            {
                return BitmapFactory.decodeStream(is);
            }
            catch (OutOfMemoryError e)
            {
                reqHeight = DEFAULT_H;
                reqWidth = DEFAULT_W;
            }
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPurgeable = true;
        options.inPreferredConfig = Bitmap.Config.ALPHA_8;
        options.inInputShareable = true;
        BitmapFactory.decodeStream(is, outPadding, options);
        options = BitmapHelper.calculateInSampleSize(options, reqWidth, reqHeight);
        bmp = BitmapFactory.decodeStream(is, outPadding, options);
        return bmp;
    }

    /**
     * 从文件获取bitmap
     * @param pathName  文件路径
     * @param reqWidth	期望的宽度
     * @param reqHeight 期望的高度
     * @return
     */
    public static Bitmap bitmapFromFile(String pathName, int reqWidth, int reqHeight)
    {

        Log.e("owen","reqWidth = "+reqWidth+" reqHeight = "+reqHeight);
        if (reqHeight == 0 || reqWidth == 0)
        {
            try
            {
                return BitmapFactory.decodeFile(pathName);
            }
            catch (OutOfMemoryError e)
            {
                reqHeight = DEFAULT_H;
                reqWidth = DEFAULT_W;
            }
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPurgeable = true;
        options.inPreferredConfig = Bitmap.Config.ALPHA_8;
        options.inInputShareable = true;
        BitmapFactory.decodeFile(pathName, options);
        options = BitmapHelper.calculateInSampleSize(options, reqWidth, reqHeight);
        Log.e("owen","sampleSize = "+options.inSampleSize);
        return BitmapFactory.decodeFile(pathName, options);
    }

}
