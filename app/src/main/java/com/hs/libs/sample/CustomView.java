package com.hs.libs.sample;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 用于测试自定义view
 * <p>
 * 自定义属性和继承View
 * 重写onDraw方法
 * ——>实现构造方法并完成相关初始化操作
 * ——>重写onMeasure方法
 * ——>onSizeChanged()拿到view的宽高等数据
 * ——>重写onLayout
 * ————>重写onTouch实现交互——>定义交互回调接口
 * <p>
 * Created by huangshuo on 17/9/7.
 */

public class CustomView extends View {

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 用于本View宽高的测量，布局复杂时可能触发多次
     * 由于View默认的onMeasure()仅仅支持EXACTLY模式，也就是说如果不重写onMeasure()方法的话则无法在正确解析布局文件里的wrap_content
     *
     * @param :widthMeasureSpec  父级提出的水平宽度要求
     * @param :heightMeasureSpec 父级提出的垂直高度要求
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = resolveSizeAndState2(getDesireW(), widthMeasureSpec, 0);
        int h = resolveSizeAndState2(300, heightMeasureSpec, 0);
        setMeasuredDimension(MeasureSpec.getSize(w), MeasureSpec.getSize(h));

    }

    private int getDesireW() {
        return 300;
    }

    /**
     * @param size               How big the view wants to be.即传入你希望View的大小
     * @param measureSpec        Constraints imposed by the parent. 父级约束大小
     * @param childMeasuredState 一般传递0即可，特殊情况还可以传入1
     * @return
     */
    private int resolveSizeAndState2(int size, int measureSpec, int childMeasuredState) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
                //当specMode为AT_MOST，并且父控件指定的尺寸specSize小于View自己想要的尺寸时，
                //我们就会用掩码MEASURED_STATE_TOO_SMALL向量算结果加入尺寸太小的标记
                //这样其父ViewGroup就可以通过该标记其给子View的尺寸太小了，
                //然后可能分配更大一点的尺寸给子View
                if (specSize < size) {
                    result = specSize | MEASURED_STATE_TOO_SMALL;//按味或
                } else {
                    result = size;
                }
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result | (childMeasuredState & MEASURED_STATE_MASK);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    /**
     * 可拿到view的宽高等数据信息
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    /**
     * 1. 首先是执行叶子child的onMeasure方法，逐层向上调用，最后调用到root的onMeasure，onMeasure本身的作用就是告诉父亲自己占用多大的位置，根据递归原理，onMeasure的确应该从下往上层调用

     * 2. 调用到root的onMeasure后，其实这个统计大小的工作肯定是比root还高级别的View触发，也许就是DecorView，统计完大小后，开始调用onSizeChange，我想这里调用onSizeChange是因为首次显示的原因，调用onSizeChange是从root开始的，然后逐级调用到child，调用每个child的onSizeChange完毕后，每个child执行layout动作

     * 3. 从顺序来看，layout动作和onMeasure一样，都是从小往上层调用。从叶子child的onlayout调用开始，最后调用到root的onlayout方法。因为只有大小发生了变化才会执行onSizeChange,所以没有onSizeChange的时候，就是从尾到头执行完onmeasure，再开始从尾到头执行完onlayout

     * 4. 由此看来，onSizeChange并不一定会调用，只有View的大小发生变化才会调用，而且也不一定一定从root开始调用。onMeasure在整个界面上需要放置一样东西或拿掉一样东西时会调用。比如addView就是放置，removeview就是拿掉，另外比较特殊的是，child设置为gone会触发onMeasure，但是invisible不会触发onMeasure。一旦执行过onMeasure，往往就会执行onLayout来重新布局

     * 5. 分支影响整个分支直到root，比如上面的root有两个孩子，这两个孩子是不同的分支，这两个孩子是同级别，如果又孩子在点击button的时候让textview消失掉


     */
}
