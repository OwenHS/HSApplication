package lib.hs.com.hsbaselib;

import android.os.Bundle;

import lib.hs.com.logsystemlib.support.Log;

/**
 * 用于添加log系统
 */
public abstract class HSBaseActivity extends HSUiInitialActivity {

    private static final String TAG = "HSBaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        Log.d(TAG,"onResume");
        super.onResume();
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
        Log.d(TAG,"onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG,"onStop");
        super.onStop();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG,"onRestart");
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG,"onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetachedFromWindow() {
        Log.d(TAG,"onDetachedFromWindow");
        super.onDetachedFromWindow();
    }
}
