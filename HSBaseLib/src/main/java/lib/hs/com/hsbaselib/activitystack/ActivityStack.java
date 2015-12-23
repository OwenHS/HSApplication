package lib.hs.com.hsbaselib.activitystack;

import android.app.Activity;
import android.content.Context;

import java.util.Stack;

import lib.hs.com.hsbaselib.interfaces.UIProcessable;

/**
 * 用于activity栈使用
 * Created by owen on 15-12-22.
 */
public class ActivityStack {

    private Stack<UIProcessable> aStack;

    private static ActivityStack instance;

    private ActivityStack() {
        if (aStack == null) {
            aStack = new Stack<>();
        }
    }

    public int getCount() {
        return aStack.size();
    }

    //获取单例
    public static ActivityStack getInstance() {
        if (instance == null) {
            instance = new ActivityStack();
        }
        return instance;
    }


    //activity入栈,将activity添加到栈顶
    public void addActivity(UIProcessable aty) {
        aStack.add(aty);
    }


    //获取栈顶的Activity
    public Activity topActivity() {
        if (aStack.isEmpty()) {
            return null;
        }
        UIProcessable aty = aStack.lastElement();
        return (Activity) aty;
    }

    /**
     * 获取当前Activity（栈顶Activity） 没有找到则返回null
     */
    public Activity findActivity(Class<?> cls) {
        UIProcessable activity = null;
        for (UIProcessable aty : aStack) {
            if (aty.getClass().equals(cls)) {
                activity = aty;
                break;
            }
        }
        return (Activity) activity;
    }

    /**
     * 结束当前Activity（栈顶Activity）
     */
    public void finishActivity() {
        UIProcessable activity = aStack.lastElement();
        finishActivity((Activity) activity);
    }

    /**
     * 结束指定的Activity(重载)
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            aStack.remove(activity);
            activity.finish();//此处不用finish
            activity = null;
        }
    }

    /**
     * 结束指定的Activity(重载)
     */
    public void finishActivity(Class<?> cls) {
        for (UIProcessable activity : aStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity((Activity) activity);
            }
        }
    }

    /**
     * 关闭除了指定activity以外的全部activity 如果cls不存在于栈中，则栈全部清空
     *
     * @param cls
     */
    public void finishOthersActivity(Class<?> cls) {
        for (UIProcessable activity : aStack) {
            if (!(activity.getClass().equals(cls))) {
                finishActivity((Activity) activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = aStack.size(); i < size; i++) {
            if (null != aStack.get(i)) {
                ((Activity) aStack.get(i)).finish();
            }
        }
        aStack.clear();
    }

    /**
     * 应用程序退出
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            Runtime.getRuntime().exit(0);
        } catch (Exception e) {
            Runtime.getRuntime().exit(-1);
        }
    }
}
