package hs.http2.request;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import hs.http2.util.EmptyUtils;

/**
 * 对请求链接处理类，目前只有url，以及一些判断
 * Created by huangshuo on 17/11/21.
 * <p>
 * <p>
 * HttpUrl url = new HttpUrl.Builder()
 * .scheme("https")
 * .host("www.google.com")
 * .addPathSegment("search")
 * .addQueryParameter("q", "polar bears")
 * .build();
 */
public class HttpUrl {

    /**
     * 请求地址
     */
    private String url;

    /**
     * 协议
     */
    private String scheme;
    /**
     * 主机地址
     */
    private String host;
    /**
     * 功能路径
     */
    private String pathSegment;
    /**
     * http get 查询参数
     */
    public Map<String, String> urlParams;
    /**
     * 端口号
     */
    private int port = -1;

    /**
     * 基础Base
     */
    private String baseUrl;

    public HttpUrl(Builder builder) {

        this.scheme = builder.scheme;
        this.host = builder.host;
        this.pathSegment = builder.pathSegment;
        this.url = builder.toString();
    }

    public static int defaultPort(String scheme) {
        if (scheme.equals("http")) {
            return 80;
        } else if (scheme.equals("https")) {
            return 443;
        } else {
            return -1;
        }
    }

    public static final class Builder {
        /**
         * 协议
         */
        private String scheme;
        /**
         * 主机地址
         */
        private String host;
        /**
         * 主机端口
         */
        private int port = -1;
        /**
         * 功能路径
         */
        private String pathSegment;
        /**
         * http get 查询参数
         */
        public Map<String, String> urlParams = new HashMap<>();

        public Builder scheme(String scheme) {
            if (scheme == null) {
                throw new NullPointerException("scheme == null");
            } else if (scheme.equalsIgnoreCase("http")) {
                this.scheme = "http";
            } else if (scheme.equalsIgnoreCase("https")) {
                this.scheme = "https";
            } else {
                throw new IllegalArgumentException("unexpected scheme: " + scheme);
            }
            return this;
        }

        public Builder host(String host) {
            if (host == null)
                throw new NullPointerException("host == null");

            String encoded = null;
            try {
                encoded = URLEncoder.encode(host, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (encoded == null)
                throw new IllegalArgumentException("unexpected host: " + host);

            this.host = encoded;
            return this;
        }

        public Builder port(int port) {
            this.port = port;
            return this;
        }

        public Builder pathSegment(String pathSegment) {
            if (pathSegment == null)
                throw new NullPointerException("host == null");

            String encoded = null;
            try {
                encoded = URLEncoder.encode(pathSegment, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (encoded == null)
                throw new IllegalArgumentException("unexpected pathSegment: " + pathSegment);

            this.pathSegment = encoded;
            return this;
        }

        public Builder addQueryParameter(String name, String value) {
            if (name == null)
                throw new NullPointerException("name == null");
            if (EmptyUtils.isEmpty(value)) {
                value = "";
            }
            urlParams.put(name, value);
            return this;
        }

        public HttpUrl build() {
            return new HttpUrl(this);
        }

        @Override
        public String toString() {

            StringBuilder result = new StringBuilder();
            result.append(scheme);
            result.append("://");
            result.append(host);

            int effectivePort = effectivePort();
            if (effectivePort != defaultPort(scheme)) {
                result.append(':');
                result.append(effectivePort);
            }

            if (pathSegment != null) {
                result.append("/");
                result.append(pathSegment);
            }

            if(urlParams.size() > 0){
                result.append("?");
                appendParams(result,urlParams);
            }

            return result.toString();
        }

        private void appendParams(StringBuilder result, Map<String, String> urlParams) {
            Iterator var3 = this.urlParams.entrySet().iterator();

            while (var3.hasNext()) {
                Map.Entry entry = (Map.Entry) var3.next();
                if (result.length() > 0) {
                    result.append("&");
                }

                try {
                    result.append(URLEncoder.encode((String) entry.getKey(), "utf-8"));
                    result.append("=");
                    result.append(URLEncoder.encode((String) entry.getValue(), "utf-8"));
                } catch (UnsupportedEncodingException var5) {
                    result.append((String) entry.getKey());
                    result.append("=");
                    result.append((String) entry.getValue());
                }
            }
        }

        int effectivePort() {
            return port != -1 ? port : defaultPort(scheme);
        }


    }
}
