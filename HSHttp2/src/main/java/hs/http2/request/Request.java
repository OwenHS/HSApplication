package hs.http2.request;

/**
 * 请求数据类，定义了http请求所需要的数据
 * Created by huangshuo on 17/11/20.
 */

public final class Request {

    /**
     * 请求url
     */
    private HttpUrl url;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求头
     */
    private HttpHead httpHeads;

    /**
     * 请求体
     */
    private HttpBody httpBody;

    /**
     * @param builder
     */
    private Request(Builder builder) {
        this.url = builder.url;
        this.method = builder.method;
        this.httpBody = builder.httpBody;
        this.httpHeads = builder.httpHeads;
    }


    public static class Builder {
        /**
         * 请求url
         */
        private HttpUrl url;

        /**
         * 请求方法
         */
        private String method;

        /**
         * 请求头
         */
        private HttpHead httpHeads;

        /**
         * 请求体
         */
        private HttpBody httpBody;

        public Builder url(HttpUrl url) {
            if (url == null)
                throw new NullPointerException("url == null");
            this.url = url;
            return this;
        }

        public Request builder() {
            return new Request(this);
        }
    }

}
