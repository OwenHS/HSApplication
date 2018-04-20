package com.hs.libs.sample;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by huangshuo on 17/9/21.
 */

public class UploadImageLayout extends ViewGroup {

    /**
     * 每个控件的宽度
     */
    private int itemWidth = 151;
    /**
     * 每个控件的高度
     */
    private int itemHeight = 151;

    /**
     * 计算后的真实宽度
     */
    private int itemRealWidth;
    /**
     * 计算后的真实高度
     */
    private int itemRealHeight;
    /**
     * 行距
     */
    private int rowSpace;

    private int column;


    public UploadImageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

//        for (int i = 0; i < 9; i++) {
//            LayoutInflater.from(context).inflate(R.layout.item_test, this);
//        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = resolveSizeAndState2(getDesireW(), widthMeasureSpec, 0);

        column = w / itemWidth;

        itemRealWidth = w / column;
        itemRealHeight = itemHeight;

        for (int i = 0; i < getChildCount(); i++) {
            int specWidth = MeasureSpec.makeMeasureSpec(itemRealWidth, MeasureSpec.EXACTLY);
            int specHeight = MeasureSpec.makeMeasureSpec(itemRealHeight, MeasureSpec.EXACTLY);
            measureChild(getChildAt(i), specWidth, specHeight);
        }

        int h = getChildCount() % column == 0 ? getChildCount() / column * itemRealHeight : (getChildCount() / column + 1) * itemRealHeight;

        setMeasuredDimension(MeasureSpec.getSize(w), MeasureSpec.getSize(h));

    }

    private int getDesireW() {
        return 300;
    }

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
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int columnNum = 0;
        int rowNum = 0;


        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (columnNum == 0) {
                child.setBackgroundColor(Color.RED);
            } else if (columnNum == 1) {
                child.setBackgroundColor(Color.BLUE);
            } else if (columnNum == 2) {
                child.setBackgroundColor(Color.YELLOW);
            }

            if (columnNum >= column) {
                columnNum = 0;
                rowNum++;
            }
            int left = columnNum * itemRealWidth;
            int right = (columnNum + 1) * itemRealWidth;
            int top = rowNum * itemRealHeight;
            int bottom = (rowNum + 1) * itemRealHeight;
            columnNum++;
            child.layout(left, top, right, bottom);
        }
    }


    public void addViewById(int layoutId) {
        LayoutInflater.from(getContext()).inflate(layoutId, this);
        invalidate();
    }

    public void addView(){
        LayoutInflater.from(getContext()).inflate(R.layout.item_test, this);
        invalidate();
    }
}
