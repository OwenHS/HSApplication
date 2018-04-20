package com.hs.lib.log;

import android.content.Context;

import com.hs.lib.log.task.SDCardLogTask;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * HLogHelper类用于封装HLog的方法
 * Created by huangshuo on 16/8/29.
 */
public class HLogHelper {

    //打印线程
    private Executor service;
    //需要添加的输出方式对象
    private ArrayList<BaseLogTask> tasks;

    private static final String SUFFIX = ".java";
    private static final String TAG_DEFAULT = "HLog";
    private static final String NULL_TIPS = "Log with null object";
    private static final String PARAM = "Param";
    private static final String NULL = "null";
    private boolean ifKill = true;

    private boolean ifCatch = true;

    private static final int STACK_TRACE_INDEX = 6;

    private boolean isDebug = false;

    public HLogHelper() {
        service = Executors.newSingleThreadExecutor();
    }

    public HLogHelper addLogTask(BaseLogTask task) {
        if (tasks == null) {
            tasks = new ArrayList<BaseLogTask>();
        }
        tasks.add(task);
        return this;
    }


    public void print(int type, String tagStr, Object... objects) {
        if (isDebug || ifCatch) {
            printLog(type, tagStr, objects);
        }
    }


    /**
     * 统一入口，根据多种方式输出日志
     *
     * @param type    日志的等级
     * @param tagStr  日志的tag标志
     * @param objects 日志的信息
     */
    private void printLog(final int type, String tagStr, Object[] objects) {
        String[] contents = null;
        if (type != HLog.CRASH) {
            contents = wrapperContent(tagStr, objects);
        } else {
            contents = new String[]{"crash", getObjectsString(objects), "headString"};
        }
        final String[] finalContents = contents;
        service.execute(new Runnable() {
            @Override
            public void run() {
                if (tasks != null && tasks.size() > 0) {

                    String tag = finalContents[0];
                    String msg = finalContents[1];
                    String headString = finalContents[2];

                    for (int i = 0; i < tasks.size(); i++) {
                        tasks.get(i).printLog(type, tag, headString, msg);
                        if(tasks.get(i) instanceof SDCardLogTask) {
                            ((BaseLogTask)tasks.get(i)).printLog(type, "AppAllLog", headString, tag + "   " + msg);
                        }
                    }

                    if (type == HLog.CRASH) {
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                }
            }
        });

    }

    //封装 信息
    private static String[] wrapperContent(String tagStr, Object... objects) {
        try {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

            //获取打印日志的element所在位置
            StackTraceElement targetElement = stackTrace[STACK_TRACE_INDEX];
            //获取打印日志的classname
            String className = targetElement.getClassName();
            //将包名打印出来
            String[] classNameInfo = className.split("\\.");

            //获取真实的对象
            if (classNameInfo.length > 0) {
                className = classNameInfo[classNameInfo.length - 1] + SUFFIX;
            }

            //有可能是内部类
            if (className.contains("$")) {
                className = className.split("\\$")[0] + SUFFIX;
            }

            //获取打印日志的方法名
            String methodName = targetElement.getMethodName();
            //获取打印日志的行数
            int lineNumber = targetElement.getLineNumber();

            if (lineNumber < 0) {
                lineNumber = 0;
            }


            String methodNameShort = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);

            //如果tag没有传入的话，就使用类名
            String tag = (tagStr == null ? className : tagStr);

            if (tag == null || tag.length() == 0) {
                tag = TAG_DEFAULT;
            }

            //将所有的参数都输入进来
            String msg = (objects == null) ? NULL_TIPS : getObjectsString(objects);
            String headString = "[ (" + className + ":" + lineNumber + ")#" + methodNameShort + " ] ";

            return new String[]{tag, msg, headString};
        } catch (Exception e) {
            e.printStackTrace();
            return new String[]{tagStr, " ", " "};
        }
    }

    private static String getObjectsString(Object... objects) {

        if (objects.length > 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\n");
            for (int i = 0; i < objects.length; i++) {
                Object object = objects[i];
                if (object == null) {
                    stringBuilder.append(PARAM).append("[").append(i).append("]").append(" = ").append(NULL).append("\n");
                } else {
                    stringBuilder.append(PARAM).append("[").append(i).append("]").append(" = ").append(object.toString()).append("\n");
                }
            }
            return stringBuilder.toString();
        } else {
            Object object = objects[0];
            return object == null ? NULL : object.toString();
        }
    }

    public HLogHelper setDebug(boolean debug) {
        this.isDebug = debug;
        return this;
    }

    public boolean getDebug() {
        return isDebug;
    }


    public HLogHelper catchError(boolean ifCatch, Context context) {
        if (ifCatch) {
            ExceptionHandleUtil.register(context.getApplicationContext());
        }
        return this;
    }

    public HLogHelper catchError(boolean ifCatch, boolean ifKill, Context context) {
        this.ifCatch = ifCatch;
        this.ifKill = ifKill;
        if(ifCatch) {
            ExceptionHandleUtil.register(context.getApplicationContext());
        }

        return this;
    }
}
