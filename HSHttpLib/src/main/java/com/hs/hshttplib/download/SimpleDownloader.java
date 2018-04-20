/**
 *
 */
package com.hs.hshttplib.download;

import android.os.SystemClock;
import android.util.Log;

import com.hs.hshttplib.HttpCallBack;
import com.hs.hshttplib.HttpConfig;
import com.hs.hshttplib.core.HSAsyncTask;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.SyncBasicHttpContext;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

/**********************************************************
 * @文件名称：SimpleDownloader.java
 * @文件作者：Administrator
 * @创建时间：2015-5-29 上午11:24:51
 * @文件描述：下载器
 * @修改历史：2015-5-29创建初始版本
 **********************************************************/
public class SimpleDownloader extends HSAsyncTask<Object, Object, Object> implements I_FileLoader {
    private AbstractHttpClient client;
    private HttpContext context;

    private HttpConfig config;
    private HttpCallBack callBack;
    // 用于处理http的实体
    private final FileEntityHandler mFileEntityHandler = new FileEntityHandler();

    // 下载的路径
    private String targetUrl = null;
    // 是否断点续传
    private boolean isResume = false;
    // 已经重试的次数
    private int executionCount = 0;

    private long time;

    private final static int UPDATE_START = 1;
    private final static int UPDATE_LOADING = 2;
    private final static int UPDATE_FAILURE = 3;
    private final static int UPDATE_SUCCESS = 4;

    public SimpleDownloader(HttpConfig httpConfig, HttpCallBack callBack) {
        this.config = httpConfig;
        this.callBack = callBack;
        initHttpClient();
    }

    /**
     * 初始化httpClient
     * httpParams是一个网络链接配置类，比如设置最大连接数，路由最大连接数，读
     * 取超时时间，连接超时时间，套接字缓冲大小。这里连接数都是10次，超市时间都是10秒，
     * 其实这样本来就是一种网络请求任务的拖慢。
     * 再下面还有个重复次数，竟然是5次，如果网络不好的话有可能要重试5次，这样严重影响了上层的UI交互。
     * 不过有个亮点就是g-zip,Gzip开启以后会将输出的数据进行压缩的处理，这样就会减小通过网络传输的数据量，提高读取的速度。
     */
    private void initHttpClient() {
        BasicHttpParams httpParams = new BasicHttpParams();

        ConnManagerParams.setTimeout(httpParams, config.timeOut);
        ConnManagerParams.setMaxConnectionsPerRoute(httpParams, new ConnPerRouteBean(config.maxConnections));
        ConnManagerParams.setMaxTotalConnections(httpParams, config.maxConnections);

        HttpConnectionParams.setSoTimeout(httpParams, config.timeOut);
        HttpConnectionParams.setConnectionTimeout(httpParams, config.timeOut);
        /*
		 * 决定了是否使用Nagle算法。Nagle算法视图通过最小化发送的分组数量来节省带宽。 当应用程序希望降低网络延迟并提高性能时，它们可以关闭Nagle算法（也就是开启TCP_NODELAY）。
		 * 数据将会更早发送，增加了带宽消耗的成文。这个参数期望得到一个java.lang.Boolean类型的值。如果这个参数没有被设置，那么TCP_NODELAY就会开启（无延迟）。
		 */
        HttpConnectionParams.setTcpNoDelay(httpParams, true);
		/*
		 * 决定了内部套接字缓冲使用的大小，来缓冲数据同时接收/传输HTTP报文。 这个参数期望得到一个java.lang.Integer类型的值。如果这个参数没有被设置，那么HttpClient将会分配8192字节的套接字缓存。
		 */
        HttpConnectionParams.setSocketBufferSize(httpParams, config.socketBuffer);

        HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setUserAgent(httpParams, "KJLibrary");

		/*
		 * Scheme类代表了一个协议模式，比如“http”或“https”同时包含一些协议属性， 比如默认端口，用来为给定协议创建java.net.Socket实例的套接字工厂。
		 * SchemeRegistry类用来维持一组Scheme，当去通过请求URI建立连接时，HttpClient可以从中选择：
		 */
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
        ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(httpParams, schemeRegistry);

        context = new SyncBasicHttpContext(new BasicHttpContext());
        client = new DefaultHttpClient(cm, httpParams);

		/*
		 * HttpRequestInterceptor就是Http请求拦截器。 可用在客户端，在Http消息发出前，对HttpRequest request做些处理。比如加头啊
		 * 也可用在服务器端，在Http到达后，正式处理前，对HttpRequest request做些处理。
		 */
        client.addRequestInterceptor(new HttpRequestInterceptor() {
            @Override
            public void process(HttpRequest request, HttpContext context) {
                if (!request.containsHeader("Accept-Encoding")) {
                    request.addHeader("Accept-Encoding", "gzip");
                }

                for (Entry<String, String> entry : config.httpHeader.entrySet()) {
                    request.addHeader(entry.getKey(), entry.getValue());
                }
            }
        });

        client.addResponseInterceptor(new HttpResponseInterceptor() {
            @Override
            public void process(HttpResponse response, HttpContext context) {
                final HttpEntity entity = response.getEntity();
                if (entity == null) {
                    return;
                }
                final Header encoding = entity.getContentEncoding();
                if (encoding != null) {
                    for (HeaderElement element : encoding.getElements()) {
                        if (element.getName().equalsIgnoreCase("gzip")) {
                            response.setEntity(new InflatingEntity(response.getEntity()));
                            break;
                        }
                    }
                }
            }
        });
        client.setHttpRequestRetryHandler(new RetryHandler(config.maxRetries));
    }

    @Override
    protected void onProgressUpdate(Object... values) {
        int update = Integer.valueOf(String.valueOf(values[0]));
        switch (update) {
            case UPDATE_START:
                if (callBack != null) {
                    callBack.onPreStart();
                }
                break;
            case UPDATE_LOADING:
                if (callBack != null && SystemClock.uptimeMillis() - time >= 100) {
                    time = SystemClock.uptimeMillis();
                    callBack.onLoading(Long.valueOf(String.valueOf(values[1])), Long.valueOf(String.valueOf(values[2])));
                }
                break;
            case UPDATE_FAILURE:
                if (callBack != null) {
                    callBack.onFailure((Throwable) values[1], (Integer) values[2], (String) values[3]);
                }
                break;
            case UPDATE_SUCCESS:
                if (callBack != null) {
                    callBack.onSuccess((File) values[1]);
                }
                break;
        }
        super.onProgressUpdate(values);
    }

    @Override
    protected Object doInBackground(Object... params) {
        Log.d("http_file", "SimpleDownloader doInBackground");

        if (params != null && params.length == 2) {
            // 下载文件的构造 url和是否断点续传
            targetUrl = String.valueOf(params[0]);
            isResume = (Boolean) params[1];
        }

        try {
            // 开始
            publishProgress(UPDATE_START);
            makeRequestWithRetries(new HttpGet(targetUrl));
        } catch (IOException e) {
            // 结束
            publishProgress(UPDATE_FAILURE, e, -1, e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        callBack.onFinish();
    }

    /**
     * 执行网络请求
     *
     * @param request
     * @throws IOException
     */
    private void makeRequestWithRetries(HttpUriRequest request) throws IOException {
        if (isResume && targetUrl != null) {
            // 如果是断点续传功能，先要判断文件是否已经下载了一部分
            File downloadFile = config.savePath;
            long fileLen = 0;
            if (downloadFile.isFile() && downloadFile.exists()) {
                fileLen = downloadFile.length();
            }
            Log.d("owen", "开始下载 之前下载了" + fileLen);
            if (fileLen > 0) {
                request.setHeader("RANGE", "bytes=" + fileLen + "-");
            }
        }

        // 标识重试,retry:true,需要重试
        boolean retry = true;
        IOException cause = null;
        // 获取了retry的handler
        HttpRequestRetryHandler retryHandler = client.getHttpRequestRetryHandler();

        try {
            if (!isCancelled()) {
                // 如果本线程没有中断，就执行请求
                HttpResponse response = client.execute(request, context);
                if (!isCancelled()) {
                    handleResponse(response);
                }
            }
            return;
        } catch (UnknownHostException e) {
            publishProgress(UPDATE_FAILURE, e, 0, "unknownHostException：can't resolve host");
            return;
        } catch (IOException e) {
            cause = e;
            retry = retryHandler.retryRequest(cause, ++executionCount, context);
        } catch (NullPointerException e) {
            // HttpClient 4.0.x 之前的一个bug
            // http://code.google.com/p/android/issues/detail?id=5255
            cause = new IOException("NPE in HttpClient" + e.getMessage());
            retry = retryHandler.retryRequest(cause, ++executionCount, context);
        } catch (Exception e) {
            cause = new IOException("Exception" + e.getMessage());
            retry = retryHandler.retryRequest(cause, ++executionCount, context);
        }
    }

    private void handleResponse(HttpResponse response) {
        StatusLine status = response.getStatusLine();
        if (status.getStatusCode() >= 300) {
            String errorMsg = "response status error code:" + status.getStatusCode();
            if (status.getStatusCode() == 416 && isResume) {
                errorMsg += " \n maybe you have download complete.";
            }
            publishProgress(UPDATE_FAILURE,
                    new HttpResponseException(status.getStatusCode(), status.getReasonPhrase()),
                    status.getStatusCode(), errorMsg);
        } else {
            try {
                // HttpEntity是获取Http返回的实体
                HttpEntity entity = response.getEntity();
                Object responseBody = null;
                if (entity != null) {
                    time = SystemClock.uptimeMillis();
                    if (targetUrl != null) {
                        responseBody = mFileEntityHandler.handleEntity(entity, getDownloadProgressListener(),
                                config.savePath, isResume);
                    }
                }
                publishProgress(UPDATE_SUCCESS, responseBody);
            } catch (Exception e) {
                publishProgress(UPDATE_FAILURE, e, 0, e.getMessage());
            }

        }
    }


    /**
     * 对httpClient的请求参数做封装
     */
    private static class InflatingEntity extends HttpEntityWrapper {
        public InflatingEntity(HttpEntity wrapped) {
            super(wrapped);
        }

        @Override
        public InputStream getContent() throws IOException {
            return new GZIPInputStream(wrappedEntity.getContent());
        }

        @Override
        public long getContentLength() {
            return -1;
        }
    }

    private DownloadProgress getDownloadProgressListener() {
        return new DownloadProgress() {
            @Override
            public void onProgress(long count, long current) {
                publishProgress(UPDATE_LOADING, count, current);
            }
        };
    }

    public interface DownloadProgress {
        void onProgress(long count, long current);
    }

    @Override
    public void doDownload(String url, boolean isResume) {
        Log.d("http_file", "SimpleDownloader startDown params ={url = " + url + " isResume = " + isResume + "}");
        execute(url, isResume);
    }

    @Override
    public boolean isStop() {
        return mFileEntityHandler.isStop();
    }

    @Override
    public void stop() {
        mFileEntityHandler.setStop(true);
    }
}
