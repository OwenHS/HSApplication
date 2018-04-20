package hs.http2.core;

import hs.http2.request.Call;
import hs.http2.request.RealCall;
import hs.http2.request.Request;

/**
 * http请求客户端
 * 使用了中介者模式
 *
 * PS：和原先的HSHttp相比，原先的HSHttp含有太多的功能在其中，扩展困难，
 * 将很多功能，封装在了RealCall，Request，Response，Dispatch中，功能单元细化。
 * 利于后期的扩展。
 * <p>
 * Created by huangshuo on 17/11/17.
 */

public class HSHttpClient implements RealCall.Factory{

    /**
     * 这里使用了抽象工厂的模式，
     * 是将抽象工厂简写放入了当前类中
     */
    @Override
    public Call newCall(Request request) {
        return RealCall.newRealCall(this, request);
    }

    /**
     * 建造者模式创建Http库需要的部件
     */
    public static final class Builder {

    }
}
