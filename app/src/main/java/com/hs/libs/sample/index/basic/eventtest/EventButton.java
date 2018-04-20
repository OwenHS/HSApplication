package com.hs.libs.sample.index.basic.eventtest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;


public class EventButton extends Button {
    public EventButton(Context context) {
        super(context);
    }

    public EventButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EventButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        TouchEventUtil.logActionMsg(getClass(), "onTouchEvent", ev);
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        TouchEventUtil.logActionMsg(getClass(), "dispatchTouchEvent", ev);
        return super.dispatchTouchEvent(ev);
    }
}
