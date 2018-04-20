package com.hs.hshttplib.bitmap.helper;

import android.app.Activity;
import android.util.Log;

import com.hs.hshttplib.bitmap.BitmapCallback;
import com.hs.hshttplib.bitmap.BitmapConfig;
import com.hs.hshttplib.bitmap.HSBitmap;
import com.hs.hshttplib.bitmap.I_ImageDownLoader;
import com.hs.hshttplib.util.CipherUtils;
import com.hs.hshttplib.util.FileUtils;

import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**********************************************************
 * @文件名称：BitmapDownLoader.java
 * @文件作者：Administrator
 * @创建时间：2015-6-5 下午3:22:08
 * @文件描述：
 * @修改历史：2015-6-5创建初始版本
 **********************************************************/
public class BitmapDownLoader implements I_ImageDownLoader {
    private BitmapCallback callback;
    private final BitmapConfig config;
    private DiskCache diskCache;

    public BitmapDownLoader(BitmapConfig bitmapConfig) {
        this.config = bitmapConfig;
    }

    @Override
    public void setImageCallback(BitmapCallback callback) {
        this.callback = callback;
    }

    @Override
    public void loadImage(String uri, DiskCache diskCache) {
        this.diskCache = diskCache;
        byte[] data = null;
        if (uri.trim().toLowerCase().startsWith("http")) {
            data = fromNet(uri);
        } else {
            data = fromFile(uri);
        }
    }

    /**
     * 从网络加载图片
     *
     * @param uri
     * @return
     */
    private byte[] fromNet(String uri) {
        Log.d("owen", "下载 开始--->" + System.currentTimeMillis());
        byte[] data = null;
        HttpURLConnection con = null;
        try {
            if (uri.trim().toLowerCase().startsWith("https")) {
                SSLContext sc = SSLContext.getInstance("SSL");

                sc.init(null, new TrustManager[]
                        {new MyTrustManager()}, null);
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                HttpsURLConnection.setDefaultHostnameVerifier(new MyHostnameVerifier());
            }

            URL url = new URL(uri);
            con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(30000);
            con.setReadTimeout(30000);
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.connect();
            Log.d("owen", "下载 结束--->" + System.currentTimeMillis());

            //下载的文件直接存入sd卡中
            diskCache.put(CipherUtils.md5(uri),con.getInputStream());

        } catch (Exception e) {
            failure(e);
        } finally {
            if (con != null) {
                Log.e(HSBitmap.TAG,"con.close");
                con.disconnect();
            }
        }
        return data;
    }

    private byte[] fromFile(String uri) {
        byte[] data = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(uri);
            if (fis != null) {
                data = FileUtils.input2byte(fis);
            }
        } catch (Exception e) {
            failure(e);
        } finally {
            FileUtils.closeIO(fis);
        }
        return data;
    }

    private void failure(final Exception e) {
        if (callback != null) {
            final Activity aty;
            if (config.cxt != null) {
                aty = config.cxt;
                if (aty != null) {
                    aty.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFailure(e);
                        }
                    });
                }
            } else {
//                aty = HSActivityStack.create().topActivity();
            }

        }
    }

    private class MyHostnameVerifier implements HostnameVerifier {

        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    private class MyTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

    }
}
