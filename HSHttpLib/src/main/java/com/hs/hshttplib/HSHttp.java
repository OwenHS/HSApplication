package com.hs.hshttplib;

import android.util.Log;

import com.hs.hshttplib.core.HSAsyncTask;
import com.hs.hshttplib.core.HSCachedTask;
import com.hs.hshttplib.download.FileProxyHttpCallBack;
import com.hs.hshttplib.download.FileProxyHttpCallBack.FileFinishCallBack;
import com.hs.hshttplib.download.I_FileLoader;
import com.hs.hshttplib.download.SimpleDownloader;
import com.hs.hshttplib.util.FileUtils;
import com.hs.hshttplib.util.StringUtils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.net.ssl.HttpsURLConnection;

/**********************************************************
 * @文件名称：HSHttp.java
 * @文件作者：huangshuo
 * @创建时间：2015-5-18 上午10:3o6:46
 * @文件描述：Http模块
 * @修改历史：2015-5-18创建初始版本
 **********************************************************/
public class HSHttp {

    private HttpConfig httpConfig;
//	private I_FileLoader downloader;

    private ConcurrentHashMap<String, I_FileLoader> fileDownLoaderMap;

    private enum Method {
        UNKNOW, GET, POST, POSTJSON, POSTFILE
    }

    public HSAsyncTask.SerialExecutor uploadExecutor = new HSAsyncTask.SerialExecutor();

    public HSHttp() {
        this(null);
    }

    public HSHttp(HttpConfig config) {
        if (config == null) {
            httpConfig = new HttpConfig();
        } else {
            httpConfig = config;
        }
    }

    public HttpConfig getHttpConfig() {
        return httpConfig;
    }

    /**
     * 自定义配置
     *
     * @param httpConfig
     */
    public void setHttpConfig(HttpConfig httpConfig) {
        this.httpConfig = httpConfig;
    }

    public void get(String url, HttpCallBack callBack) {
        get(url, null, callBack);
    }

    public void get(String url, HttpParams params, HttpCallBack callBack) {
        VollyTask.setDefaultExecutor(VollyTask.mLruSerialExecutor);
        if (params != null) {
            StringBuilder builder = new StringBuilder(url);
            builder.append("?").append(params.toString());
            url = builder.toString();
        }
        // get的参数拼接到了url中，所以httpparams传null
        new VollyTask(Method.GET, url, null, null, callBack).execute();
    }

    /**
     * 使用HttpURLConnection方式发起post请求
     *
     * @param url      地址
     * @param params   参数集
     * @param callback 请求中的回调方法
     */
    public void post(String url, HttpParams params, HttpCallBack callback) {
        VollyTask.setDefaultExecutor(VollyTask.mLruSerialExecutor);
        if (params != null) {
            new VollyTask(Method.POST, url, params, null, callback).execute();
        } else {
            new VollyTask(Method.GET, url, null, null, callback).execute();
        }
    }

    public void postJson(String url, HttpParams params, JSONObject object, HttpCallBack callback) {
        VollyTask.setDefaultExecutor(VollyTask.mLruSerialExecutor);
        new VollyTask(Method.POSTJSON, url, params, object, callback).execute();
    }

    /**
     * 文件下载
     *
     * @param url      地址
     * @param savePath 保存位置
     * @param callback
     */
    public void download(String url, String savePath, HttpCallBack callback) {
        if (fileDownLoaderMap != null && fileDownLoaderMap.containsKey(url)) {
            if (!fileDownLoaderMap.get(url).isStop()) {
                Log.d("owen", "在运行中，不能继续下载");
                //防止重复下载
                return;
            }
        }
        download(url, savePath, new SimpleDownloader(httpConfig, new FileProxyHttpCallBack(url, callback, new FileFinishCallBack() {

            @Override
            public void removeLoader(String url) {
                if (fileDownLoaderMap != null) {
                    Log.d("owen", "remove 成功");
                    fileDownLoaderMap.remove(url);
                }
            }
        })));
    }

    /**
     * 多文件上传
     *
     * @param url      上传文件的地址
     * @param params   上传文件的参数
     * @param callback 上传文件的回调
     */
    public void uploadFile(String url, String fileKey, List<String> adds, HttpParams params, HttpCallBack callback) {
        //上传文件的单独线程池，防止后台上传文件造成其他请求的阻塞。
        VollyTask.setDefaultExecutor(uploadExecutor);

        for(String path : adds){
            File saveFile;
            try {
                if (path != null) {
                    saveFile = new File(path);
                    if (!saveFile.exists()) {
                        saveFile.createNewFile();
                    }
                    params.put(fileKey,saveFile);
                    new VollyTask(Method.POSTFILE, url, params, null, callback).execute();
                } else {
                    throw new RuntimeException("save file can not create");
                }
            } catch (Exception e) {
                throw new RuntimeException("save file can not create");
            }
        }



    }

    /**
     * 自定义文件下载器下载
     *
     * @param url        地址
     * @param savePath   保存位置
     * @param downloader 下载器
     */
    private void download(String url, String savePath, I_FileLoader downloader) {
        File saveFile;
        try {
            if (savePath != null) {
                saveFile = new File(savePath);
                if (!saveFile.exists()) {
                    saveFile.createNewFile();
                }
            } else {
                throw new RuntimeException("save file can not create");
            }
        } catch (Exception e) {
            throw new RuntimeException("save file can not create");
        }

        if (fileDownLoaderMap == null) {
            fileDownLoaderMap = new ConcurrentHashMap<String, I_FileLoader>();
        }

        fileDownLoaderMap.put(url, downloader);
        httpConfig.savePath = saveFile;
        downloader.doDownload(url, true);
    }

    /**
     * 暂停下载
     */
    public void stopDownload(String url) {
        if (fileDownLoaderMap.get(url) != null) {
            fileDownLoaderMap.get(url).stop();
        }
    }

    /**
     * 是否已经停止下载
     */
    public boolean isStopDownload(String url) {
        if (fileDownLoaderMap.get(url) != null) {
            return fileDownLoaderMap.get(url).isStop();
        } else {
            return false;
        }
    }

    /**
     * 移除一份磁盘缓存
     *
     * @param uri    接口地址
     * @param params http请求时的参数，如果没有则传null
     */
    public void removeDiskCache(String uri, HttpParams params) {
        VollyTask.remove(uri + params); // post请求时的key
        if (params != null) {
            VollyTask.remove(uri + "?" + params + "null"); // get请求时的key
        }
    }

    /**
     * 清空缓存(本操作是异步处理，不会卡顿UI)
     */
    public void removeAllDiskCache() {
        VollyTask.cleanCacheFiles(HttpConfig.CACHEPATH);
    }

    /**
     * 读取一份缓存数据，不考虑缓存是否已过期
     *
     * @return 如果不存在，则为null
     */
    public String getCache(String uri, HttpParams params) {
        String cache = null;
        if (params == null) {
            cache = VollyTask.getCache(HttpConfig.CACHEPATH, uri + params);
        } else {
            cache = VollyTask.getCache(HttpConfig.CACHEPATH, uri + "?" + params + "null");
        }
        return cache;
    }

    public class VollyTask extends HSCachedTask<String, Object, String> {
        private String responseMsg = "";
        private int responseCode = -1;

        /**
         * 编码格式
         */
        private String charsetName;

        /**
         * 请求方式
         */
        private Method requestMethod;
        /**
         * 请求地址
         */
        private String uri;
        /**
         * post参数集
         */
        private HttpParams params;
        /**
         * 回调函数
         */
        private HttpCallBack callBack;
        /**
         * post object数据
         */
        private JSONObject object;

        public VollyTask(String cachePath, String key, long cacheTime) {
            super(cachePath, key, cacheTime);
        }

        public VollyTask(Method method, String uri, HttpParams params, JSONObject object, HttpCallBack callBack) {
            super(HttpConfig.CACHEPATH, uri, httpConfig.cacheTime);

            this.requestMethod = method;
            this.uri = uri;
            this.params = params;
            this.callBack = callBack;
            this.object = object;
            this.charsetName = httpConfig.httpHeader.get("Charset");
            if (StringUtils.isEmpty(charsetName)) {
                this.charsetName = "UTF-8";
            }

            String cookie = httpConfig.getCookieString();
            Log.d("Cookie", "url = " + uri + "VollyTask put config cookie ==>" + cookie + " to HttpHeader ");
            if (!StringUtils.isEmpty(cookie)) {
                HSHttp.this.httpConfig.httpHeader.put("cookie", cookie);
            } else {
                HSHttp.this.httpConfig.httpHeader.put("cookie", null);
            }

            if (httpConfig.useDelayCache) {
                setDelayCacheTime(httpConfig.delayTime);
            }
        }

        @Override
        protected void onPreExecuteSafely() throws Exception {
            super.onPreExecuteSafely();
            callBack.onPreStart();
        }

        @Override
        protected String doConnectNetwork(String... uris) throws Exception {
            InputStream input = null;
            BufferedReader reader = null;
            StringBuilder respond = new StringBuilder();
            try {
                HttpURLConnection conn = openConnection(requestMethod, uri, params, object, charsetName);
                // 响应码描述
                responseMsg = conn.getResponseMessage();
                // 响应码的值
                responseCode = conn.getResponseCode();

                input = conn.getInputStream();
                // 获取所有响应头字段
                httpConfig.respondHeader = conn.getHeaderFields();
                String mCookie = conn.getHeaderField("set-cookie");
                if (mCookie != null) {
                    httpConfig.setCookieString(mCookie);
                }
                callBack.onHttpConnection(conn);
                Log.d("Cookie", "url = " + uri + "   doConnectNetwork ---->" + mCookie);

                reader = new BufferedReader(new InputStreamReader(input));
                int len = 0;
                char[] buf = new char[1024];
                while ((len = reader.read(buf)) != -1) {
                    respond.append(buf, 0, len);
                }
                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                FileUtils.closeIO(input, reader);
            }
            return respond.toString();
        }

        @Override
        protected void onPostExecuteSafely(String result, Exception e) throws Exception {
            super.onPostExecuteSafely(result, e);
            callBack.respondCode = responseCode;

            if (e == null) {
                // doInBackground没有报错
                if (resFromCache) {
                    // 从缓存中取值
                    callBack.onSuccessFromCache(responseCode, result);
                } else {
                    callBack.onSuccess(responseCode, result);
                }
            } else {
                callBack.onFailure(e, -1, e.toString());
            }
            callBack.onFinish();
        }
    }

    private HttpURLConnection openConnection(Method requestMethod, String uri, HttpParams params, JSONObject object,
                                             String charsetName) throws IOException {
        URL url = new URL(uri);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setConnectTimeout(httpConfig.timeOut);
        connection.setReadTimeout(httpConfig.timeOut);
        // 有自己的缓存，不使用http的缓存
        connection.setUseCaches(false);
        // 设置容许输出,流的输入,不设置,connection.getInputStream没有数据
        connection.setDoInput(true);
        connection.setRequestProperty("Accept", "application/json");
        switch (requestMethod) {
            case GET:
                connection.setRequestMethod("GET");
                break;
            case POST:
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setInstanceFollowRedirects(true);
                connection.setRequestProperty("connection", "Keep-Alive");
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                break;
            case POSTJSON:
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setInstanceFollowRedirects(true);
                connection.setRequestProperty("connection", "Keep-Alive");
                connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                break;
            case POSTFILE:
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setInstanceFollowRedirects(true);
                connection.setRequestProperty("connection", "Keep-Alive");
                connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=---------7d4a6d158c9");
                break;
            default:
                new IllegalStateException("unsupported http request method");
                break;
        }

        for (Map.Entry<String, String> entry : httpConfig.httpHeader.entrySet()) {
            connection.setRequestProperty(entry.getKey(), entry.getValue());
        }

        if (requestMethod == HSHttp.Method.POST && params != null) {
            DataOutputStream out = null;
            String string = params.toString();

            try {
                out = new DataOutputStream(connection.getOutputStream());
                out.write(string.getBytes());
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                FileUtils.closeIO(new Closeable[]{out});
            }
        }

        if (requestMethod == Method.POSTFILE && params != null) {
            DataInputStream in = null;
            DataOutputStream out = null;
            try {
                out = new DataOutputStream(connection.getOutputStream());
                // 定义最后数据分隔线
                byte[] end_data = ("\r\n--" + HttpConfig.BOUNDARY + "--\r\n").getBytes(charsetName);
                // 上传参数
                StringBuilder sb = new StringBuilder();

                if (params.urlParams.size() == 0) {
                    sb.append("{}");
                    out.write(sb.toString().getBytes(charsetName));
                    out.write("\r\n".getBytes(charsetName));
                    sb.delete(0, sb.length());
                }

                for (Map.Entry<String, String> entry : params.urlParams.entrySet()) {
                    sb.append("--");
                    sb.append(HttpConfig.BOUNDARY);
                    sb.append("\r\nContent-Disposition: form-data; name=\"");
                    sb.append(entry.getKey());
                    sb.append("\"\r\n\r\n");
                    sb.append(entry.getValue());
                    out.write(sb.toString().getBytes(charsetName));
                    out.write("\r\n".getBytes(charsetName));
                    sb.delete(0, sb.length());
                }

                // 上传文件
                for (Map.Entry<String, HttpParams.FileWrapper> entry : params.fileWraps.entrySet()) {
                    sb.append("--")
                            .append(HttpConfig.BOUNDARY)
                            .append("\r\nContent-Disposition: form-data;name=\"")
                            //name是文件入参的参数名
                            .append(entry.getKey())
                            .append("\";filename=\"")
                            .append(entry.getValue().fileName)
                            .append("\"\r\nContent-Type:application/octet-stream\r\n\r\n");
                    byte[] data = sb.toString().getBytes(charsetName);
                    out.write(data);
                    in = new DataInputStream(entry.getValue().inputStream);
                    int bytes = 0;
                    byte[] buf = new byte[1024];
                    while ((bytes = in.read(buf)) != -1) {
                        out.write(buf, 0, bytes);
                    }
                    // 多个文件时，二个文件之间加入这个
                    out.write("\r\n".getBytes(charsetName));
                    sb.delete(0, sb.length());
                }
                out.write(end_data);
                out.flush();
            } finally {
                FileUtils.closeIO(out);
            }
        }

        if (requestMethod == Method.POSTJSON) {
            DataOutputStream out = null;
            String str = null;
            if (object == null) {
                str = params.toJsonString();
            } else {
                str = object.toString();
            }

            try {
                out = new DataOutputStream(connection.getOutputStream());
                out.write(str.getBytes(charsetName));
                out.flush();
            } finally {
                FileUtils.closeIO(out);
            }
        }

        // use caller-provided custom SslSocketFactory, if any, for HTTPS
        if ("https".equals(url.getProtocol()) && httpConfig.sslSocketFactory != null) {
            ((HttpsURLConnection) connection).setSSLSocketFactory(httpConfig.sslSocketFactory);
        }
        return connection;

    }

}

// POST /b.php HTTP/1.1
// Accept: */*
// Accept-Language: zh-cn
// Content-Type: multipart/form-data; boundary=---------------------------40612316912668
// User-Agent: Mozilla/5.0 (Windows NT 6.1; rv:9.0) Gecko/20100101 Firefox/9.0
// Host: 127.0.0.1
// Content-Length: 570
// Connection: Keep-Alive
// Cache-Control: no-cache
// Cookie:
//
// -----------------------------40612316912668
// Content-Disposition: form-data; name="a"
//
// txt11
// -----------------------------40612316912668
// Content-Disposition: form-data; name="b"
//
// txt22
// -----------------------------40612316912668
// Content-Disposition: form-data; name="c"; filename="11 - 副本.txt"
// Content-Type: text/plain
//
// aaaa
// abbb
// cdd
// ccc
// -----------------------------40612316912668
// Content-Disposition: form-data; name="d"; filename="11.txt"
// Content-Type: text/plain
//
// sfdsfsafsafsd
// sdfsdaf
// asfddsaf
// -----------------------------40612316912668--/