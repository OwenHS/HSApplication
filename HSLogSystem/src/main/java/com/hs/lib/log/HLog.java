package com.hs.lib.log;

/**
 * 1.HSLogSystem 用于日志打印
 * 2.包含以下输出功能
 * （1）终端输出
 * （2）sd卡输出保存
 * Created by huangshuo on 16/8/29.
 */
public class HLog {

    private static HLogHelper logHelper;

    //以下是Log的等级
    public static final int V = 0x1;
    public static final int D = 0x2;
    public static final int I = 0x3;
    public static final int W = 0x4;
    public static final int E = 0x5;
    public static final int A = 0x6;
    public static final int JSON = 0x7;
    public static final int XML = 0x8;
    public static final int CRASH = 0x9;

    public static final String DEFAULT_MESSAGE = "OWEN IS GENIUS";
    public static final String NULL_TIPS = "Log with null object";

    public static final int JSON_INDENT = 4;
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * 初始化 log帮助类
     * @return
     */
    public static HLogHelper init(){
        if(logHelper == null){
            logHelper = new HLogHelper();
        }
        return logHelper;
    }

    public static void v() {
        logHelper.print(V,null,DEFAULT_MESSAGE);
    }

    public static void v(Object msg) {
        logHelper.print(V,null,msg);
    }

    public static void v(String tag, Object... objects) {
        logHelper.print(V,tag,objects);
    }

    public static void d() {
        logHelper.print(D,null,DEFAULT_MESSAGE);
    }

    public static void d(Object msg) {
        logHelper.print(D,null,msg);
    }

    public static void d(String tag, Object... objects) {
        logHelper.print(V,tag,objects);
    }

    public static void i() {
        logHelper.print(I,null,DEFAULT_MESSAGE);
    }

    public static void i(Object msg) {
        logHelper.print(I,null,msg);
    }

    public static void i(String tag, Object... objects) {
        logHelper.print(I,tag,objects);
    }

    public static void w() {
        logHelper.print(W,null,DEFAULT_MESSAGE);
    }

    public static void w(Object msg) {
        logHelper.print(W,null,msg);
    }

    public static void w(String tag, Object... objects) {
        logHelper.print(W,tag,objects);
    }

    public static void e() {
        logHelper.print(E,null,DEFAULT_MESSAGE);
    }

    public static void e(Object msg) {
        logHelper.print(E,null,msg);
    }

    public static void e(String tag, Object... objects) {
        logHelper.print(E,tag,objects);
    }

    public static void a() {
        logHelper.print(A,null,DEFAULT_MESSAGE);
    }

    public static void a(Object msg) {
        logHelper.print(A,null,msg);
    }

    public static void a(String tag, Object... objects) {
        logHelper.print(A,tag,objects);
    }

    public static void json(String jsonFormat) {
        logHelper.print(JSON, null, jsonFormat);
    }

    public static void json(String tag, String jsonFormat) {
        logHelper.print(JSON, tag, jsonFormat);
    }

    public static void xml(String xml) {
        logHelper.print(XML, null  , xml);
    }

    public static void xml(String tag, String xml) {
        logHelper.print(XML, tag, xml);
    }

    public static void addLogTask(BaseLogTask task){
        logHelper.addLogTask(task);
    }

    public static void setDebugMode(boolean isDebug){
        logHelper.setDebug(isDebug);
    }

    public static boolean getDebugMode() {
        return logHelper.getDebug();
    }

    protected static void crash(String crash, String message) {
        logHelper.print(CRASH,crash,message);
    }
}
