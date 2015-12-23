package lib.hs.com.hsbaselib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import lib.hs.com.hsbaselib.activitystack.ActivityStack;
import lib.hs.com.logsystemlib.support.Log;

/**
 * 用于添加log系统
 */
public abstract class HSBaseActivity extends HSUiInitialActivity {

    private static final String TAG = "HSBaseActivity";

    /** Activity状态 */
    public ActivityState activityState = ActivityState.DESTROY;
    /**
     * 当前Activity状态
     */
    public static enum ActivityState {
        RESUME, PAUSE, STOP, DESTROY
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        ActivityStack.getInstance().addActivity(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        Log.d(TAG,"onResume");
        super.onResume();
        activityState = ActivityState.RESUME;
    }

    @Override
    protected void onStart() {
        Log.d(TAG,"onStart");
        super.onStart();
    }


    @Override
    public void onAttachedToWindow() {
        Log.d(TAG,"onAttachedToWindow");
        super.onAttachedToWindow();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
        activityState = ActivityState.PAUSE;
    }

    @Override
    protected void onStop() {
        Log.d(TAG,"onStop");
        super.onStop();
        activityState = ActivityState.STOP;
    }

    @Override
    protected void onRestart() {
        Log.d(TAG,"onRestart");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG,"onDestroy");
        activityState = ActivityState.DESTROY;
        super.onDestroy();
        ActivityStack.getInstance().finishActivity(this);
    }

    @Override
    public void onDetachedFromWindow() {
        Log.d(TAG,"onDetachedFromWindow");
        super.onDetachedFromWindow();
    }


    /**
     * skip to @param(cls)，and call @param(aty's) finish() method
     */
    @Override
    public void skipActivity(Activity aty, Class<?> cls) {
        showActivity(aty, cls);
        aty.finish();
    }

    /**
     * skip to @param(cls)，and call @param(aty's) finish() method
     */
    @Override
    public void skipActivity(Activity aty, Intent it) {
        showActivity(aty, it);
        aty.finish();
    }

    /**
     * skip to @param(cls)，and call @param(aty's) finish() method
     */
    @Override
    public void skipActivity(Activity aty, Class<?> cls, Bundle extras) {
        showActivity(aty, cls, extras);
        aty.finish();
    }

    /**
     * show to @param(cls)，but can't finish activity
     */
    @Override
    public void showActivity(Activity aty, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(aty, cls);
        aty.startActivity(intent);
    }

    /**
     * show to @param(cls)，but can't finish activity
     */
    @Override
    public void showActivity(Activity aty, Intent it) {
        aty.startActivity(it);
    }

    /**
     * show to @param(cls)，but can't finish activity
     */
    @Override
    public void showActivity(Activity aty, Class<?> cls, Bundle extras) {
        Intent intent = new Intent();
        intent.putExtras(extras);
        intent.setClass(aty, cls);
        aty.startActivity(intent);
    }
}
