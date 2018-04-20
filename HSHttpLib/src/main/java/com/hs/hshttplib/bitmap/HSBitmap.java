package com.hs.hshttplib.bitmap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.hs.hshttplib.bitmap.helper.BitmapCreate;
import com.hs.hshttplib.bitmap.helper.BitmapHelper;
import com.hs.hshttplib.bitmap.helper.BitmapMemoryCache;
import com.hs.hshttplib.bitmap.helper.DensityUtils;
import com.hs.hshttplib.bitmap.helper.DiskCache;
import com.hs.hshttplib.bitmap.helper.EmptyUtils;
import com.hs.hshttplib.core.HSAsyncTask;
import com.hs.hshttplib.core.SimpleSafeAsyncTask;
import com.hs.hshttplib.util.CipherUtils;
import com.hs.hshttplib.util.FileUtils;
import com.hs.hshttplib.util.SystemTool;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by huangshuo on 16/7/11.
 */
public class HSBitmap {

    public static final String TAG = HSBitmap.class.getSimpleName();

    private static HSBitmap instance;
    private final BitmapConfig config;
    private BitmapCallback callback;

    private BitmapMemoryCache memoryCache;
    private DiskCache diskCache;

    /**
     * 存放所有正在下载或者等待下载的任务
     */
    private final Set<BitmapWorkerTask> taskCollection;

    private HSBitmap(BitmapConfig bitmapConfig) {
        config = bitmapConfig;
        taskCollection = new HashSet<BitmapWorkerTask>();

        memoryCache = new BitmapMemoryCache(config.memoryCacheSize);
        diskCache = new DiskCache(BitmapConfig.CACHEPATH, config.memoryCacheSize * 8, config.isDEBUG);

        setCallback(null);
    }

    public static HSBitmap create() {
        return create(new BitmapConfig());
    }

    public synchronized static HSBitmap create(BitmapConfig bimapConfig) {
        if (instance == null) {
            instance = new HSBitmap(bimapConfig);
        }
        return instance;
    }

    /**
     * 加载过程中图片不会闪烁加载方法(不可用在ListView中)
     *
     * @param imageView 要显示图片的控件(ImageView设置src，普通View设置bg)
     * @param imageUrl  图片的URL
     */
    public void displayNotTwink(View imageView, String imageUrl) {
        displayNotTwink(imageView, imageUrl, 0, 0);
    }

    /**
     * 加载过程中图片不会闪烁加载方法(不可用在ListView中)
     *
     * @param imageView 要显示图片的控件(ImageView设置src，普通View设置bg)
     * @param imageUrl  图片的URL
     * @param width     图片的宽
     * @param height    图片的高
     */
    public void displayNotTwink(View imageView, String imageUrl, int width, int height) {
        display(imageView, imageUrl, width, height);
    }

    /**
     * 使用默认配置加载网络图片
     *
     * @param imageView 要显示图片的控件(ImageView设置src，普通View设置bg)
     * @param imageUrl  图片的URL
     */
    public void display(View imageView, String imageUrl) {
        display(imageView, imageUrl, 0, 0);
    }

    /**
     * 显示网络图片
     *
     * @param imageView 要显示图片的控件
     * @param imageUrl  图片地址
     * @param width     宽
     * @param height    高
     */
    public void display(View imageView, String imageUrl, int width, int height) {
        display(imageView, imageUrl, width, height, null);
    }

    /**
     * 显示网络图片
     *
     * @param imageView  要显示图片的控件
     * @param imageUrl   图片地址
     * @param loadBitmap 载入过程中显示的图片
     */
    public void display(View imageView, String imageUrl, Bitmap loadBitmap) {
        display(imageView, imageUrl, loadBitmap, 0, 0);
    }

    /**
     * 显示网络图片
     *
     * @param imageView 要显示图片的控件
     * @param imageUrl  图片地址
     * @param loadingId 载入过程中显示的图片
     */
    public void display(View imageView, String imageUrl, int loadingId) {
        display(imageView, imageUrl, 0, 0, imageView.getResources().getDrawable(loadingId));
    }

    /**
     * 显示网络图片
     *
     * @param imageView  要显示图片的控件
     * @param imageUrl   图片地址
     * @param loadBitmap 图片载入过程中的显示
     * @param width      图片宽度
     * @param height     图片高度
     */
    public void display(View imageView, String imageUrl, Bitmap loadBitmap, int width, int height) {
        Drawable loadDrawable = null;
        if (loadBitmap != null) {
            loadDrawable = new BitmapDrawable(imageView.getResources(), loadBitmap);
        }
        display(imageView, imageUrl, width, height, loadDrawable);
    }

    /**
     * 显示网络图片
     *
     * @param imageView 要显示图片的控件
     * @param imageUrl  图片地址
     * @param loadingId 图片载入过程中的显示
     * @param width     图片宽度
     * @param height    图片高度
     */
    public void display(View imageView, String imageUrl, int loadingId, int width, int height) {
        display(imageView, imageUrl, width, height, imageView.getResources().getDrawable(loadingId));
    }

    /**
     * 显示网络图片(core)
     *
     * @param imageView  要显示图片的控件
     * @param imageUrl   图片地址
     * @param loadBitmap 图片载入过程中的显示
     * @param width      图片宽度
     * @param height     图片高度
     */
    private void display(View imageView, String imageUrl, int width, int height, Drawable loadBitmap) {
        if (imageView == null) {
            Log.d(TAG, "要显示必须有imageview");
            callFailure("imageview is null");
            return;
        }
        if (EmptyUtils.isEmpty(imageUrl)) {
            callFailure("imge url is empty");
            return;
        }

        Log.e(TAG, "display 显示传入的 url = " + imageUrl + " imageView = " + imageView);

        if (width == 0 || height == 0) {
            width = DensityUtils.getScreenW(imageView.getContext());
            height = DensityUtils.getScreenH(imageView.getContext());
            Log.e(TAG, "display 没有传入长高，所以使用屏幕高度 width = " + width + " height = " + height);
        } else {
            Log.e(TAG, "display 传入了长高 width = " + width + " height = " + height);
        }


        boolean notTwink = false;

        config.cxt = (Activity) imageView.getContext();
        config.setDefaultHeight(height);
        config.setDefaultWidth(width);

        cancel(imageView);

        //先判断本地是否有图片
        Bitmap bitmap = preDoInBg(imageView, imageUrl, loadBitmap, notTwink);
        if (bitmap != null) {
            if (imageUrl.equals(imageView.getTag())) {
                setViewImage(imageView, bitmap);
            }
            return;
        }

        //本地没有，再到网络下图片
        BitmapWorkerTask task = new BitmapWorkerTask(imageView, imageUrl, width, height, loadBitmap, notTwink);
        taskCollection.add(task);
        BitmapWorkerTask.setDefaultExecutor(BitmapWorkerTask.mSerialExecutor);
        task.execute();
    }

    private Bitmap preDoInBg(View imageView, String imageUrl, Drawable loadBitmap, boolean notTwink) {

        Log.e(TAG, "preDoInBg 如果内存中含有bitmap的对象，也没有必要去到task请求中去了");

        imageView.setTag(imageUrl);

        Bitmap keyBitmap = getBitmapFromMC(imageUrl);

        if (keyBitmap == null) {
            if (!notTwink && imageUrl.startsWith("http")) {
                Log.e(TAG, "preDoInBg 设置默认图片");
                setViewImage(imageView, loadBitmap);
            }
        }
        return keyBitmap;
    }

    /**
     * 取消view在队列中的下载请求
     *
     * @param view
     */
    public synchronized void cancel(View view) {
        for (BitmapWorkerTask task : taskCollection) {
            if (task.imageView.equals(view)) {
                Log.e(TAG, "cancel cancel了task view = " + view);
                taskCollection.remove(task);
                break;
            }
        }
    }

    public synchronized void cancelAll() {
        for (BitmapWorkerTask task : taskCollection) {
            task.cancelTask();
            taskCollection.remove(task);
        }

    }

    /*****************异步获取bitmap并设置image的任务类*****************/

    private class BitmapWorkerTask extends SimpleSafeAsyncTask<Bitmap> {
        private View imageView;
        private String imageUrl;
        private int width;
        private int height;
        private Drawable drawable;
        private boolean notTwink;
        private Bitmap keyBitmap;

        public BitmapWorkerTask(View imageView, String imageUrl, int width, int height, Drawable drawable,
                                boolean notTwink) {
            this.imageView = imageView;
            this.imageUrl = imageUrl;
            this.width = width;
            this.height = height;
            this.drawable = drawable;
            this.notTwink = notTwink;
        }

        public boolean cancelTask() {
            return this.cancel(true);
        }

        @Override
        protected void onPreExecuteSafely() throws Exception {
            super.onPreExecuteSafely();
            Log.e(TAG, "开始 onPreExecuteSafely--->" + System.currentTimeMillis());

            config.downloader.setImageCallback(callback);

            if (callback != null) {
                callback.onPreLoad(imageView);
            }

            /**将imageView打上tag方便查找*/

            imageView.setTag(imageUrl);

            if (!notTwink && imageUrl.startsWith("http")) {
                setViewImage(imageView, drawable);
            }

            Log.e(TAG, "结束 onPreExecuteSafely--->" + System.currentTimeMillis());
        }

        @Override
        public Bitmap doInBackground() {
            Log.e(TAG, "/********************开始 doInBackground--->" + System.currentTimeMillis() + "*****************/");
            keyBitmap = getBitmapFromDC(imageUrl, width, height);
            if (keyBitmap == null) {
                keyBitmap = getBitmapFromNet(imageUrl, width, height);
                if (keyBitmap != null) {
                    putBitmapToMC(imageUrl, keyBitmap);
                }
            } else {
                putBitmapToMC(imageUrl, keyBitmap);
            }

            Log.e(TAG, "/********************结束 doInBackground--->" + System.currentTimeMillis() + "*****************/");
            return keyBitmap;
        }

        @Override
        protected void onPostExecuteSafely(Bitmap result, Exception e) throws Exception {
            super.onPostExecuteSafely(result, e);
            if (result != null) {
                if (imageUrl.equals(imageView.getTag())) {
                    setViewImage(imageView, result);
                    if (callback != null) {
                        Log.e(TAG, "成功回调出去");
                        callback.onSuccess(imageView, result);
                    }
                }
            } else {
                if (callback != null) {
                    e = (e == null) ? new RuntimeException("bitmap not found") : e;
                    callback.onFailure(e);
                }
            }
            if (callback != null) {
                callback.onFinish(imageView);
            }
            keyBitmap = null;
        }
    }

    @SuppressLint("NewApi")
    private void setViewImage(View view, Bitmap background) {
        if (view instanceof ImageView) {
            Log.e(TAG, "setImageBitmap 开始--->" + System.currentTimeMillis());
            ((ImageView) view).setImageBitmap(background);
            Log.e(TAG, "setImageBitmap 结束--->" + System.currentTimeMillis());
        } else {
            if (SystemTool.getSDKVersion() >= 16) {
                view.setBackground(new BitmapDrawable(view.getResources(), background));
            } else {
                view.setBackgroundDrawable(new BitmapDrawable(view.getResources(), background));
            }
        }
    }

    // 设置下载时候的背景图片
    @SuppressLint("NewApi")
    public void setViewImage(View imageView, Drawable background) {
        if (imageView instanceof ImageView) {
            ((ImageView) imageView).setImageDrawable(background);
        } else {
            if (SystemTool.getSDKVersion() >= 16) {
                imageView.setBackground(background);
            } else {
                imageView.setBackgroundDrawable(background);
            }
        }
    }

    /************************************************************/

    /***********************内存，硬盘，网络获取图片***************************/
    // 获取缓存图片
    private Bitmap getBitmapFromCache(String key) {
        return getBitmapFromCache(key, null, 0, 0);
    }


    private Bitmap getBitmapFromCache(String key, View view, int width, int height) {
        Log.e(TAG, "开始 getBitmapFromCache--->" + System.currentTimeMillis());
        Bitmap bitmap = getBitmapFromMC(key);
        if (bitmap == null) {
            Log.e(TAG, "getBitmapFromCache 不在内存中");
            if (view != null) {
                //去掉原先的bitmap
                setViewImage(view, bitmap);
            }
            bitmap = getBitmapFromDC(key, width, height);

            if (bitmap == null) {
                Log.e(TAG, "getBitmapFromCache 不在磁盘中");
            }
        }
        Log.e(TAG, "结束 getBitmapFromCache--->" + System.currentTimeMillis());
        return bitmap;
    }

    // 从内存获取
    private Bitmap getBitmapFromMC(String key) {
        Log.e(TAG, "从内存获取  开始--->" + System.currentTimeMillis());
        Bitmap bitmap = memoryCache.get(CipherUtils.md5(key));
        Log.e(TAG, "从内存获取  结束--->" + System.currentTimeMillis());
        return bitmap;
    }

    // 从磁盘获取
    private Bitmap getBitmapFromDC(String key, int width, int height) {
        Log.e(TAG, "从磁盘获取  开始--->" + System.currentTimeMillis());
        Bitmap bitmap = diskCache.get(CipherUtils.md5(key), width, height);
        Log.e(TAG, "从磁盘获取 结束--->" + System.currentTimeMillis());
        return bitmap;
    }

    /**
     * 从网络读取Bitmap<br>
     * <p>
     * <b>注意：</b>这里有网络访问，应该放在线程中调用<br>
     * <b>注意：</b>如果宽高参数为0，显示图片默认大小，此时有可能会造成OOM<br>
     *
     * @param imageUrl 图片地址Url
     * @param width    图片期望宽度，0为默认大小
     * @param height   图片期望高度，0为默认大小
     * @return
     */
    public Bitmap getBitmapFromNet(String imageUrl, int width, int height) {
        Bitmap bitmap;
        if (imageUrl.trim().toLowerCase().startsWith("http")) {
            Log.e(TAG, "从网络获取 开始--->" + System.currentTimeMillis());
            config.downloader.loadImage(imageUrl, diskCache);
            bitmap = getBitmapFromDC(imageUrl, width, height);
            Log.e(TAG, "从网络获取 结束--->" + System.currentTimeMillis());
        } else {
            Log.e(TAG, "从本地获取 开始--->" + System.currentTimeMillis());
            bitmap = BitmapCreate.bitmapFromFile(imageUrl, width, height);
            Log.e(TAG, "从本地获取 结束--->" + System.currentTimeMillis());
        }


        return bitmap;
    }

    /**
     * 添加bitmap到内存缓存
     *
     * @param k 缓存的key
     * @param v 要添加的bitmap
     */
    public void putBitmapToMC(String k, Bitmap v) {
        Log.e(TAG, "存入内存缓存");
        memoryCache.put(CipherUtils.md5(k), v);
    }

    /**
     * 加入磁盘缓存
     *
     * @param k 图片路径
     * @param v 要保存的Bitmap
     */
    public void putBitmapToDC(String k, Bitmap v) {
        Log.e(TAG, "存入磁盘缓存");
        diskCache.put(CipherUtils.md5(k), v);
    }

    /**
     * 移除一个指定的图片缓存
     *
     * @param key
     */
    public void removeCache(String key) {
        key = CipherUtils.md5(key);
        memoryCache.remove(key);
        File dir = FileUtils.getSaveFolder(BitmapConfig.CACHEPATH);
        File file = new File(dir, BitmapConfig.CACHE_FILENAME_PREFIX + key);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 清空图片缓存
     */
    public void removeCacheAll() {
        memoryCache.removeAll();
        diskCache.clearCache();
        // File dir = FileUtils.getSaveFolder(BitmapConfig.CACHEPATH);
        // for (File f : dir.listFiles()) {
        // f.delete();
        // }
    }

    /***************************************************************/
    /**
     * 错误后调用回调函数
     *
     * @param errorInfo
     */
    private void callFailure(String errorInfo) {
        if (callback != null) {
            callback.onFailure(new RuntimeException(errorInfo));
        }
    }

    public BitmapCallback getCallback() {
        return callback;
    }

    public void setCallback(BitmapCallback callback) {
        this.callback = callback;
    }

    /**
     * 保存一张图片到本地
     *
     * @param url  图片地址
     * @param path 在本地的绝对路径
     */
    public void saveImage(String url, String path) {
        saveImage(url, path, 0, 0, null);
    }

    /**
     * 保存一张图片到本地
     *
     * @param url  图片地址
     * @param path 在本地的绝对路径
     * @param cb   保存过程回调
     */
    public void saveImage(String url, String path, BitmapCallback cb) {
        saveImage(url, path, 0, 0, cb);
    }

    /**
     * 保存一张图片到本地
     *
     * @param url  图片地址
     * @param path 在本地的绝对路径
     * @param reqW 图片的宽度（0表示默认）
     * @param reqH 图片的高度（0表示默认）
     * @param cb   保存过程回调
     */
    public void saveImage(final String url, final String path, final int reqW, final int reqH, final BitmapCallback cb) {
        if (cb != null)
            cb.onPreLoad(null);

        Bitmap bmp = getBitmapFromCache(url);
        if (bmp == null) {
            HSAsyncTask.setOnFinishedListener(new HSAsyncTask.OnFinishedListener() {
                @Override
                public void onPostExecute() {
                    if (cb != null) {
                        cb.onSuccess(null, null);
                        cb.onFinish(null);
                    }
                }
            });
            HSAsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    Bitmap bmp = getBitmapFromNet(url, reqW, reqH);
                    if (bmp == null && cb != null && config.cxt != null) {
                        config.cxt.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                cb.onFailure(new RuntimeException("download error"));
                            }
                        });
                    } else {
                        FileUtils.bitmapToFile(bmp, path);
                    }
                }
            });
        } else {
            bmp = BitmapHelper.scaleWithWH(bmp, reqW, reqH);
            boolean success = FileUtils.bitmapToFile(bmp, path);
            if (cb != null) {
                if (success) {
                    cb.onSuccess(null, null);
                } else {
                    cb.onFailure(new RuntimeException("save error"));
                }
                cb.onFinish(null);
            }
        }
    }
}
